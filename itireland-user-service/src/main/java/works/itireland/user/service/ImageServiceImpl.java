package works.itireland.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import works.itireland.exception.ApiRequestException;
import works.itireland.user.domain.Image;
import works.itireland.user.domain.User;
import works.itireland.user.repository.ImageRepository;
import works.itireland.user.repository.UserRepository;
import works.itireland.user.s3.S3Buckets;
import works.itireland.user.s3.S3Service;

import java.io.IOException;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final S3Service s3Service;
    private final S3Buckets s3Buckets;

    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    @Override
    public byte[] get(String username, String imageName) {
        return s3Service.getObject(
                s3Buckets.getImage(),
                "images/%s/%s".formatted(username, imageName)
        );
    }

    public String uploadProfileImage(String username, MultipartFile file) {
        User user = userRepository.findByUsername(username);
        if(user == null)
            throw new ApiRequestException("Please sign in first!");
        String imageName = user.getUsername()+"_profle_"+ UUID.randomUUID();

        // Upload Image
        try {
            s3Service.putObject(
                    s3Buckets.getImage(),
                    "images/%s/%s".formatted(username, imageName),
                    file.getBytes()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Save to Database
        Image image = new Image(imageName, user, 1);
        imageRepository.save(image);

        // Update User
        user.setProfileImageName(imageName);
        user.setHeadShotUrl("/users/images/"+user.getUsername()+"/"+imageName);
        userRepository.save(user);

        return imageName;
    }

}
