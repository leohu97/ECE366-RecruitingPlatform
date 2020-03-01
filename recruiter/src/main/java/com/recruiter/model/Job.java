package com.recruiter.model;

public class Job {
    private Long salary;
    private String company;
    private String title;
    private Long jobId;

    public Job(Long jobId, String company, String title, Long salary) {
        this.salary = salary;
        this.company = company;
        this.title = title;
        this.jobId = jobId;
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Job)) {
            return false;
        }
        Job other = (Job) obj;
        return super.equals(other)
                || this.jobId.equals(other.getJobId())
                && this.company.equals(other.getCompany())
                && this.salary.equals(other.getSalary())
                && this.title.equals(other.getTitle());
    }

    public Long getJobId() {
        return jobId;
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
}
