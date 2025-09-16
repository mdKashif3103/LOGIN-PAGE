package com.example.magnus.repo;

import com.example.magnus.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    List<Employee> findByDeletedFalse();

    @Modifying
    @Query("update Employee e set e.deleted = true where e.id in :ids")
    int bulkSoftDelete(List<UUID> ids);
}
