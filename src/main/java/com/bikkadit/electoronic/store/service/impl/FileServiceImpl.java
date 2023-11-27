package com.bikkadit.electoronic.store.service.impl;

import com.bikkadit.electoronic.store.exception.BadApiRequest;
import com.bikkadit.electoronic.store.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String uploadFile(MultipartFile file, String path) throws IOException {

        logger.info("Entering The Dao Call For Upload Image :");
        String originalFilename = file.getOriginalFilename();
        logger.info("orginalFileName : {}", originalFilename);

        String fileName = UUID.randomUUID().toString();

        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

        String fileNameWithExtension = fileName + extension;

        String fullpathWithName = path + fileNameWithExtension;

        if (extension.equalsIgnoreCase(".Png") || extension.equalsIgnoreCase(".Jpg") || extension.equalsIgnoreCase(".Jpeg")) {

            File folder = new File(path);
            if(!folder.exists()){
                folder.mkdirs();
            }
            Files.copy(file.getInputStream(), Paths.get(fullpathWithName));
            logger.info("Completed The Dao Call For Upload Image");
            return fileNameWithExtension;
        } else {

            throw new BadApiRequest("Do Not Insert File With This Extension "+extension+" This Is Not Allow ");
        }


    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {

        logger.info("Entering The Dao Call For Get Image :{}",name);

        String fullPath = path+File.separator + name;

        InputStream inputStream=new FileInputStream(fullPath);
        logger.info("Completed The Dao Call For Get Image :{}",name);

        return inputStream;
    }


}
