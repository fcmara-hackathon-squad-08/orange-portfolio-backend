package com.squad8.s8orangebackend.service;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

@Service
public class S3Services {

    @Value("${bucketName}")
    private String nomeDobucket;
    private static final String URL = "https://s3.amazonaws.com/";
    @Autowired
    private AmazonS3 amazonS3;

    public String saveFile(Long id, MultipartFile file) {
        String originalFileName =  id + file.getOriginalFilename();
        try {
            File convertMultipartInFile = convertMultipartInFile(file);
            amazonS3.putObject(nomeDobucket, originalFileName, convertMultipartInFile);
            String urlFile = URL + nomeDobucket + "/" + originalFileName;
            return urlFile;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private File convertMultipartInFile(MultipartFile file) throws IOException {
        File convertFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fileOutputStream = new FileOutputStream(convertFile);
        fileOutputStream.write(file.getBytes());
        fileOutputStream.close();

        return convertFile;
    }
}
