package com.ELOUSTA.ELOUSTA.backend.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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
}
