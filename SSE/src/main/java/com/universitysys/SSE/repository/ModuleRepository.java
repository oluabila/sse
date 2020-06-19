package com.universitysys.SSE.repository;

import com.universitysys.SSE.model.Module;
import com.universitysys.SSE.model.Student_has_module;
import com.universitysys.SSE.model.Students;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleRepository extends JpaRepository<Student_has_module, Long> {
    @Query(value = "select module_id from student_has_module where student_id = ?1", nativeQuery = true)
    Integer[] findMyID(long id);

}
