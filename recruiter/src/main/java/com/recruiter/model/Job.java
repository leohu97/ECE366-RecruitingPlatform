package com.recruiter.model;

import java.util.Objects;

public class Job {
    private Long jobId;
    private Long salary;
    private String company;
    private String title;
    private String location;
    private Long experienceLevel;
    private String jobStatus;

    public Job(Long jobId, Long salary, String company, String title, String location, Long experienceLevel, String jobStatus) {
        this.jobId = jobId;
        this.salary = salary;
        this.company = company;
        this.title = title;
        this.location = location;
        this.experienceLevel = experienceLevel;
        this.jobStatus = jobStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Job job = (Job) o;
        return Objects.equals(salary, job.salary) &&
                Objects.equals(company, job.company) &&
                Objects.equals(title, job.title) &&
                Objects.equals(jobId, job.jobId) &&
                Objects.equals(location, job.location) &&
                Objects.equals(experienceLevel, job.experienceLevel) &&
                Objects.equals(jobStatus, job.jobStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(salary, company, title, jobId, location, experienceLevel, jobStatus);
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getExperienceLevel() {
        return experienceLevel;
    }

    public void setExperienceLevel(Long experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }
}

