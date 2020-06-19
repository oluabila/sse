package com.universitysys.SSE.service;

import com.universitysys.SSE.model.Module;
import com.universitysys.SSE.model.Students;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class RegisterService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void registerStudent(Students students){
        String sql1 = "SELECT * FROM student";
        List<Students> users = jdbcTemplate.query(sql1, new  UserMapper());
        String sql = "insert into student values(?,?,?,?,?,?)";
        jdbcTemplate.update(sql, new Object[] {users.size()+1,students.getName(), students.getSurname(), students.getDate_of_birth(),students.getNationality(),students.getSex()});

    }
    public List<Students> showStudent (Integer id){
        String sql1 = "select * from student where  id = "+ id +";";
        List<Students> users = jdbcTemplate.query(sql1,new UserMapper());
        return users;
    }
    public List<Students> showSurname(Integer id){
        String sql1 = "SELECT surname FROM student where id = " + id+";";
        List<Students> users = jdbcTemplate.query(sql1, new  UserMapper());
        return users;
    }
    class UserMapper implements RowMapper<Students> {
        public Students  mapRow(ResultSet rs, int arg1) throws SQLException {
            Students user = new Students();
            user.setNationality(rs.getString("nationality"));
            user.setSex(rs.getString("sex"));
            user.setDate_of_birth(rs.getString("date_of_birth"));
            user.setName(rs.getString("name"));
            user.setSurname(rs.getString("surname"));
            user.setId(rs.getInt("id"));
            return user;
        }
    }




}
