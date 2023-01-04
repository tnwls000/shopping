package web.clone.onemorebag.common.file;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;

@RestController
@RequiredArgsConstructor
public class FileApiController {

    private final FileStore fileStore;

    @ResponseBody
    @GetMapping("/images/{originalFileName}")
    public Resource downloadImage(@PathVariable String originalFileName) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(originalFileName));
    }
}
