package com.example.magnus.service;

import com.example.magnus.model.Employee;
import com.example.magnus.repo.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService {
    private final EmployeeRepository repo;

    public EmployeeService(EmployeeRepository repo) { this.repo = repo; }

    public Employee create(Employee e){ if(e.getId()==null) e.setId(UUID.randomUUID()); return repo.save(e); }
    public Employee get(UUID id){ return repo.findById(id).orElseThrow(() -> new RuntimeException("Employee not found")); }
    public List<Employee> list(){ return repo.findByDeletedFalse(); }
    public Employee update(UUID id, Employee incoming){
        Employee ex = get(id);
        ex.setName(incoming.getName());
        ex.setEmail(incoming.getEmail());
        ex.setGender(incoming.getGender());
        ex.setPhone(incoming.getPhone());
        ex.setDob(incoming.getDob());
        return repo.save(ex);
    }
    @Transactional
    public void softDelete(UUID id){ Employee e = get(id); e.setDeleted(true); repo.save(e); }
    @Transactional
    public int bulkSoftDelete(List<UUID> ids){ return repo.bulkSoftDelete(ids); }
    @Transactional
    public void restore(UUID id){ Employee e = get(id); e.setDeleted(false); repo.save(e); }
}
