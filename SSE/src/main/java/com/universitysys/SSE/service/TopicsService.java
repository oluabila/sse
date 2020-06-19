package com.universitysys.SSE.service;

import com.universitysys.SSE.model.Professor;
import com.universitysys.SSE.model.Students;
import com.universitysys.SSE.model.Topics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class TopicsService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Topics> showAll(){
        String sql = "select * from topics";
        List<Topics> users = jdbcTemplate.query(sql,new TopicsMapper());
        return users;
    }


    class TopicsMapper implements RowMapper<Topics> {
        public Topics mapRow(ResultSet rs, int arg1) throws SQLException {
            Topics user = new Topics();
            user.setTitle( rs.getString("title"));
            user.setGrade_distribution(rs.getInt("grade_distribution"));
            user.setIdtopics(rs.getInt("idtopics"));
            return user;
        }
    }
}
