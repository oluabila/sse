package com.universitysys.SSE.controller;

import com.universitysys.SSE.model.Account;
import com.universitysys.SSE.model.Students;
import com.universitysys.SSE.repository.LoginRepository;
import com.universitysys.SSE.repository.ModuleRepository;
import com.universitysys.SSE.service.HasModuleService;
import com.universitysys.SSE.service.ModuleService;
import com.universitysys.SSE.service.RegisterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import com.universitysys.SSE.service.LoginService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.SynchronousQueue;

@Controller
@SessionAttributes("name")
public class LoginController {
    private static final Logger logger =   LoggerFactory.getLogger( LoginController.class);
    @Autowired
    LoginService service;
    @Autowired
    LoginRepository repository;
    @Autowired
    ModuleRepository moduleRepository;
    @Autowired
    ModuleService moduleService;
    @Autowired
    RegisterService registerService;
    @Autowired
    HasModuleService hasModuleService;

    @Autowired
    private HttpServletRequest request;

    private String getClientIP() {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null){
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }


    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView showRegister() {
        ModelAndView mav = new ModelAndView("register");
        mav.addObject("students", new Students());
        return mav;
    }
    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public ModelAndView showWelcome() {
        return new ModelAndView("welcome");
    }



    @GetMapping({"/login" , "/"})
    public ModelAndView showLoginPage(ModelMap model, HttpSession session) {
        if (session.getAttribute("student_user") != null) {
            return new ModelAndView(new RedirectView("/welcome"));
        }
        model.addAttribute("student", new Students());


        return new ModelAndView("index", model);


    }

    @RequestMapping(value = "/logout")
    public RedirectView showLogoutPage(HttpSession session) {
        session.removeAttribute("staff_user");
        session.removeAttribute("student_user");
        return new RedirectView("/");
    }

    Map<String, Integer> ip_list = new HashMap<String,Integer>();

    private void WrongAttempt(String ip){
        Object check = ip_list.get(ip);
        if (check == null){
            ip_list.put(ip, 1);
        }
        else {
            ip_list.replace(ip, ip_list.get(ip), ip_list.get(ip) + 1);


        }

    }
    private Boolean check_IP_list(String ip){
        if(ip_list.get(ip) == null) return true;
        else{
        if (ip_list.get(ip) == 3){
            logger.warn("This user is blocked, because of 3 times wrong login. " + ip);
            return false;}
        else
            return true;}
    }
    

