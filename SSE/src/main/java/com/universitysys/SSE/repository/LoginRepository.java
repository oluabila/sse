package com.universitysys.SSE.repository;

import com.universitysys.SSE.exception.WrongPasswordOrLogin;
import com.universitysys.SSE.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import com.universitysys.SSE.model.Students;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginRepository extends JpaRepository<Students,Long> {
    @Query(value = "SELECT * from student where id = ?1", nativeQuery = true)
     List<Students> findById(Integer id);
    @Query(value = "SELECT id from account where username = ?1", nativeQuery = true)
    Integer findIdbyName(String name);
    @Query(value = "SELECT fees from account where username = ?1", nativeQuery = true)
    Boolean findFeebyName(String name);
    @Query(value = "SELECT surname from student where id = ?1", nativeQuery = true)
    String findSurnameName(Integer id);
    @Query(value = "SELECT name from student where id = ?1", nativeQuery = true)
    String findName(Integer id);
    @Query(value = "SELECT password from account where username = ?1", nativeQuery = true)
    String findPasswordbyName(String name);

}
