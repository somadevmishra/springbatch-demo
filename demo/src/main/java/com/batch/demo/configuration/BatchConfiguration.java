package com.batch.demo.configuration;

import com.batch.demo.entity.Employee;
import com.batch.demo.mapper.EmployeeMapper;
import com.batch.demo.processor.EmployeeFileProcessor;
import com.batch.demo.repository.EmployeeRepository;
import com.batch.demo.writer.EmployeeWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import java.io.File;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private EmployeeRepository empRepository;

    @Autowired
    private EmployeePropertyConfig propertyConfig;

    @Bean
    public Job createJob(){
        return jobBuilderFactory.get("demoJob")
                .start(firstStep())
                .build();
    }

    @Bean
    public Step firstStep(){
        return stepBuilderFactory.get("readfile")
                .<Employee, Employee>chunk(200)
                .reader(getFileReader())
                .processor(getProcessor())
                .writer(getWriter())
                .taskExecutor(simpleTaskExecutor())
                .build();
    }

    @Bean
    public FlatFileItemReader<Employee> getFileReader(){
        return new FlatFileItemReaderBuilder<Employee>()
                .resource(new FileSystemResource(propertyConfig.getInputFolder()+"/" +propertyConfig.getFilename()))
                .fieldSetMapper(new EmployeeMapper())
                .lineTokenizer(getMyLineTokenzier())
                .name("myFirstReader")
                .linesToSkip(1)
                .build();
    }

    @Bean
    public ItemProcessor<Employee, Employee> getProcessor(){
        return new EmployeeFileProcessor();
    }

    @Bean
    public ItemWriter<Employee> getWriter(){
        return new EmployeeWriter();
    }

    @Bean
    public DelimitedLineTokenizer getMyLineTokenzier(){
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer("|");
        lineTokenizer.setNames(new String[]{"first_name","last_name","status","date_of_join","date_of_birth","email"});
        return lineTokenizer;
    }

    public SimpleAsyncTaskExecutor simpleTaskExecutor(){
        return new SimpleAsyncTaskExecutor();
    }
}
