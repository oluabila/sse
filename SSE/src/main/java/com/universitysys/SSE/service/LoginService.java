package com.universitysys.SSE.service;

import com.universitysys.SSE.exception.WrongPasswordOrLogin;
import com.universitysys.SSE.model.Account;
import com.universitysys.SSE.model.Students;
import com.universitysys.SSE.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.jdbc.core.RowMapper;

import javax.persistence.NamedQuery;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class LoginService {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    LoginRepository repository;

    public boolean validateStudent(Account account,  String hash_password) {
        Boolean pw_hash = BCrypt.checkpw(account.getPassword(), hash_password);
        if (pw_hash ){
            return true;
        }
        return false;
    }

    public boolean validateUsername( String username ) {

        String sql = "select * from account where username = '" + username +"' ;";
        List<Account> users = jdbcTemplate.query(sql, new UserMapper());
        if (users.size() == 1 ){
            return true;
        }
        return false;
    }

    class UserMapper implements RowMapper<Account> {
        public Account  mapRow(ResultSet rs, int arg1) throws SQLException {
            Account user = new Account();
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            return user;
        }
    }
    public void registerAccount(Account account){
        String sql1 = "select * from account";
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12); // Strength set as 12
        List<Account> users = jdbcTemplate.query(sql1, new UserMapper());
        String sql = "insert into account values(?,?,?,?,?)";
        jdbcTemplate.update(sql, new Object[] {account.getUsername(), encoder.encode(account.getPassword()), users.size()+1, 0, users.size()+1});
    }

    public void payAccount(Students student){
        String sql = "update account set `fees`=1 where `id`="+student.getId() +";";
        jdbcTemplate.update(sql, new Object[] {student.getName()});
    }

    public void paysAccount(Integer id){
        String sql = "update account set fees=? where id =?";
        this.jdbcTemplate.update(sql, 1, id) ;
    }

}