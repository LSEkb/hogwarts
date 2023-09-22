package ru.hogwarts.school.service.impl;

import nonapi.io.github.classgraph.utils.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.exception.AvatarException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.service.StudentService;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AvatarServiceImplTest {

    StudentService studentService = mock(StudentService.class);
    AvatarRepository avatarRepository = mock(AvatarRepository.class);
    String avatarsDir = "./src/test/resources";

    AvatarServiceImpl avatarService = new AvatarServiceImpl(avatarRepository, studentService, avatarsDir);

    Student student = new Student(1L, "Harry", 13);

    @Test
    void uploadAvatar__avatarSavedToDbAndDirectory() throws IOException {
        MultipartFile file = new MockMultipartFile("1.jpg", "1.jpg", "jpg", new byte[]{});

        when(studentService.read(student.getId())).thenReturn(student);
        when(avatarRepository.findByStudent_id(student.getId())).thenReturn(Optional.empty());

        avatarService.uploadAvatar(student.getId(), file);

        verify(avatarRepository, times(1)).save(any());
        assertTrue(FileUtils.canRead(new File(avatarsDir + "/" + student.getId() + "." + file.getContentType())));
    }

    @Test
    void readFromDB_avatarIsInDB_() {
        Avatar avatar = new Avatar(1L, "./src/test/resources", 300L, "jpg", new byte[8], student);
        when(avatarRepository.findByStudent_id(student.getId())).thenReturn(Optional.of(avatar));
        Avatar result = avatarService.readFromDB(student.getId());
        assertEquals(avatar, result);
    }

    @Test
    void readFromDB_avatarIsNotInDB_() {
        when(avatarRepository.findByStudent_id(student.getId())).thenReturn(Optional.empty());

        AvatarException result = assertThrows(AvatarException.class, () -> avatarService.readFromDB(student.getId()));
        assertThrows(AvatarException.class, () -> avatarService.readFromDB(student.getId()));
        assertEquals("Avatar not found", result.getMessage());
    }

    @Test
    void getAvatarsPage__returnListOfAvatars() {
        Avatar avatar = new Avatar();
        when(avatarRepository.findAll((PageRequest) any())).thenReturn(new PageImpl<Avatar>(List.of(avatar)));
        List<Avatar> result = avatarService.getAvatarsPage(1, 1);
        assertEquals(List.of(avatar), result);
    }
}