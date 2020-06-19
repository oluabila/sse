package com.universitysys.SSE.service;

import com.universitysys.SSE.model.Account;
import com.universitysys.SSE.model.Students;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import com.universitysys.SSE.repository.StatisticRepository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class StatisticService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Students> getOne(int id) {
        String sql = "select * from student where id=?;";
        List<Students> users = jdbcTemplate.query(sql, new UserMapper());
        return users;
    }

    public List<Students> showInfo() {
        String sql1 = "select * from student";
        List<Students> users = jdbcTemplate.query(sql1, new UserMapper());
        return users;
    }


    class UserMapper implements RowMapper<Students> {
        public Students mapRow(ResultSet rs, int arg1) throws SQLException {
            Students user = new Students();
            user.setName(rs.getString("name"));
            user.setSurname(rs.getString("surname"));
            user.setDate_of_birth(rs.getString("date_of_birth"));
            user.setSex(rs.getString("sex"));
            user.setNationality(rs.getString("nationality"));
            user.setId(rs.getInt("id"));
            return user;
        }
    }


}
