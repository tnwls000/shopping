package web.clone.onemorebag.common.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static web.clone.onemorebag.common.exception.form.FormExceptionType.EMPTY_FILE;

@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;

    public FileStore(@Value("${file.dir}") String fileDir) {
        this.fileDir = fileDir;
    }

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    public List<FileDto> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<FileDto> FileDtos = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            FileDtos.add(storeFile(multipartFile));
        }
        return FileDtos;
    }

    public FileDto storeFile(MultipartFile multipartFile) throws IOException {
        validateEmptyFile(multipartFile);

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        String extension = extractExtension(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFileName)));
        return new FileDto(originalFilename, storeFileName, extension);
    }

    private static void validateEmptyFile(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw EMPTY_FILE.getException();
        }
    }

    private String createStoreFileName(String originalFilename) {
        String extension = extractExtension(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + extension;
    }

    private String extractExtension(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
