package com.recruiter.util;
import com.recruiter.model.User;
import com.recruiter.store.UserStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.recruiter.model.Job;
import com.recruiter.store.JobStore;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobHandler {

    private static JobStore jobStore = new JobStore();
    private static User user;
    private static UserStore userStore;
    //private static WebClient client = WebClient.builder().baseUrl("http://localhost:8080").build();


    public JobHandler() {
    }

    @PostMapping("createJobPosting")
    public ResponseEntity<String> createJobPosting(
            @RequestParam(value = "company", defaultValue="") String company,
            @RequestParam(value = "title", defaultValue = "") String title,
            @RequestParam(value = "salary", defaultValue = "0") Long salary,
            Long userId) {

        JobHandler.CreateJobRequest createJobRequest = new CreateJobRequest(company, title, salary);
        Job job = jobStore.addJob(createJobRequest);
        return ResponseEntity.status(HttpStatus.OK).body("A new job has been posted!");
//            return new ResponseEntity<>("User is not a company", HttpStatus.FORBIDDEN);




    }

    @DeleteMapping("removeJobPosting")
    public ResponseEntity<String> removeJobPosting(
            @RequestParam(value = "jobId", defaultValue="") Long jobId) {

        return ResponseEntity.status(HttpStatus.OK).body("The specified job has been deleted!");


    }

    @RequestMapping(value = "/getOpenJobs", method ={RequestMethod.GET})
    public ResponseEntity<String> getOpenJobs(
            @RequestParam(value = "company", defaultValue="") String company,
            @RequestParam(value = "title", defaultValue = "") String title,
            @RequestParam(value = "salary", defaultValue = "0") Long salary) {

        List<Job> jobList = jobStore.getJobs();
        String outStr = "";
        for(int i = 0; i<jobList.size(); i++){
            Job curJob = jobList.get(i);
            outStr = outStr + curJob.getJobId() + " " + curJob.getCompany() + " " + curJob.getTitle() + " " + curJob.getSalary() + "\n";
        }
        return new ResponseEntity<>(outStr, HttpStatus.OK);
    }

    public class CreateJobRequest {
        private String company;
        private String title;
        private Long salary;

        public CreateJobRequest(String company, String title, Long salary) {
            this.company = company;
            this.title = title;
            this.salary = salary;
        }

        public String getCompany() {
            return company;
        }

        public String getTitle() {
            return title;
        }

        public Long getSalary() {
            return salary;
        }
    }


}
