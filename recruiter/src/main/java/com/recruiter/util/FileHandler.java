package com.recruiter.util;

import com.recruiter.model.Application;
import com.recruiter.model.User;
import com.recruiter.service.ApplicationService;
import com.recruiter.service.FileService;
import com.recruiter.service.JobService;
import com.recruiter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
public class FileHandler {

    @Autowired
    FileService fileService;

    @Autowired
    UserService userService;

    @Autowired
    ApplicationService applicationService;

    @Autowired
    JobService jobService;

    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = "/api/file", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
    @ResponseBody
    public ResponseEntity getApplicationFile(
            @RequestParam(name = "id") Long id) throws IOException {
        String currentUsername = userService.getCurrentUsername();
        Optional<User> currentUser = Optional.ofNullable(userService.findByUsername(currentUsername));
        if (!currentUser.isPresent()|| currentUser.get().getAccountType().equals("Applicant")) {
            return new ResponseEntity<>("You are not authorized to get an attached file", HttpStatus.FORBIDDEN);
        } else if (applicationService.isExist(id)) {
            Long currentuserId = currentUser.get().getId();
            Application application = applicationService.findById(id);
            Long jobId = application.getJobId();
            Long companyId = jobService.findById(jobId).getCompanyId();
            if (currentuserId != companyId) {
                return new ResponseEntity("Application Does Not Exist", HttpStatus.NOT_FOUND);
            } else {
                String uploadsDir = "/uploads/";
                String filename = id + ".pdf";
                String realPathtoUploads =  request.getServletContext().getRealPath(uploadsDir);
                byte[] contents = Files.readAllBytes(Paths.get(realPathtoUploads + filename));
                ResponseEntity<byte[]> response = new ResponseEntity<>(contents, HttpStatus.OK);
                return response;
            }
        } else
            return new ResponseEntity("Application Does Not Exist", HttpStatus.NOT_FOUND);

    }

    @RequestMapping(value = "/api/file", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> postApplicationFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("filename") String filename) throws IOException {
        byte[] bytes = file.getBytes();
        String uploadsDir = "/uploads/";
        String realPathtoUploads =  request.getServletContext().getRealPath(uploadsDir);
        String insPath = realPathtoUploads + filename; // Directory path where you want to save ;
        Files.write(Paths.get(insPath), bytes);

        return ResponseEntity.ok(filename);
    }

}
