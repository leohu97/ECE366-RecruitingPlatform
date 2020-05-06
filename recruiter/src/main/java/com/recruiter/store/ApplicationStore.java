//package com.recruiter.store;
//
//import com.recruiter.model.Application;
//import com.recruiter.util.ApplicationHandler;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.io.IOException;
//import java.nio.charset.Charset;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.ConcurrentMap;
//import java.util.concurrent.atomic.AtomicLong;
//import java.util.stream.Collectors;
//
//
//public class ApplicationStore {
//
//    private final ConcurrentMap<Long, Application> ApplicationsById;
//    private final AtomicLong nextId = new AtomicLong(4);
//
//    public ApplicationStore(){
//        this.ApplicationsById =
//                List.of(
//                        new Application(1L, 1L, 1L, "Pending"),
//                        new Application(2L, 2L, 2L, "Rejected" ),
//                        new Application(3L, 3L, 3L, "Accepted"))
//                        .stream()
//                        .collect(Collectors.toConcurrentMap(Application -> Application.getApplicationId(), Application -> Application));
//    }
//
//    public List<Application> getApplications() {
//        return new ArrayList<>(ApplicationsById.values());
//    }
//
//    public Application findApplicationById(Long ApplicationId) {
//        return ApplicationsById.getOrDefault(ApplicationId, null);
//    }
//
//    public void removeApplication(Long ApplicationId) {
//        ApplicationsById.remove(ApplicationId);
//    }
//
//    public Application addApplication(final ApplicationHandler.CreateApplicationRequest createApplicationRequest) {
//        long applicationId = nextId.getAndIncrement();
//        Application application = new Application(applicationId, createApplicationRequest.getJobId(),
//                createApplicationRequest.getUserId(),
//                createApplicationRequest.getApplicationStatus());
//        ApplicationsById.put(applicationId, application);
//        return application;
//    }
//
//    public ConcurrentMap<Long, Application> getApplicationsById() {
//        return ApplicationsById;
//    }
//}
//
