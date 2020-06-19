package com.universitysys.SSE.service;

import com.universitysys.SSE.model.Account;
import com.universitysys.SSE.model.Module;
import com.universitysys.SSE.model.Student_has_module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class ModuleService {
    @Autowired
    JdbcTemplate jdbcTemplate;


    public List<Module> showInfo(){
        String sql1 = "select * from module";
        List<Module> users = jdbcTemplate.query(sql1,new UserMapper());
        return users;
    }
    public Integer showAmount(){
        String sql1 = "select * from module";
        List<Module> users = jdbcTemplate.query(sql1,new UserMapper());
       return users.size();

    }
    public List<Module> findMyModule(String st){
        String sql1 = "select * from module where "+ st +";";
        List<Module> users = jdbcTemplate.query(sql1,new UserMapper());
        return users;
    }

    class UserMapper implements RowMapper<Module> {
        public Module mapRow(ResultSet rs, int arg1) throws SQLException {
            Module user = new Module();
            user.setName(rs.getString("name"));
            user.setCredit(rs.getString("credit"));
            user.setEnrolled_stud(rs.getInt("enrolled_stud"));
            user.setMaxplace(rs.getInt("max_places"));
            user.setId(rs.getInt("id"));
            return user;
        }
    }

}
