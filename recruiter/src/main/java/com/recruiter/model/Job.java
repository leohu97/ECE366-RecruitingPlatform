package com.recruiter.model;

import javax.persistence.*;

@Entity
@Table(name = "job")

public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long salary;
    private String title;
    private String location;
    private Long experienceLevel;

    // 0: Job Open
    // 1: Job Closed
    private Integer jobStatus;
    private Long companyId;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Integer getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(Integer jobStatus) {
        this.jobStatus = jobStatus;
    }
}

