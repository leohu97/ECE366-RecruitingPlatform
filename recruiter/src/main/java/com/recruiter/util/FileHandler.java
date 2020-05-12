package com.recruiter.util;

import com.recruiter.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller
public class FileHandler {

    @Autowired
    FileService fileService;

    @Autowired
    private HttpServletRequest request;


//    @RequestMapping(value = "/api/file", method = RequestMethod.POST)
//    @ResponseBody
//    public ResponseEntity<String> applicationSearch(
//            @RequestParam("file") MultipartFile file) {
//        String uploadsDir = "/uploads/";
//        String realPathtoUploads =  request.getServletContext().getRealPath(uploadsDir);
//
//        fileService.uploadFile(realPathtoUploads, file);
//
//        return new ResponseEntity("Filed uploaded", HttpStatus.OK);
//    }

    @RequestMapping(value = "/api/file", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> applicationSearch(
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