    @RequestMapping(value={"/login", "/"}, method = RequestMethod.POST)
    public String showWelcomePage(ModelMap model, @RequestParam String name, @RequestParam String password, HttpSession session, @ModelAttribute Students student, Account account) throws UnknownHostException {
        String ip = getClientIP();
        String remote_ip = InetAddress.getLocalHost().getHostAddress();
        Boolean check = check_IP_list(remote_ip) ;
        logger.warn("Show the list of IP adresses " + ip_list);
        logger.info("This ip of user " + ip + "and host address of user " +(InetAddress.getLocalHost().getHostAddress()))  ;
        String hash_password = repository.findPasswordbyName(name);
        if (! check){
            model.addAttribute("errorMessage", "Error: Your IP is blocked.");
            return "index";
        }
        else{
        if (hash_password != null ){
        boolean isValidStudent = service.validateStudent(account , hash_password);

        if (!isValidStudent) {
            WrongAttempt(remote_ip);
            model.addAttribute("errorMessage", "Error: Invalid Credentials");
            logger.warn("This user tried to login with errors: " + name);
            return "index";
        }

        else {
            logger.info("This user logged in succesfully: " + name);
            Integer findId = repository.findIdbyName(name);
            List<Students> found = repository.findById(findId);
            List<Students> studen = registerService.showStudent(findId);
            Students user=found.get(0) ;
            model.addAttribute("student", studen.toString());
            model.put("findId", findId);
            model.put("name", name);
            String welcome = repository.findName(findId) + " " + repository.findSurnameName(findId);
            model.addAttribute("atr_name",  welcome);
            model.addAttribute("Id", findId);
            session.setAttribute("student_user", user);
            return "welcome";
        }}
        else{
            WrongAttempt(remote_ip);
            model.addAttribute("errorMessage", "Error: Invalid Credentials");
            return "index";}}
    }
    @RequestMapping(value = "/mypayment", method = RequestMethod.POST)
    public  ModelAndView addPayment(HttpSession session,HttpServletRequest request, HttpServletResponse response,
                             @ModelAttribute Students students, ModelMap model) {

        Object name = model.get("name");
        Integer findId = repository.findIdbyName(name.toString());
        service.paysAccount(findId);
        return new  ModelAndView( "mymodules");

    }
    @RequestMapping(value="/mypayment", method = RequestMethod.GET)
    public ModelAndView addPayment(HttpSession session,HttpServletRequest request, HttpServletResponse response,
                              @ModelAttribute Account account, ModelMap model){
        if (session.getAttribute("student_user") != null) {

            Object name = model.get("name");
            Integer findId = repository.findIdbyName(name.toString());
            service.paysAccount(findId);
            logger.info("This user paid for courses succesfully: " + name);
            model.addAttribute("message", "You have already paid for classes, so now it is possible to choose your modules.");
            return new ModelAndView("payments");

        }
        return new ModelAndView("index");

    }
    @RequestMapping(value = "/allmodules" , method = RequestMethod.POST)
    public void enrollmodules(ModelMap model, @RequestParam String id_module) {

        Object name = model.get("name");
        Integer findId = repository.findIdbyName(name.toString());
        Integer findAmount = moduleService.showAmount();
        Boolean fee = repository.findFeebyName(name.toString());
        if(fee){
        if(Integer.parseInt(id_module) < findAmount ){
            logger.info("The user " + name + " enrolled succesfully to course with id " + id_module);
            hasModuleService.addMyModule(findId, id_module);
            model.put("succes", "You have succefully enrolled to this module. Go to mymodules to check");}
        else {
            model.put("succes", "There are some problems check module id or your fees.");
        }}
        else{
            model.put("succes", "Pay fees first.");
        }

    }
    @RequestMapping(value = "mymodules" , method = RequestMethod.GET)
    public ModelAndView myModules(ModelMap model, HttpSession session) {
        ModelAndView mod = new ModelAndView("mymodules");
        if (session.getAttribute("student_user") != null) {

        Object name = model.get("name");
        Integer findId = repository.findIdbyName(name.toString());
        Integer id[] = moduleRepository.findMyID(findId);
        String sent = null;
        if (id.length > 0)
            sent = " " + "id = " + id[0];

        if (id.length > 1)
            for (int i = 1; i < id.length; i++) {
                sent = sent + " or " + " id = " + id[i];
            }
        mod.addObject("modules",  moduleService.findMyModule(sent));

        return mod;}
        else
            return new ModelAndView("index");
    }
    @RequestMapping(value = "mymodules" , method = RequestMethod.POST)
    public ModelAndView modules(ModelMap model, HttpSession session) {

        Object name = model.get("name");
        Integer findId = repository.findIdbyName(name.toString());

        Integer id[] = moduleRepository.findMyID(findId);

        String sent = null;
        if (id.length > 0)
            sent = " " + "id = " + id[0];
        ModelAndView mod = new ModelAndView("mymodules");
        if (id.length > 1)
            for (int i = 1; i < id.length; i++) {
                sent = sent + " or " + " id = " + id[i];
            }
        mod.addObject("modules",  moduleService.findMyModule(sent));

        // mod.addObject("modules",  moduleService.showInfo());
        return mod;
    }
    @RequestMapping(value="/payment", method = RequestMethod.GET)
    public String showPayment(HttpSession session,HttpServletRequest request, HttpServletResponse response,
                              @ModelAttribute Account account, ModelMap model){
        if (session.getAttribute("student_user") != null) {
            return "payments";

        }
        return  "index" ;

    }

}
