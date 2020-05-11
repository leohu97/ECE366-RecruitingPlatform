package com.recruiter.validator;

import com.recruiter.model.Job;
import com.recruiter.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Objects;

@Component
public class JobValidator implements Validator {
    @Autowired
    private JobService jobService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Job.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Job job = (Job) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "company", "EmptyCompany");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "EmptyTitle");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "location", "EmptyLocation");

        if (job.getSalary() == 0) {
            errors.rejectValue("salary", "zerosalary");
        }

    }
}
