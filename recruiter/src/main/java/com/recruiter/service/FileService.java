package com.recruiter.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.GenericServlet;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class FileService {



    public void uploadFile(String uploadDir, MultipartFile file, String filename) throws IOException {

        byte[] bytes = file.getBytes();
        String uploadsDir = "/uploads/";
        String insPath = uploadDir + filename; // Directory path where you want to save ;
        Files.write(Paths.get(insPath), bytes);
    }
}