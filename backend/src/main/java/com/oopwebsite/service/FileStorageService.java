package com.oopwebsite.service;

import com.dropbox.core.v2.files.FileMetadata;
import com.oopwebsite.dto.FileDto;

import java.io.InputStream;

public interface FileStorageService {


        FileMetadata uploadFile(String filePath, InputStream fileStream);

        FileMetadata getFileDetails(String filePath);

        void deleteFile(String filePath);

        FileDto getDownloadLink(String filePath);
}
