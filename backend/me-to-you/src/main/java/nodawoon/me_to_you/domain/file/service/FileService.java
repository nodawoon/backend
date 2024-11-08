package nodawoon.me_to_you.domain.file.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nodawoon.me_to_you.domain.file.exception.*;
import nodawoon.me_to_you.domain.file.presentation.dto.response.UploadFileResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileService implements FileServiceUtils {

    @Value("${aws.s3.bucket}")
    private String bucket;

    @Value("${aws.s3.base-url}")
    private String baseUrl;

    private static final int IMAGE_SIZE = 5;
    private final AmazonS3 amazonS3;

    @Override
    public UploadFileResponse uploadImage(MultipartFile file) {
        validateFile(file, IMAGE_SIZE, new String[]{"jpg", "jpeg", "png"});

        try {
            BufferedImage image = ImageIO.read(file.getInputStream());
            BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g = newImage.createGraphics();
            g.drawImage(image, 0, 0, Color.WHITE, null);
            g.dispose();
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(newImage, "jpg", os);
            byte[] jpgBytes = os.toByteArray();
            return new UploadFileResponse(uploadToS3(file.getOriginalFilename(), jpgBytes));
        } catch (IOException e) {
            throw ImageProcessingException.EXCEPTION;
        }
    }

    private void validateFile(MultipartFile file, int sizeThreshold, String[] exts) {
        if (file.isEmpty()) {
            throw FileEmptyException.EXCEPTION;
        }

        if (file.getSize() <= sizeThreshold) {
            throw FileSizeException.EXCEPTION;
        }

        String originalFilename = file.getOriginalFilename();
        String ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        if (!isExtensionAllowed(ext, exts)) {
            throw BadFileExtensionException.EXCEPTION;
        }
    }

    @Override
    public String uploadToS3(String originalFilename, byte[] fileBytes) {
        String fileName = generateFileName(originalFilename);
        try {
            ObjectMetadata objMeta = new ObjectMetadata();
            objMeta.setContentType("image/jpeg");
            objMeta.setContentLength(fileBytes.length);

            // S3에 업로드
            amazonS3.putObject(
                    new PutObjectRequest(bucket, fileName, new ByteArrayInputStream(fileBytes), objMeta)
                            .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (Exception e) {
            throw FileUploadFailException.EXCEPTION;
        }

        return baseUrl + "/" + fileName;
    }

    private boolean isExtensionAllowed(String extension, String[] allowedExtensions) {
        for (String allowedExtension : allowedExtensions) {
            if (extension.equalsIgnoreCase(allowedExtension)) {
                return true;
            }
        }
        return false;
    }

    private String generateFileName(String originalFilename) {
        String ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        String randomName = UUID.randomUUID().toString();
        return randomName + "." + ext;
    }
}
