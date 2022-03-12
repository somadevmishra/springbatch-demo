package com.batch.demo.writer;

import com.batch.demo.entity.Employee;
import com.batch.demo.repository.EmployeeRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class EmployeeWriter implements ItemWriter<Employee> {

    @Autowired
    EmployeeRepository employeeRepository;


    @Override
    public void write(List<? extends Employee> list) throws Exception {
        employeeRepository.saveAll(list);
    }
}
