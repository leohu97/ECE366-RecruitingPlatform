package com.recruiter.util;
import com.google.gson.Gson;
import com.recruiter.service.ApplicationService;
import com.recruiter.service.UserService;
import com.recruiter.service.JobService;
import com.recruiter.store.ApplicationStore;
import com.recruiter.store.UserStore;
import com.recruiter.store.JobStore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.recruiter.model.Application;

import java.util.List;

@RestController
@RequestMapping("/applications")
public class ApplicationHandler {

    private static ApplicationStore applicationStore;
    private static ApplicationService applicationService;
    private static UserStore userStore;
    private static UserService userService;
    private static JobStore jobStore;
    private static JobService jobService;

    public ApplicationHandler() {
        this.applicationStore = new ApplicationStore();
        this.userStore = new UserStore();
        this.jobStore = new JobStore();
        this.applicationService = new ApplicationService(applicationStore);
        this.userService = new UserService(userStore);
        this.jobService = new JobService(jobStore);
    }

    @PostMapping("createApplicationPosting")
    public ResponseEntity<String> createApplicationPosting(
            @RequestParam(value = "jobId", defaultValue = "") Long jobId,
            @RequestParam(value = "userId", defaultValue = "") Long userId,
            @RequestParam(value = "applicationStatus", defaultValue = "Pending") String applicationStatus) {

        int userStatus = userService.isValidUser(userId);
        if (userStatus == 1) {
            int jobStatus = jobService.isValidJob(jobId);
            if (jobStatus == 0) {
                ApplicationHandler.CreateApplicationRequest createApplicationRequest = new CreateApplicationRequest(jobId, userId, applicationStatus);
                int ApplicationPostStatus = applicationService.addApplicationPosting(createApplicationRequest);
                if (ApplicationPostStatus == 0) {
                    return ResponseEntity.status(HttpStatus.OK).body("A new Application has been posted!");
                } else {
                    return new ResponseEntity<>("One of the required fields is blank!", HttpStatus.NO_CONTENT);
                }
            } else if (jobStatus == 1) {
                return new ResponseEntity<>("Job is closed! This action can not be taken!", HttpStatus.FORBIDDEN);
            } else {
                return new ResponseEntity<>("Job does not exists! This action can not be taken!", HttpStatus.FORBIDDEN);
            }
        } else if (userStatus == 0) {
            return new ResponseEntity<>("Only applicant can add new Application postings.", HttpStatus.FORBIDDEN);
        } else if (userStatus == -1) {
            return new ResponseEntity<>("Account does not exists! This action can not be taken!", HttpStatus.FORBIDDEN);
        } else {
            return new ResponseEntity<>("ERROR!", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("removeApplicationPosting")
    public ResponseEntity<String> removeApplicationPosting(
            @RequestParam(value = "ApplicationId", defaultValue = "") Long applicationId,
            @RequestParam(value = "userId", defaultValue = "") Long userId,
            @RequestParam(value = "applicationStatus", defaultValue = "") String applicationStatus) {
        int status = userService.isValidUser(userId);
        if (status == 1) {
            if (applicationService.isValidApplication(applicationId)) {
                applicationService.deleteApplication(applicationId);
                return new ResponseEntity<>("The Application with id " + applicationId + "has been deleted!", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("The Application with the given id does not exist", HttpStatus.NOT_FOUND);
            }
        } else if (status == 0) {
            return new ResponseEntity<>("Only applicant can remove Application postings.", HttpStatus.FORBIDDEN);
        } else if (status == -1) {
            return new ResponseEntity<>("Account does not exists! This action can not be taken!", HttpStatus.FORBIDDEN);
        } else {
            return new ResponseEntity<>("ERROR!", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getOpenApplications")
    public ResponseEntity<String> getOpenApplications(
            @RequestParam(value = "jobId", defaultValue = "") Long jobId,
            @RequestParam(value = "userId", defaultValue = "") Long userId,
            @RequestParam(value = "applicationStatus", defaultValue = "") String applicationStatus) {

        Gson gson = new Gson();
        CreateApplicationRequest application = new CreateApplicationRequest(jobId, userId, applicationStatus);
        List<Application> applications = applicationService.getOpenApplications(application);
        String json = gson.toJson(applications);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    public class CreateApplicationRequest {
        private Long jobId;
        private Long userId;
        private String applicationStatus;

        public CreateApplicationRequest(Long jobId, Long userId, String applicationStatus) {
            this.jobId = jobId;
            this.userId = userId;
            this.applicationStatus = applicationStatus;
        }

        public String getApplicationStatus() {
            return applicationStatus;
        }

        public Long getJobId() {
            return jobId;
        }

        public Long getUserId() {
            return userId;
        }
    }


}
