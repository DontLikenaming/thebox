//package com.team.thebox.utils;
//
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.model.CannedAccessControlList;
//import com.amazonaws.services.s3.model.PutObjectRequest;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.Optional;
//import java.util.UUID;
//
//@Slf4j
//@RequiredArgsConstructor
//@Component
//public class S3Uploader {
//
//    private final AmazonS3 amazonS3Client;
//
//    @Value("${cloud.aws.s3.bucketName}")
//    private String bucket;
//
//    public String upload(MultipartFile multipartFile, String dirName) {
//        File uploadFile = convert(multipartFile)
//                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));
//
//        return upload(uploadFile, dirName);
//    }
//
//    private String upload(File uploadFile, String dirName) {
//        UUID uuid = UUID.randomUUID();
//        String originName = uploadFile.getName();
//        String imageFileName = dirName + "/" + uuid + "_" + originName;
//        String uploadImageUrl = putS3(uploadFile, imageFileName);
//        removeNewFile(uploadFile);
//        return uploadImageUrl;
//    }
//
//    private String putS3(File uploadFile, String fileName) {
//        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
//        return amazonS3Client.getUrl(bucket, fileName).toString();
//    }
//
//    private void removeNewFile(File targetFile) {
//        if (targetFile.delete()) {
//            log.info("파일이 삭제되었습니다.");
//        } else {
//            log.info("파일이 삭제되지 못했습니다.");
//        }
//    }
//
//    private Optional<File> convert(MultipartFile file) {
//        File convertFile = new File(file.getOriginalFilename());
//        try {
//            if(convertFile.createNewFile()) {
//                try (FileOutputStream fos = new FileOutputStream(convertFile)) {
//                    fos.write(file.getBytes());
//                }
//                return Optional.of(convertFile);
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        return Optional.empty();
//    }
//}