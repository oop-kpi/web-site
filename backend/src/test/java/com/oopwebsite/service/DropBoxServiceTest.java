package com.oopwebsite.service;

import com.oopwebsite.OopwebsiteApplication;
import com.oopwebsite.dto.FileDto;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import static org.junit.Assert.*;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OopwebsiteApplication.class)
public class DropBoxServiceTest {
    @Autowired
    private FileStorageService fileStorage;
    @Test
    public void uploadFile() throws IOException {
        File test_file  = new File("kek.txt");
        test_file.createNewFile();
        FileUtils.writeStringToFile(test_file,"Test data");
        fileStorage.uploadFile("/keks/kek.txt",FileUtils.openInputStream(test_file));
        assertEquals(fileStorage.getFileDetails("/kek.txt").getName(),test_file.getName());
    }@Test
    public void downloadFile() throws IOException {

        FileDto link = fileStorage.getDownloadLink("/keks/kek.txt");
        System.out.println(link.getLink());
        assertNotEquals(link,null);
    }
}
