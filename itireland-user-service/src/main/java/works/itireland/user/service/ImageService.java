package works.itireland.user.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    public byte[] get(String username, String imageName);
    public String uploadProfileImage(String username, MultipartFile file);
}
