package com.ELOUSTA.ELOUSTA.backend.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public final class ImageHandler {
    public static byte[] getProfilePhoto (String filename, String path) throws IOException {
        if (filename == null || path == null) {
            return new byte[0];
        }

        String filePath = path + filename;
        File file = new File(filePath);

        if (!file.exists() || !file.isFile()) {
            return new byte[0];
        }

        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            return new byte[0];
        }
    }

    public static String generateUniquePhotoName(String originalFileName) {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        String uuid = UUID.randomUUID().toString();
        return uuid + "_" + timestamp + "_" + originalFileName;
    }
}
