package com.oopwebsite.service;

import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.GetTemporaryLinkResult;
import com.dropbox.core.v2.files.Metadata;
import com.oopwebsite.controller.exceptions.FileStorageException;
import com.oopwebsite.dto.FileDto;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class DropboxFileStorageImpl implements FileStorageService{
    private final DbxClientV2 client;

    public DropboxFileStorageImpl(DbxClientV2 client) {
        this.client = client;
    }


    @Override
    public FileMetadata uploadFile(String filePath, InputStream file) {
        return handleDropboxAction(() -> client.files().uploadBuilder(filePath).uploadAndFinish(file),
                String.format("Error uploading file: %s", filePath));

    }


    @Override
    public FileMetadata getFileDetails(String filePath) {
        return getMetadata(filePath, FileMetadata.class, String.format("Error getting file details: %s", filePath));
    }



    @Override
    public void deleteFile(String filePath) {
        handleDropboxAction(() -> client.files().deleteV2(filePath), String.format("Error deleting file: %s", filePath));
    }

    @Override
    public FileDto getDownloadLink(String filePath) {

        GetTemporaryLinkResult fileLink = handleDropboxAction(() -> client.files().getTemporaryLink(filePath), String.format("Error while getting download link"));
        FileDto fileDto = new FileDto();
        fileDto.setFileLength(fileLink.getMetadata().getSize());
        fileDto.setFileName(fileLink.getMetadata().getName().substring(fileLink.getMetadata().getName().indexOf('.')+1));
        fileDto.setLink(fileLink.getLink());
        return fileDto;

    }


    private <T> T handleDropboxAction(DropboxActionResolver<T> action, String exceptionMessage) {
        try {
            return action.perform();
        } catch (Exception e) {
            String messageWithCause = String.format("%s with cause: %s", exceptionMessage, e.getMessage());
            throw new FileStorageException(messageWithCause);
        }
    }


    @SuppressWarnings("unchecked")
    private <T> T getMetadata(String path, Class<T> type, String message) {
        Metadata metadata = handleDropboxAction(() -> client.files().getMetadata(path),
                String.format("Error accessing details of: %s", path));

        checkIfMetadataIsInstanceOfGivenType(metadata, type, message);
        return (T) metadata;
    }

    private <T> void checkIfMetadataIsInstanceOfGivenType(Metadata metadata, Class<T> validType, String exceptionMessage) {
        boolean isValidType = validType.isInstance(metadata);
        if (!isValidType) {
            throw new FileStorageException(exceptionMessage);
        }
    }
}
