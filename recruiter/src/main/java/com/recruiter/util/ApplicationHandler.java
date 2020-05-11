package com.recruiter.util;

import com.recruiter.model.Application;
import com.recruiter.model.Job;
import com.recruiter.model.User;
import com.recruiter.repository.ApplicationRepository;
import com.recruiter.service.ApplicationService;
import com.recruiter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@Controller
public class ApplicationHandler {
    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/api/applications", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity applicationSearch(
            @RequestParam(name = "id", required = false) Long id,
            @RequestParam(name = "jobid", required = false) Long jobid,
            @RequestParam(name = "userid", required = false)  Long userid,
            @RequestParam(name = "applicationstatus", required = false) String applicationstatus) {
        if (null == id && null == jobid && null == userid && null == applicationstatus) {
            return new ResponseEntity("At least one parameter is required", HttpStatus.BAD_REQUEST);
        } else {
            Application application = new Application();
            application.setId(id);
            application.setJobId(jobid);
            application.setUserId(userid);
            application.setApplicationStatus(applicationstatus);

//            ExampleMatcher matcher = ExampleMatcher.matching()
//                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
//                    .withIgnoreCase(true)
//                    .withMatcher("title", ExampleMatcher.GenericPropertyMatchers.startsWith())
//                    .withIgnorePaths("focus");
//            Example<Job> ex = Example.of(job, matcher);

            Example<Application> ex = Example.of(application);

            List<Application> result = applicationRepository.findAll(ex);
            return new ResponseEntity(result, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/api/applications", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity addApplication(
            @RequestParam(name = "jobid") Long jobid) {
        if(null == jobid) {
            return new ResponseEntity<>("No Null Parameter Allowed", HttpStatus.BAD_REQUEST);
        }
        String currentUsername = userService.getCurrentUsername();
        Optional<User> currentUser = Optional.ofNullable(userService.findByUsername(currentUsername));
        if (!currentUser.isPresent()|| !currentUser.get().getAccountType().equals("Applicant")) {
            return new ResponseEntity<>("You are not authorized to post a new application", HttpStatus.FORBIDDEN);
        }

        Application application = new Application();
        application.setJobId(jobid);
        application.setUserId(currentUser.get().getId());
        application.setApplicationStatus("Pending");

        Integer applicationPostStatus;
        applicationPostStatus = applicationService.save(application, currentUser.get().getId());
        if (applicationPostStatus == 0) {
            return ResponseEntity.status(HttpStatus.OK).body("A new application has been posted!");
        } else if(applicationPostStatus == 1) {
            return new ResponseEntity<>("Application Already Exists!", HttpStatus.BAD_REQUEST);
        } else if(applicationPostStatus == 2) {
            return new ResponseEntity<>("Job Does Not Exist", HttpStatus.BAD_REQUEST);
        } else if(applicationPostStatus == 3) {
            return new ResponseEntity<>("Job Already Closed", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("Something Went Wrong", HttpStatus.NOT_FOUND);
        }
    }

//    @RequestMapping(value = "/api/applications", method = RequestMethod.PUT)
//    @ResponseBody
//    public ResponseEntity updateApplication(
//            @RequestParam(name = "id") Long id,
//            @RequestParam(name = "applicationstatus") String applicationstatus) {
//        String currentUsername = userService.getCurrentUsername();
//        if (!userService.isCompanyUser(currentUsername))
//            return new ResponseEntity<>("You are not authorized to update a job", HttpStatus.FORBIDDEN);
//        else {
//            Optional<User> currentUser = Optional.ofNullable(userService.findByUsername(currentUsername));
////            if (!applicationService.isExist(id, currentUser.get().getId())) {
//                return new ResponseEntity<>("Application Does Not Exist", HttpStatus.BAD_REQUEST);
//            } else {
//                Application application = applicationService.findById(id);
//                application.setApplicationStatus(applicationstatus);
//
//                Integer applicationUpdateStatus;
//                applicationUpdateStatus = applicationService.update(application);
//                if (applicationUpdateStatus == 0) {
//                    return ResponseEntity.status(HttpStatus.OK).body("A new job has been posted!");
//                } else if(applicationUpdateStatus == 1) {
//                    return new ResponseEntity<>("Salary/ExperienceLevel should not be 0", HttpStatus.FORBIDDEN);
//                } else if(applicationUpdateStatus == 2) {
//                    return new ResponseEntity<>("Job Title Already Existed", HttpStatus.BAD_REQUEST);
//                } else if(applicationUpdateStatus == 3) {
//                    return new ResponseEntity<>("Invalid Job Status", HttpStatus.BAD_REQUEST);
//                } else {
//                    return new ResponseEntity<>("Something Went Wrong", HttpStatus.NOT_FOUND);
//                }
//            }
//        }
//    }

    @RequestMapping(value = "/api/applications", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity deleteApplication(
            @RequestParam(name = "id") Long id) {
        String currentUsername = userService.getCurrentUsername();
        if (userService.isCompanyUser(currentUsername))
            return new ResponseEntity<>("You are not authorized to delete a job", HttpStatus.FORBIDDEN);
        else {
            Optional<User> currentUser = Optional.ofNullable(userService.findByUsername(currentUsername));
            Optional<Application> targetApplication = applicationService.findByIdAndUserId(id, currentUser.get().getId());
            if (targetApplication.isPresent()) {
                applicationService.deleteById(id);
                return new ResponseEntity<>("Application Deleted", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Application Does Not Exist", HttpStatus.BAD_REQUEST);
            }
        }
    }

}
//import com.google.gson.Gson;
//import com.recruiter.service.ApplicationService;
//import com.recruiter.service.UserService;
//import com.recruiter.service.JobService;
//import com.recruiter.store.ApplicationStore;
//import com.recruiter.store.UserStore;
//import com.recruiter.store.JobStore;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import com.recruiter.model.Application;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/applications")
//public class ApplicationHandler {
//
//    private static ApplicationStore applicationStore;
//    private static ApplicationService applicationService;
//    private static UserStore userStore;
//    private static UserService userService;
//    private static JobStore jobStore;
//    private static JobService jobService;
//
//    public ApplicationHandler() {
//        this.applicationStore = new ApplicationStore();
////        this.userStore = new UserStore(usersById);
//        this.jobStore = new JobStore();
//        this.applicationService = new ApplicationService(applicationStore);
//        this.userService = new UserService(userStore);
//        this.jobService = new JobService(jobStore);
//    }
//
//    @PostMapping("createApplicationPosting")
//    public ResponseEntity<String> createApplicationPosting(
//            @RequestParam(value = "jobId", defaultValue = "") Long jobId,
//            @RequestParam(value = "userId", defaultValue = "") Long userId,
//            @RequestParam(value = "applicationStatus", defaultValue = "Pending") String applicationStatus) {
//
//        int userStatus = userService.isValidUser(userId);
//        if (userStatus == 1) {
//            int jobStatus = jobService.isValidJob(jobId);
//            if (jobStatus == 0) {
//                ApplicationHandler.CreateApplicationRequest createApplicationRequest = new CreateApplicationRequest(jobId, userId, applicationStatus);
//                List<Application> applications = applicationService.getApplications(createApplicationRequest);
//                if (applications.size() != 0){
//                    return new ResponseEntity<>("Application already exists! This action can not be taken!", HttpStatus.FORBIDDEN);
//                } else {
//                    int ApplicationPostStatus = applicationService.addApplicationPosting(createApplicationRequest);
//                    if (ApplicationPostStatus == 0) {
//                        return ResponseEntity.status(HttpStatus.OK).body("A new Application has been posted!");
//                    } else {
//                        return new ResponseEntity<>("One of the required fields is blank!", HttpStatus.NO_CONTENT);
//                    }
//                }
//            } else if (jobStatus == 1) {
//                return new ResponseEntity<>("Job is closed! This action can not be taken!", HttpStatus.FORBIDDEN);
//            } else {
//                return new ResponseEntity<>("Job does not exists! This action can not be taken!", HttpStatus.FORBIDDEN);
//            }
//        } else if (userStatus == 0) {
//            return new ResponseEntity<>("Only applicant can add new Application postings.", HttpStatus.FORBIDDEN);
//        } else if (userStatus == -1) {
//            return new ResponseEntity<>("Account does not exists! This action can not be taken!", HttpStatus.FORBIDDEN);
//        } else {
//            return new ResponseEntity<>("ERROR!", HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @DeleteMapping("removeApplicationPosting")
//    public ResponseEntity<String> removeApplicationPosting(
//            @RequestParam(value = "ApplicationId", defaultValue = "") Long applicationId,
//            @RequestParam(value = "userId", defaultValue = "") Long userId,
//            @RequestParam(value = "applicationStatus", defaultValue = "") String applicationStatus) {
//        int status = userService.isValidUser(userId);
//        if (status == 1) {
//            if (applicationService.isValidApplication(applicationId)) {
//                applicationService.deleteApplication(applicationId);
//                return new ResponseEntity<>("The Application with id " + applicationId + "has been deleted!", HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>("The Application with the given id does not exist", HttpStatus.NOT_FOUND);
//            }
//        } else if (status == 0) {
//            return new ResponseEntity<>("Only applicant can remove Application postings.", HttpStatus.FORBIDDEN);
//        } else if (status == -1) {
//            return new ResponseEntity<>("Account does not exists! This action can not be taken!", HttpStatus.FORBIDDEN);
//        } else {
//            return new ResponseEntity<>("ERROR!", HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @GetMapping("/getApplications")
//    public ResponseEntity<String> getApplications(
//            @RequestParam(value = "jobId", defaultValue = "") Long jobId,
//            @RequestParam(value = "userId", defaultValue = "") Long userId,
//            @RequestParam(value = "applicationStatus", defaultValue = "") String applicationStatus) {
//
//        Gson gson = new Gson();
//        CreateApplicationRequest application = new CreateApplicationRequest(jobId, userId, applicationStatus);
//        List<Application> applications = applicationService.getApplications(application);
//        if (applications.size() == 0){
//            return new ResponseEntity<>("Application does not exists! This action can not be taken!", HttpStatus.NOT_FOUND);
//        } else {
//            String json = gson.toJson(applications);
//            return new ResponseEntity<>(json, HttpStatus.OK);
//        }
//
//    }
//
//    public class CreateApplicationRequest {
//        private Long jobId;
//        private Long userId;
//        private String applicationStatus;
//
//        public CreateApplicationRequest(Long jobId, Long userId, String applicationStatus) {
//            this.jobId = jobId;
//            this.userId = userId;
//            this.applicationStatus = applicationStatus;
//        }
//
//        public String getApplicationStatus() {
//            return applicationStatus;
//        }
//
//        public Long getJobId() {
//            return jobId;
//        }
//
//        public Long getUserId() {
//            return userId;
//        }
//    }
//
//
//}
