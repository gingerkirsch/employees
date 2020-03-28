package com.employees.app.service;

import com.employees.app.exception.FileNotFoundException;
import com.employees.app.exception.FileStorageException;
import lombok.SneakyThrows;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {
    private final Path fileStorageLocation = Paths.get("uploads").toAbsolutePath().normalize();

    public FileStorageService() {
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Path not found", ex);
        }
    }

    @SneakyThrows
    public String storeFile(MultipartFile file){
        if (!file.getOriginalFilename().endsWith("png") || file.getOriginalFilename().endsWith("jpeg") || file.getOriginalFilename().endsWith("jpg")){
            throw new FileStorageException("Invalid file format!");
        }
        File f = new File("uploads" + file.getOriginalFilename());
        f.createNewFile();
        FileOutputStream fout = new FileOutputStream(f);
        fout.write(file.getBytes());
        fout.close();
        BufferedImage image = ImageIO.read(f);
        int height = image.getHeight();
        int width = image.getWidth();
        if (width > 300 || height > 300) {
            if (f.exists())
                f.delete();
            throw new FileStorageException("Invalid file dimensions");
        }

        if (f.exists())
            f.delete();

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (fileName.contains("Invalid file delimeter")){
                throw new FileStorageException("Invalid path name " + fileName);
            }
            String newFileName = System.currentTimeMillis() + "/" + fileName;
            Path target = this.fileStorageLocation.resolve(newFileName);
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
            return newFileName;
        } catch (IOException ex) {
            throw new FileStorageException(String.format("File storage exception", fileName), ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found: " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("File not found: " + fileName, ex);
        }
    }
}
