package com.universitysys.SSE.service;

import com.universitysys.SSE.model.Module;
import com.universitysys.SSE.model.Student_has_module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class HasModuleService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void addMyModule(Integer student_id, String module_id ){
        String sql1 = "insert into student_has_module values (?,?,?)" ;
        jdbcTemplate.update(sql1, new Object[] {student_id,module_id, 0});;
    }

    class UserMapper implements RowMapper<Student_has_module> {
        public Student_has_module mapRow(ResultSet rs, int arg1) throws SQLException {
            Student_has_module module = new Student_has_module();
            module.setGrade(rs.getInt("grade"));
            module.setModule_id(rs.getInt("module_id"));
            module.setStudent_id(rs.getInt("student_id"));
            return module ;
        }
    }

}
