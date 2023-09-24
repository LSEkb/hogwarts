package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.exception.AvatarException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class AvatarServiceImpl implements AvatarService {
    private final Logger logger = LoggerFactory.getLogger(AvatarServiceImpl.class);
    private final String avatarsDir;

    private final AvatarRepository avatarRepository;
    private final StudentService studentService;

    public AvatarServiceImpl(AvatarRepository avatarRepository, StudentService studentService,
                             @Value("${students.avatar.dir.path}") String avatarsDir) {
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
        this.avatarsDir = avatarsDir;
    }

    @Override
    public void uploadAvatar(long studentId, MultipartFile avatarFile) throws IOException {
        logger.info("The UploadAvatar method was called with data {} and {}", studentId, avatarFile);
        Student student = studentService.read(studentId);

        Path filePath = Path.of(avatarsDir, student.getId() + "."
                + getExtensions(avatarFile.getOriginalFilename()));
        Files.createDirectories((filePath.getParent()));
        Files.deleteIfExists(filePath);

        try (
                InputStream is = avatarFile.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
        }
        Avatar avatar = avatarRepository.findByStudent_id(studentId).orElse(new Avatar());

        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());

        avatarRepository.save(avatar);
        logger.info("The UploadAvatar method completed successfully, the student's avatar was loaded in DB");
    }

    @Override
    public Avatar readFromDB(long id) {
        logger.info("The ReadFromDB method was called with data {}", id);
        Avatar readedAvatar = avatarRepository.findByStudent_id(id).orElseThrow(() -> new AvatarException("Avatar not found"));
        logger.info("Returned from the ReadFromDB method avatar (id) {}", readedAvatar.getId());
        return readedAvatar;
    }

    @Override
    public List<Avatar> getAvatarsPage(int pageNumber, int pageSize) {
        logger.info("The GetAvatarsPage method was called with data: number of page {} and size of page {}", pageNumber, pageSize);
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        List<Avatar> avatars = avatarRepository.findAll(pageRequest).getContent();
        logger.info("Returned from the ReadFromDB method list of avatars (id) {}", avatars.stream().map(Avatar::getId).toList());
        return avatars;
    }

    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
