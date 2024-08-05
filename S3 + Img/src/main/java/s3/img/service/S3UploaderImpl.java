package s3.img.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3UploaderImpl implements S3Uploader {

    private final AmazonS3 amazonS3;

    @Value("${application.bucket.name}")
    private String bucket;

    /**
     * 파일을 S3에 업로드하고 파일의 URL을 반환합니다.
     *
     * @param file 업로드할 MultipartFile
     * @return 업로드된 파일의 S3 URL
     */
    @Override
    public String upload(MultipartFile file) {
        // 원본 파일 이름을 그대로 사용
        String fileName = file.getOriginalFilename().replaceAll("\\s", "_");

        // 파일을 S3에 업로드
        try {
            amazonS3.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), null));
        } catch (IOException e) {
            log.error("파일 업로드 중 오류 발생: {}", e.getMessage());
            throw new RuntimeException("파일 업로드 중 오류가 발생했습니다.");
        }

        // 업로드된 파일의 URL 반환
        return getFileUrl(fileName);
    }

    /**
     * S3에서 파일을 삭제합니다.
     *
     * @param fileName 삭제할 파일의 이름
     */
    @Override
    public void deleteFile(String fileName) {
        amazonS3.deleteObject(bucket, fileName);
        log.info("S3에서 파일 삭제: {}", fileName);
    }

    /**
     * S3에 저장된 파일의 URL을 반환합니다.
     *
     * @param fileName 파일의 이름
     * @return 파일의 S3 URL
     */
    @Override
    public String getFileUrl(String fileName) {
        return amazonS3.getUrl(bucket, fileName).toString();
    }
}
