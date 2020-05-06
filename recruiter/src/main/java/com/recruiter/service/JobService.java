//package com.recruiter.service;
//
//import com.recruiter.model.Job;
//import com.recruiter.model.User;
//import com.recruiter.store.JobStore;
//import com.recruiter.store.UserStore;
//import com.recruiter.util.JobHandler;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Optional;
//import java.util.concurrent.ConcurrentMap;
//
//public class JobService {
//
//    private JobStore jobStore;
//
//    public JobService(JobStore jobStore) {
//        this.jobStore = jobStore;
//    }
//
//    // -1 : blank field
//    // 0 : job created
//    public int addJobPosting(JobHandler.CreateJobRequest createJobRequest) {
//        if (createJobRequest.getCompany().equals("")
//            || createJobRequest.getSalary().equals("")
//            || createJobRequest.getTitle().equals("")) {
//            return -1;
//        } else {
//            jobStore.addJob(createJobRequest);
//            return 0;
//        }
//    }
//
//    // -1 : job does not exist
//    // 0 : job exist and opened
//    // 1 : job exist but closed
//    public int isValidJob(Long jobId) {
//        Optional<Job> job = Optional.ofNullable(jobStore.findJobById(jobId));
//        if(job.isPresent()){
//            if (job.get().getJobStatus() == "Open") {
//                return 0;
//            } else {
//                return 1;
//            }
//        } else {
//            return -1;
//        }
//    }
//
//    public void deleteJob(Long jobId) {
//        jobStore.removeJob(jobId);
//    }
//
//    public List<Job> getJobs(JobHandler.CreateJobRequest job) {
//        ConcurrentMap<Long, Job> jobs = jobStore.getJobsById();
//        List<Job> openJobs = new ArrayList<Job>();
//        Iterator<Long> it1 = jobs.keySet().iterator();
//        while(it1.hasNext()) {
//            Long key = it1.next();
//            Job curJob = jobs.get(key);
//            if (isParameter(curJob.getLocation(), job.getLocation())
//                    && isParameter(curJob.getCompany(), job.getCompany())
//                    && isParameter(curJob.getTitle(), job.getTitle())
//                    && isParameter(curJob.getJobStatus(), job.getJobStatus())
//                    && job.getExperienceLevel() >= curJob.getExperienceLevel()
//                    && job.getSalary() <= curJob.getSalary()) {
//                openJobs.add(curJob);
//            }
//        }
//        return openJobs;
//    }
//
//    public boolean isParameter(String s1, String s2) {
//        s1 = s1.toUpperCase();
//        s2 = s2.toUpperCase();
//        if (s1.equals(s2)) {
//            return true;
//        } else if(s1.contains(s2)) {
//            return true;
//        } else if(s2.length() == 0) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//}
