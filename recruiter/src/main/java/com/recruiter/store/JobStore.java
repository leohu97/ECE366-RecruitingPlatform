//package com.recruiter.store;
//
//import com.recruiter.model.Job;
//import com.recruiter.util.JobHandler;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.io.IOException;
//import java.nio.charset.Charset;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.ConcurrentMap;
//import java.util.concurrent.atomic.AtomicLong;
//import java.util.stream.Collectors;
//
//
//public class JobStore {
//
//    private final ConcurrentMap<Long, Job> jobsById;
//    private final AtomicLong nextId = new AtomicLong(4);
//
//    public JobStore(){
//        this.jobsById =
//                List.of(
//                        new Job(1L, 100L, "Cooper Union", "Student", "NYC", 3L, "Open"),
//                        new Job(2L, 200L, "Cooper Union", "Adjunct", "NYC", 5L, "Closed"),
//                        new Job(3L, 400L, "Cooper Union", "Professor", "NYC", 10L, "Open"))
//                        .stream()
//                        .collect(Collectors.toConcurrentMap(job -> job.getJobId(), job -> job));
//    }
//
//    public List<Job> getJobs() {
//        return new ArrayList<>(jobsById.values());
//    }
//
//    public Job findJobById(Long jobId) {
//        return jobsById.getOrDefault(jobId, null);
//    }
//
//    public void removeJob(Long jobId) {
//        jobsById.remove(jobId);
//    }
//
//    public Job addJob(final JobHandler.CreateJobRequest createJobRequest) {
//        long jobId = nextId.getAndIncrement();
//        Job job = new Job(jobId, createJobRequest.getSalary(),
//                createJobRequest.getCompany(),
//                createJobRequest.getTitle(),
//                createJobRequest.getLocation(),
//                createJobRequest.getExperienceLevel(),
//                createJobRequest.getJobStatus());
//        jobsById.put(jobId, job);
//        return job;
//    }
//
//    public ConcurrentMap<Long, Job> getJobsById() {
//        return jobsById;
//    }
//}
//
