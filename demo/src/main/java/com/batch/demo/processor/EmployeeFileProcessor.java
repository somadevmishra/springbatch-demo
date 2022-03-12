package com.batch.demo.processor;

import com.batch.demo.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDate;

@Slf4j
public class EmployeeFileProcessor implements ItemProcessor<Employee, Employee> {
    @Override
    public Employee process(Employee employee) throws Exception {
        employee.setCreatedBy("batch-user");
        employee.setCreatedDate(LocalDate.now());
        employee.setUpdatedBy("batch-user");
        employee.setUpdatedDate(LocalDate.now());

        log.trace("employee: {}", employee);
        return employee ;
    }
}
