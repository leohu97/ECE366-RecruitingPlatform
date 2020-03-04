package com.recruiter.model;

public class Application {
    private Long applicationId;
    private Long jobId;
    private Long userId;
    private String applicationStatus;

    public Application(Long applicationId, Long jobId, Long userId, String applicationStatus) {
        this.applicationId = applicationId;
        this.jobId = jobId;
        this.userId = userId;
        this.applicationStatus = applicationStatus;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
