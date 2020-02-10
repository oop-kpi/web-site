package com.oopwebsite.utility;

import com.oopwebsite.controller.exceptions.FileStorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;


public class FileUtility {
private static Logger LOGGER = LoggerFactory.getLogger(FileUtility.class);
    public static String saveFileToDir(MultipartFile file,String fileUploadingPath) {
        if (file == null) throw new FileStorageException("File is null!");
        LOGGER.info("Starting saving file  : "+file.getOriginalFilename());

        try {
            String newFileName = UUID.randomUUID().toString() + "." + file.getOriginalFilename();
            Path targetPath = Paths.get(fileUploadingPath).toAbsolutePath().normalize().resolve(newFileName);

            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            return newFileName;

        } catch (IOException exc){

            LOGGER.error("Error during saving file :"+file.getOriginalFilename());
            throw new FileStorageException("Cant save file! File name "+file.getOriginalFilename());

        }
    }
}
