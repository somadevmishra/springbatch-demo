package com.batch.demo.mapper;

import com.batch.demo.entity.Employee;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import java.time.ZoneId;

public class EmployeeMapper implements FieldSetMapper<Employee> {
    @Override
    public Employee mapFieldSet(FieldSet fieldSet) throws BindException {
        if (fieldSet != null) {
            return Employee.builder()
                    .firstName(fieldSet.readString("first_name"))
                    .lastName(fieldSet.readString("last_name"))
                    .status(fieldSet.readString("status").equals("true")?"A":"I")
                    .dateOfJoin(fieldSet.readDate("date_of_join", "dd-MM-yyyy").toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                    .dateOfBirth(fieldSet.readDate("date_of_birth","dd-MM-yyyy").toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                    .emailId(fieldSet.readString("email"))
                    .build();
        }
        return null;
    }
}
