package s3.img.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import s3.img.service.S3Uploader;
import s3.img.service.S3UploaderImpl;

import java.io.IOException;

@Controller
@RequestMapping("/files")
public class FileUploadController {

    private final S3Uploader s3Uploader;

    public FileUploadController(S3Uploader s3Uploader) {
        this.s3Uploader = s3Uploader;
    }

    /**
     * 파일 업로드 폼을 보여주는 메서드
     */
    @GetMapping("/upload")
    public String showUploadForm() {
        return "uploadForm";
    }

    /**
     * 파일을 업로드하고 S3 URL을 반환하는 메서드
     */
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) {
        try {
            // 파일 업로드 및 URL 반환
            String fileUrl = s3Uploader.upload(file);
            model.addAttribute("fileUrl", fileUrl);
            model.addAttribute("message", "파일 업로드 성공!");
        } catch (Exception e) {
            model.addAttribute("message", "파일 업로드 실패: " + e.getMessage());
        }
        return "uploadForm";
    }
}
