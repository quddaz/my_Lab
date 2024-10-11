package s3.img.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;@Slf4j
@Service
@RequiredArgsConstructor
public class S3UploaderImpl implements S3Uploader {

    private final AmazonS3 amazonS3;

    @Value("${application.bucket.name}")
    private String bucket;

    @Override
    public String upload(MultipartFile file) {
        // 확장자를 제거한 파일 이름 생성
        String originalFileName = file.getOriginalFilename().replaceAll("\\s", "_");
        String fileNameWithoutExtension = originalFileName.substring(0, originalFileName.lastIndexOf(".")); // 확장자 제거

        // 파일을 S3에 업로드
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType()); // MIME 타입 설정

            amazonS3.putObject(new PutObjectRequest(bucket, fileNameWithoutExtension, file.getInputStream(), metadata));
        } catch (IOException e) {
            log.error("파일 업로드 중 오류 발생: {}", e.getMessage());
            throw new RuntimeException("파일 업로드 중 오류가 발생했습니다.");
        }

        // 업로드된 파일의 URL 반환
        return getFileUrl(fileNameWithoutExtension);
    }

    @Override
    public void deleteFile(String fileName) {
        amazonS3.deleteObject(bucket, fileName);
        log.info("S3에서 파일 삭제: {}", fileName);
    }

    @Override
    public String getFileUrl(String fileName) {
        return amazonS3.getUrl(bucket, fileName).toString();
    }
    }