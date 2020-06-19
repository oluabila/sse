package com.universitysys.SSE.service;

import com.universitysys.SSE.model.Professor;
import com.universitysys.SSE.model.Student_has_module;
import com.universitysys.SSE.model.Students;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class ProfessorService {
    @Autowired
    JdbcTemplate jdbcTemplate;



    public List<Student_has_module> showGrades( int student_id,int module_id){
        String sql = "select * from student_has_module where `student_id`='" +student_id  +"'and `module_id`='"+ module_id+"';";
        List<Student_has_module> users = jdbcTemplate.query(sql,new GradeMapper());
        return  users;
    }

    public List<Professor> showAll(){
       String sql = "select * from professor";
        List<Professor> users = jdbcTemplate.query(sql,new ProfessorMapper());
        return users;
    }

    class ProfessorMapper implements RowMapper<Professor> {
        public Professor mapRow(ResultSet rs, int arg1) throws SQLException {
            Professor user = new Professor();
            user.setName(rs.getString("name"));
            user.setSurname(rs.getString("surname"));
            user.setId(rs.getInt("id"));
            return user;
        }
    }
    class GradeMapper implements RowMapper<Student_has_module> {
        public Student_has_module mapRow(ResultSet rs, int arg1) throws SQLException {
            Student_has_module user = new Student_has_module();
            user.setGrade(rs.getInt("grade"));
            return user;
        }
    }
}
