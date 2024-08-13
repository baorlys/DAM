package com.example.dam.service.thumbnail;

import java.io.IOException;

public class ImageThumbnail implements IGenerateThumbnail {
    @Override
    public void generateThumbnail(String filePath, String outputPath, int scaleTarget) throws IOException, InterruptedException {
        String command = String.format(
                "ffmpeg -i %s -vf \"scale=%d:-1:force_original_aspect_ratio=1\" %s",
                filePath,
                scaleTarget,
                outputPath
        );

        Process process = Runtime.getRuntime().exec(command);
        process.waitFor();
    }
}
