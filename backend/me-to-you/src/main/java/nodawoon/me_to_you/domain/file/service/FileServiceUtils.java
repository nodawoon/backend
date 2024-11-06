package nodawoon.me_to_you.domain.file.service;


import nodawoon.me_to_you.domain.file.presentation.dto.response.UploadFileResponse;
import org.springframework.web.multipart.MultipartFile;

public interface FileServiceUtils {
    UploadFileResponse uploadImage(MultipartFile file);

    String uploadToS3(String originalFilename, byte[] fileBytes);
}
