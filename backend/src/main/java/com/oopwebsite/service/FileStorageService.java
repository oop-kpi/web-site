package com.oopwebsite.service;

import com.dropbox.core.v2.files.FileMetadata;

import java.io.InputStream;

public interface FileStorageService {

        InputStream downloadFile(String filePath);

        FileMetadata uploadFile(String filePath, InputStream fileStream);

        FileMetadata getFileDetails(String filePath);

        void deleteFile(String filePath);


}
