package com.recruiter.service.impl;

import com.recruiter.model.Application;
import com.recruiter.repository.ApplicationRepository;
import com.recruiter.service.ApplicationService;
import com.recruiter.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private JobService jobService;

    @Override
    public Integer save(Application application, Long userId) {
        if (isExist(application.getJobId(), userId)) {
            return 1;
        } else if(!jobService.isExist(application.getJobId())){
            return 2;
        } else if(!jobService.isOpen(application.getJobId())) {
            return 3;
        }
        applicationRepository.save(application);
        return 0;
    }


    @Override
    public void deleteById(Long id) {
        applicationRepository.deleteById(id);
    }

    @Override
    public Application findById(Long id) {
        return applicationRepository.findById(id).orElse(null);
    }

//    public Integer isValidApplication(Long applicationId) {
//        Optional<Application> application = Optional.ofNullable(applicationRepository.findById(applicationId).orElse(null));
//        if(application.isPresent(application.getTitle())){
//            if (application.getTitle()) {
//                return 0;
//            } else {
//                return 1;
//            }
//        } else {
//            return -1;
//        }
//    }

    @Override
    public Optional<Application> findByIdAndUserId(Long applicationId, Long userId) {
        return applicationRepository.findByIdAndUserId(applicationId, userId);
    }


    public boolean isExist(Long applicationId) {
        return applicationRepository.findById(applicationId).isPresent();
    }

    public boolean isExist(Long jobId, Long userId) {
        return applicationRepository.findByJobIdAndUserId(jobId, userId).isPresent();
    }


}
