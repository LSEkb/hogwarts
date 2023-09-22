package ru.hogwarts.school.service;

import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;

import java.io.IOException;
import java.util.List;

public interface AvatarService {

    void uploadAvatar(long studentId, MultipartFile avatarFile) throws IOException;

    Avatar readFromDB(long id);

    List<Avatar> getAvatarsPage(int pageNumber, int pageSize);
}
