package nodawoon.me_to_you.domain.file.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import nodawoon.me_to_you.domain.file.presentation.dto.response.UploadFileResponse;
import nodawoon.me_to_you.domain.file.service.FileService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "파일", description = "파일 관련 API")
@RequiredArgsConstructor
@RequestMapping("/api/file")
@RestController
public class FileController {

    private final FileService fileService;

    @SecurityRequirements
    @Operation(summary = "이미지 업로드")
    @PostMapping("/image")
    public UploadFileResponse uploadImage(@RequestPart(value = "file") MultipartFile file) {
        return fileService.uploadImage(file);
    }
}