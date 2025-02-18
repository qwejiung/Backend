package LittlePet.UMC;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class S3Service {
    private final AmazonS3 s3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, fileName, file.getInputStream(), metadata);

        s3.putObject(putObjectRequest);

        return getPublicUrl(fileName);

    }
    private String getPublicUrl(String fileName) {
        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucket, s3.getRegionName(), fileName);
    }

    public String getFileUrl(String fileName) {
        return s3.getUrl(bucket, fileName).toString();
    }

    public String getFileUrlIfExists(String fileName) {
        try {
            if (s3.doesObjectExist(bucket, fileName)) {  // S3에 파일 존재 여부 확인
                return s3.getUrl(bucket, fileName).toString();
            } else {
                throw new IllegalArgumentException(fileName + "이 존재하지 않음");

            }
        } catch (AmazonS3Exception e) {
            return "Error checking file: " + e.getMessage();
        }
    }
}
