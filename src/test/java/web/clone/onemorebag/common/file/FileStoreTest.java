package web.clone.onemorebag.common.file;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import web.clone.onemorebag.common.exception.form.item.EmptyFileException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class FileStoreTest {

    final String FILE_DIR = "C:\\Users\\201910086\\Desktop\\spring\\onemorebag\\src\\test\\resources\\file\\";
    FileStore fileStore = new FileStore(FILE_DIR);
    List<MultipartFile> multipartFiles;
    List<FileDto> fileDtos = new ArrayList<>();


    @DisplayName("전체 경로 조회")
    @Test
    void getFullPath() {
        //given
        String originalFileName = "originalFileName";

        //when
        String fullPath = fileStore.getFullPath(originalFileName);

        //then
        assertThat(fullPath).isEqualTo(FILE_DIR + originalFileName);
    }

    @Nested
    @DisplayName("파일 저장 - 여러개")
    class storeFiles {

        @Test
        @DisplayName("성공")
        void success() throws IOException {
            //given
            multipartFiles = List.of(
                    createMockMultipartFile("file", "test_file1.png", MediaType.IMAGE_PNG_VALUE),
                    createMockMultipartFile("file", "test_file2.jpeg", MediaType.IMAGE_JPEG_VALUE),
                    createMockMultipartFile("file", "test_file3.jpeg", MediaType.IMAGE_JPEG_VALUE));

            //when
            //then
            assertThatNoException().isThrownBy(() -> fileStore.storeFiles(multipartFiles));
        }

        @Test
        @DisplayName("실패 - 파일 없음")
        void failByEmptyFile() throws IOException {
            //given
            multipartFiles = List.of(
                    createMockMultipartFile("file", "test_file1.png", MediaType.IMAGE_PNG_VALUE),
                    createEmptyMockMultipartFile("file", "test_file2.jpeg", MediaType.IMAGE_JPEG_VALUE),
                    createMockMultipartFile("file", "test_file3.jpeg", MediaType.IMAGE_JPEG_VALUE));

            //when
            //then
            assertThatThrownBy(() -> fileStore.storeFiles(multipartFiles))
                    .isExactlyInstanceOf(EmptyFileException.class);
        }
    }

    @Nested
    @DisplayName("파일 저장 - 1개")
    class storeFile {

        @Test
        @DisplayName("성공")
        void success() throws IOException {
            //given
            MultipartFile multipartFile = createMockMultipartFile("file", "test_file1.png", MediaType.IMAGE_PNG_VALUE);
            //when
            //then
            assertThatNoException().isThrownBy(() -> fileStore.storeFile(multipartFile));
        }

        @Test
        @DisplayName("실패 - 파일 없음")
        void failByEmptyFile() throws IOException {
            //given
            MultipartFile multipartFile = createEmptyMockMultipartFile("file", "test_file1.png", MediaType.IMAGE_PNG_VALUE);
            //when
            //then
            assertThatThrownBy(() -> fileStore.storeFile(multipartFile))
                    .isExactlyInstanceOf(EmptyFileException.class);
        }
    }

    private MockMultipartFile createMockMultipartFile(String name, String originalFileName, String contentType) throws IOException {
        return new MockMultipartFile(
                name,
                originalFileName,
                contentType,
                new FileInputStream(new File(FILE_DIR + originalFileName))
        );
    }

    private MockMultipartFile createEmptyMockMultipartFile(String name, String originalFileName, String contentType) throws IOException {
        return new MockMultipartFile(name, originalFileName, contentType, InputStream.nullInputStream());
    }
}