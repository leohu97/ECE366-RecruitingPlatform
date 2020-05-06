//package com.recruiter.util;
//import com.google.gson.Gson;
//import com.recruiter.service.JobService;
//import com.recruiter.service.UserService;
//import com.recruiter.store.UserStore;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import com.recruiter.model.Job;
//import com.recruiter.store.JobStore;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/jobs")
//public class JobHandler {
//
//    private static JobStore jobStore;
//    private static JobService jobService;
//    private static UserStore userStore;
//    private static UserService userService;
//
//    public JobHandler() {
//        this.jobStore = new JobStore();
////        this.userStore = new UserStore(usersById);
//        this.jobService = new JobService(jobStore);
//        this.userService = new UserService(userStore);
//    }
//
//    @PostMapping("createJobPosting")
//    public ResponseEntity<String> createJobPosting(
//            @RequestParam(value = "company", defaultValue = "") String company,
//            @RequestParam(value = "title", defaultValue = "") String title,
//            @RequestParam(value = "salary", defaultValue = "0") Long salary,
//            @RequestParam(value = "userId", defaultValue = "-1") Long userId,
//            @RequestParam(value = "location", defaultValue = "") String location,
//            @RequestParam(value = "experienceLevel", defaultValue="0") Long experienceLevel,
//            @RequestParam(value = "jobStatus", defaultValue="Open") String jobStatus) {
//
//        int status = userService.isValidUser(userId);
//        if (status == 0) {
//            JobHandler.CreateJobRequest createJobRequest = new CreateJobRequest(salary, company, title, location, experienceLevel, jobStatus);
//            int jobPostStatus = jobService.addJobPosting(createJobRequest);
//            if (jobPostStatus == 0) {
//                return ResponseEntity.status(HttpStatus.OK).body("A new job has been posted!");
//            } else {
//                return new ResponseEntity<>("One of the required fields is blank!", HttpStatus.NO_CONTENT);
//            }
//        } else if (status == 1) {
//            return new ResponseEntity<>("Only companies can add new job postings.", HttpStatus.FORBIDDEN);
//        } else if (status == -1) {
//            return new ResponseEntity<>("Account does not exists! This action can not be taken!", HttpStatus.FORBIDDEN);
//        } else {
//            return new ResponseEntity<>("ERROR!", HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @DeleteMapping("removeJobPosting")
//    public ResponseEntity<String> removeJobPosting(
//            @RequestParam(value = "jobId", defaultValue = "") Long jobId,
//            @RequestParam(value = "userId", defaultValue = "") Long userId) {
//        int status = userService.isValidUser(userId);
//        if (status == 0) {
//            if (jobService.isValidJob(jobId) == 0) {
//                jobService.deleteJob(jobId);
//                return new ResponseEntity<>("The job with id " + jobId + "has been deleted!", HttpStatus.OK);
//            } else if (jobService.isValidJob(jobId) == -1){
//                return new ResponseEntity<>("The job with the given id does not exist", HttpStatus.NOT_FOUND);
//            } else {
//                return new ResponseEntity<>("The job is closed", HttpStatus.NOT_FOUND);
//            }
//        } else {
//            return new ResponseEntity<>("Only companies can remove job postings.", HttpStatus.FORBIDDEN);
//        }
//    }
//
//    @GetMapping("/getJobs")
//    public ResponseEntity<String> getOpenJobs(
//            @RequestParam(value = "company", defaultValue = "") String company,
//            @RequestParam(value = "title", defaultValue = "") String title,
//            @RequestParam(value = "salary", defaultValue = "0") Long salary,
//            @RequestParam(value = "location", defaultValue = "") String location,
//            @RequestParam(value = "experienceLevel", defaultValue = "100") Long experienceLevel,
//            @RequestParam(value = "jobStatus", defaultValue = "") String jobStatus) {
//        Gson gson = new Gson();
//        CreateJobRequest job = new CreateJobRequest(salary, company, title, location, experienceLevel, jobStatus);
//        List<Job> jobs = jobService.getJobs(job);
//        String json = gson.toJson(jobs);
//        return new ResponseEntity<>(json, HttpStatus.OK);
//    }
//
//    public class CreateJobRequest {
//        private Long salary;
//        private String company;
//        private String title;
//        private String location;
//        private Long experienceLevel;
//        private String jobStatus;
//
//        public CreateJobRequest(Long salary, String company, String title, String location, Long experienceLevel, String jobStatus) {
//            this.salary = salary;
//            this.company = company;
//            this.title = title;
//            this.location = location;
//            this.experienceLevel = experienceLevel;
//            this.jobStatus = jobStatus;
//        }
//
//        public Long getSalary() {
//            return salary;
//        }
//
//        public String getCompany() {
//            return company;
//        }
//
//        public String getTitle() {
//            return title;
//        }
//
//        public String getLocation() {
//            return location;
//        }
//
//        public Long getExperienceLevel() {
//            return experienceLevel;
//        }
//
//        public String getJobStatus() {
//            return jobStatus;
//        }
//    }
//
//
//}
