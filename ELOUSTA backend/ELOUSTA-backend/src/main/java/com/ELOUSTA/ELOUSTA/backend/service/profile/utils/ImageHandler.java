package com.ELOUSTA.ELOUSTA.backend.service.profile.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public final class ImageHandler {
    public static byte[] getProfilePhoto (String filename, String path) throws IOException {
        String filePath = path + filename;
        return Files.readAllBytes(new File(filePath).toPath());
    }
}
