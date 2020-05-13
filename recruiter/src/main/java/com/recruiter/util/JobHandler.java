package com.recruiter.util;

import com.recruiter.model.Job;
import com.recruiter.model.User;
import com.recruiter.repository.JobRepository;
import com.recruiter.service.JobService;
import com.recruiter.service.UserService;
import com.recruiter.validator.JobValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class JobHandler {
    @Autowired
    private JobValidator jobValidator;

    @Autowired
    private JobService jobService;

    @Autowired
    private UserService userService;

    @Autowired
    private JobRepository jobRepository;

    @RequestMapping(value = "/api/jobs", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity jobSearch(
            @RequestParam(name = "salary", required = false) Long salary,
            @RequestParam(name = "minsalary", required = false) Long minsalary,
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "location", required = false)  String location,
            @RequestParam(name = "experiencelevel", required = false) Long experienceLevel,
            @RequestParam(name = "companyid", required = false) Long companyId) {
        if (null == salary && null == title && null == location && null == experienceLevel && null == companyId && null == minsalary) {
            return new ResponseEntity("At least one parameter is required", HttpStatus.BAD_REQUEST);
        } else {
            Job job = new Job();
            job.setSalary(salary);
            job.setTitle(title);
            job.setLocation(location);
            job.setExperienceLevel(experienceLevel);
            job.setCompanyId(companyId);

//            ExampleMatcher matcher = ExampleMatcher.matching()
//                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
//                    .withIgnoreCase(true)
//                    .withMatcher("title", ExampleMatcher.GenericPropertyMatchers.startsWith())
//                    .withIgnorePaths("focus");
//            Example<Job> ex = Example.of(job, matcher);

            Example<Job> ex = Example.of(job);

            List<Job> result = jobRepository.findAll(ex);

            if (minsalary != null && salary == null) {
                List<Job> filteredjobs =
                        result.stream()
                                .filter(t -> t.getSalary() >= minsalary)
                                .collect(Collectors.toList());
                return new ResponseEntity(filteredjobs, HttpStatus.OK);
            }

            return new ResponseEntity(result, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/api/jobs/{jobId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getJob(
            @PathVariable(name = "jobId") Long id) {
        Job job = jobService.findById(id);
        if (job == null) {
            return new ResponseEntity<>("Job Does Not Exist", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity(job, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/api/jobs/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity updateJob(
            @RequestParam(name = "id") Long id,
            @RequestParam(name = "salary", required = false) Long salary,
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "location", required = false)  String location,
            @RequestParam(name = "experienceLevel", required = false) Long experienceLevel,
            @RequestParam(name = "jobStatus", required = false) Integer jobStatus,
            @RequestParam(name = "description", required = false) String description) {
        String currentUsername = userService.getCurrentUsername();
        if (null == salary && null == title && null == location && null == experienceLevel && null == jobStatus && null == description) {
            return new ResponseEntity("At least one parameter is required", HttpStatus.BAD_REQUEST);
        } else if((title != null && title.isEmpty()) || (location != null && location.isEmpty()) ||(description != null && description.isEmpty())) {
            return new ResponseEntity<>("No Null Parameter Allowed", HttpStatus.BAD_REQUEST);
        } else if (!userService.isCompanyUser(currentUsername))
            return new ResponseEntity<>("You are not authorized to update a job", HttpStatus.FORBIDDEN);
        else {
            Optional<User> currentUser = Optional.ofNullable(userService.findByUsername(currentUsername));
            if (!jobService.isExist(id, currentUser.get().getId())) {
                return new ResponseEntity<>("Job Does Not Exist", HttpStatus.BAD_REQUEST);
            } else {
                Job job = jobService.findById(id);
                if (salary != null) {
                    job.setSalary(salary);
                }
                if (title != null) {
                    job.setTitle(title);
                }
                if (location != null) {
                    job.setLocation(location);
                }
                if (experienceLevel != null) {
                    job.setExperienceLevel(experienceLevel);
                }
                if (jobStatus != null) {
                    job.setJobStatus(jobStatus);
                }
                if (jobStatus != null) {
                    job.setDescription(description);
                }
                Integer jobUpdateStatus;
                jobUpdateStatus = jobService.update(job);
                if (jobUpdateStatus == 0) {
                    return ResponseEntity.status(HttpStatus.OK).body("A new job has been posted!");
                } else if(jobUpdateStatus == 1) {
                    return new ResponseEntity<>("Salary/ExperienceLevel should not be 0", HttpStatus.FORBIDDEN);
                } else if(jobUpdateStatus == 2) {
                    return new ResponseEntity<>("Job Title Already Existed", HttpStatus.BAD_REQUEST);
                } else if(jobUpdateStatus == 3) {
                    return new ResponseEntity<>("Invalid Job Status", HttpStatus.BAD_REQUEST);
                } else {
                    return new ResponseEntity<>("Something Went Wrong", HttpStatus.NOT_FOUND);
                }
            }
        }
    }


    @RequestMapping(value = "/api/jobs", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity addJob(
            @RequestParam(name = "salary") Long salary,
            @RequestParam(name = "title") String title,
            @RequestParam(name = "location")  String location,
            @RequestParam(name = "experienceLevel") Long experienceLevel,
            @RequestParam(name = "description") String description) {

        if(null == salary || title.isEmpty() || location.isEmpty() || null == experienceLevel || description.isEmpty()) {
            return new ResponseEntity<>("No Null Parameter Allowed", HttpStatus.BAD_REQUEST);
        }
        String currentUsername = userService.getCurrentUsername();
        Optional<User> currentUser = Optional.ofNullable(userService.findByUsername(currentUsername));
        if (!currentUser.isPresent()|| currentUser.get().getAccountType().equals("Applicant")) {
            return new ResponseEntity<>("You are not authorized to post a new job", HttpStatus.FORBIDDEN);
        }

        Job job = new Job();
        job.setSalary(salary);
        job.setTitle(title);
        job.setLocation(location);
        job.setExperienceLevel(experienceLevel);
        job.setJobStatus(0);
        job.setCompanyId(currentUser.get().getId());
        job.setDescription(description);

        Integer jobPostStatus;
        jobPostStatus = jobService.save(job);
        if (jobPostStatus == 0) {
            return ResponseEntity.status(HttpStatus.OK).body("A new job has been posted!");
        } else if(jobPostStatus == 1) {
            return new ResponseEntity<>("Salary/ExperienceLevel should not be 0", HttpStatus.FORBIDDEN);
        } else if(jobPostStatus == 2) {
            return new ResponseEntity<>("Job Title Already Existed", HttpStatus.BAD_REQUEST);
        } else if(jobPostStatus == 3) {
            return new ResponseEntity<>("Invalid Job Status", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("Something Went Wrong", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/api/jobs/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity deleteJob(
            @RequestParam(name = "title") String title) {
        String currentUsername = userService.getCurrentUsername();
        if (!userService.isCompanyUser(currentUsername))
            return new ResponseEntity<>("You are not authorized to delete a job", HttpStatus.FORBIDDEN);
        else {
            Optional<User> currentUser = Optional.ofNullable(userService.findByUsername(currentUsername));
            if (!jobService.isExist(title, currentUser.get().getId())) {
                return new ResponseEntity<>("Job Title Does Not Exist", HttpStatus.BAD_REQUEST);
            } else {
                Optional<Job> targetJob = jobService.findByCompanyIdAndTitle(currentUser.get().getId(),title);
                Long jobId = targetJob.get().getId();
                if (currentUser.get().getId().equals(targetJob.get().getCompanyId())){
                    jobService.deleteById((long) jobId);
                    return ResponseEntity.status(HttpStatus.OK).body("A job has been deleted");
                } else {
                    return new ResponseEntity<>("You are not authorized to delete this job", HttpStatus.FORBIDDEN);
                }

            }
        }
    }






}
//    private static JobStore jobStore;
//    private static JobService jobService;
//    private static JobStore jobStore;
//    private static JobService jobService;
//
//    public JobHandler() {
//        this.jobStore = new JobStore();
////        this.jobStore = new JobStore(jobsById);
//        this.jobService = new JobService(jobStore);
//        this.jobService = new JobService(jobStore);
//    }
//
//    @PostMapping("createJobPosting")
//    public ResponseEntity<String> createJobPosting(
//            @RequestParam(value = "company", defaultValue = "") String company,
//            @RequestParam(value = "title", defaultValue = "") String title,
//            @RequestParam(value = "salary", defaultValue = "0") Long salary,
//            @RequestParam(value = "jobId", defaultValue = "-1") Long jobId,
//            @RequestParam(value = "location", defaultValue = "") String location,
//            @RequestParam(value = "experienceLevel", defaultValue="0") Long experienceLevel,
//            @RequestParam(value = "jobStatus", defaultValue="Open") String jobStatus) {
//
//        int status = jobService.isValidJob(jobId);
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
//            @RequestParam(value = "jobId", defaultValue = "") Long jobId) {
//        int status = jobService.isValidJob(jobId);
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
