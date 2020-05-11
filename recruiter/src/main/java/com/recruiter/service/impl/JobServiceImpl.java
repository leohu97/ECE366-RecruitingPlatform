package com.recruiter.service.impl;

import com.recruiter.model.Job;
import com.recruiter.repository.JobRepository;
import com.recruiter.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {
    @Autowired
    private JobRepository jobRepository;

    @Override
    public Integer save(Job job) {
        if (job.getSalary() == 0 || job.getExperienceLevel() == 0) {
            return 1;
        } else if(isExist(job.getTitle(), job.getCompanyId())){
            return 2;
        } else if(!(job.getJobStatus() == 0 || job.getJobStatus() == 1)) {
            return 3;
        }
        jobRepository.save(job);
        return 0;
    }

    //
    @Override
    public Integer update(Job job) {
        if (job.getSalary() == 0 || job.getExperienceLevel() == 0) {
            return 1;
        } else if((isExist(job.getTitle(), job.getCompanyId())) && (job.getId() != findByCompanyIdAndTitle(job.getCompanyId(), job.getTitle()).get().getId())){
            return 2;
        } else if(!(job.getJobStatus() == 0 || job.getJobStatus() == 1)) {
            return 3;
        }
        jobRepository.save(job);
        return 0;
    }

    @Override
    public void deleteById(Long id) {
        jobRepository.deleteById(id);
    }

    @Override
    public List<Job> findByTitle(String jobTitle) {
        return jobRepository.findByTitle(jobTitle);
    }

    @Override
    public Job findById(Long id) {
        return jobRepository.findById(id).orElse(null);
    }

//    public Integer isValidJob(Long jobId) {
//        Optional<Job> job = Optional.ofNullable(jobRepository.findById(jobId).orElse(null));
//        if(job.isPresent(job.getTitle())){
//            if (job.getTitle()) {
//                return 0;
//            } else {
//                return 1;
//            }
//        } else {
//            return -1;
//        }
//    }

    @Override
    public List<Job> findByCompanyId(Long id) {
        return jobRepository.findByCompanyId(id);
    }

    @Override
    public Optional<Job> findByCompanyIdAndTitle(Long companyId, String title) {
        return jobRepository.findByCompanyIdAndTitle(companyId, title);
    }

    public boolean isExist(Long jobId) {
        return jobRepository.findById(jobId).isPresent();
    }

    public boolean isExist(String jobTitle) {
        return !jobRepository.findByTitle(jobTitle).isEmpty();
    }

    public boolean isExist(Long jobId, Long companyId) {
        Optional<Job> job = jobRepository.findByIdAndCompanyId(jobId, companyId);
        return job.isPresent();
    }

    public boolean isExist(String jobTitle, Long companyId) {
        Optional<Job> job = jobRepository.findByCompanyIdAndTitle(companyId, jobTitle);
        return job.isPresent();
    }

    public boolean isOpen(Long jobId) {
        return findById(jobId).getJobStatus()==0;
    }

}

