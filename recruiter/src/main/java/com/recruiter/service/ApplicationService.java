//package com.recruiter.service;
//
//import com.recruiter.model.Application;
//import com.recruiter.model.User;
//import com.recruiter.model.Job;
//import com.recruiter.store.ApplicationStore;
//import com.recruiter.store.UserStore;
//import com.recruiter.store.JobStore;
//import com.recruiter.util.ApplicationHandler;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Optional;
//import java.util.concurrent.ConcurrentMap;
//
//public class ApplicationService {
//
//    private ApplicationStore ApplicationStore;
//
//    public ApplicationService(ApplicationStore ApplicationStore) {
//        this.ApplicationStore = ApplicationStore;
//    }
//
//    // -1 : blank field
//    // 0 : Application created
//    public int addApplicationPosting(ApplicationHandler.CreateApplicationRequest createApplicationRequest) {
//        if (createApplicationRequest.getJobId().equals("")
//                || createApplicationRequest.getUserId().equals("")) {
//            return -1;
//        } else {
//            ApplicationStore.addApplication(createApplicationRequest);
//            return 0;
//        }
//    }
//
//    public boolean isValidApplication(Long applicationId) {
//        Optional<Application> Application = Optional.ofNullable(ApplicationStore.findApplicationById(applicationId));
//        if(Application.isPresent()){
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    public void deleteApplication(Long applicationId) {
//        ApplicationStore.removeApplication(applicationId);
//    }
//
//    public List<Application> getApplications(ApplicationHandler.CreateApplicationRequest application) {
//        ConcurrentMap<Long, Application> applications = ApplicationStore.getApplicationsById();
//        List<Application> openApplications = new ArrayList<Application>();
//        Iterator<Long> it1 = applications.keySet().iterator();
//        while(it1.hasNext()) {
//            Long key = it1.next();
//            Application curApplication = applications.get(key);
//            if (curApplication.getJobId() == application.getJobId()
//                    && application.getUserId() == curApplication.getUserId()
//                    ) {
//                openApplications.add(curApplication);
//            }
//        }
//        return openApplications;
//    }
//
//    public boolean isParameter(String s1, String s2) {
//        s1 = s1.toUpperCase();
//        s2 = s2.toUpperCase();
//        if (s1.equals(s2)) {
//            return true;
//        } else if(s1.contains(s2)) {
//            return true;
//        } else if(s2.length() == 0) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//}
