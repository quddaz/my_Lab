package s3.img.service;

import org.springframework.web.multipart.MultipartFile;

public interface S3Uploader {
    String upload(MultipartFile file);
    //파일 삭제
    void deleteFile(String fileName);

    //파일 URL 조회
    String getFileUrl(String fileName);


}
