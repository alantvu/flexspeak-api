package dev.mjamieson.flexspeak.feature.custom_word;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mjamieson.flexspeak.annotation.CurrentUsername;
import dev.mjamieson.flexspeak.exception.GeneralMessageException;
import dev.mjamieson.flexspeak.feature.aws_s3_bucket.AmazonS3StorageService;
import dev.mjamieson.flexspeak.feature.s3.S3Buckets;
import dev.mjamieson.flexspeak.feature.s3.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomWordServiceImpl implements CustomWordService {
    @Qualifier("jpa")
    private final CustomWordDAO customWordDAO;


    private final S3Service s3Service;
    private final S3Buckets s3Buckets;

    static final String IMAGE_FILE = "file";
    static final String METADATA_PARAMETER_NAME = "metadata";

    @Override
    @SneakyThrows
    public Void post(@CurrentUsername String username, MultipartHttpServletRequest request) {

        String jsonMetadata = request.getParameter(METADATA_PARAMETER_NAME);
        final ObjectMapper objectMapper = new ObjectMapper();
        CustomWordDTO customWordDTO = objectMapper.readValue(jsonMetadata, CustomWordDTO.class);

        handleSingleCustomWord(username, request, customWordDTO);

        return null;
    }

    @Override
    @SneakyThrows
    public Void posts(String username, MultipartHttpServletRequest request) {
        String jsonMetadata = request.getParameter(METADATA_PARAMETER_NAME);
        final ObjectMapper objectMapper = new ObjectMapper();
        List<CustomWordDTO> customWordDTOs = objectMapper.readValue(jsonMetadata, objectMapper.getTypeFactory().constructCollectionType(List.class, CustomWordDTO.class));

        // Loop through each CustomWordDTO and handle it
        for (CustomWordDTO customWordDTO : customWordDTOs) {
            handleSingleCustomWord(username, request, customWordDTO);
        }

        return null;
    }
    @Override
    public List<CustomWordDTO> get(@CurrentUsername String username) {
        List<CustomWordDTO> customWordDTOS = customWordDAO.get(username)
                .stream()
                .map(customWord -> Optional.ofNullable(customWord.getImagePath())
                        .map(imagePath -> CustomWordDTO.fromWithImagePath(customWord, s3Service.generatePresignedUrl(s3Buckets.getFlexspeak(), imagePath)))
                        .orElseGet(() -> CustomWordDTO.from(customWord)))
                .collect(Collectors.toList());
        return customWordDTOS;
    }


    private void handleSingleCustomWord(String username, MultipartHttpServletRequest request, CustomWordDTO customWordDTO) {
        MultipartFile imageMultipartFile = request.getFile(IMAGE_FILE);

        if (Objects.nonNull(imageMultipartFile)) {
            saveCustomWordAndImage(imageMultipartFile, username, customWordDTO);
        } else {
            customWordDAO.save(username, customWordDTO);
        }
    };
    private void saveCustomWordAndImage(MultipartFile multipartFile,@CurrentUsername String username, CustomWordDTO customWordDTO){
        String fileName = createFileName(multipartFile);
        storeInS3Bucket(multipartFile,fileName);
        customWordDAO.save(username,CustomWordDTO.fromWithImagePath(customWordDTO,fileName));
    };
    private String createFileName(MultipartFile multipartFile) {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "") + multipartFile.getOriginalFilename().replace("-","");
    }
    @SneakyThrows
    private void storeInS3Bucket(MultipartFile multipartFile, String finalFileName) {
        if (multipartFile.getBytes().length < 0) throw new GeneralMessageException("Something is wrong with a file you uploaded");
        s3Service.putObject(
                s3Buckets.getFlexspeak(),
                finalFileName,
                multipartFile.getBytes()
                );
    }
    private byte[] getFromS3Bucket(String imagePath) {
        return s3Service.getObject(s3Buckets.getFlexspeak(), imagePath);
    }

}
