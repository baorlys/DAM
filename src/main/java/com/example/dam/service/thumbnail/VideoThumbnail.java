package com.example.dam.service.thumbnail;

import java.io.IOException;

public class VideoThumbnail implements IGenerateThumbnail {
    @Override
    public void generateThumbnail(String filePath, String outputPath, int scaleTarget) throws IOException, InterruptedException {
        String scale = scaleTarget + ":" + scaleTarget;
        String command = String.format(
                "ffmpeg -i %s -vf \"scale=%s:force_original_aspect_ratio=1,pad=%s:(ow-iw)/2:(oh-ih)/2\" -frames:v 1 %s",
                filePath,
                scale,
                scale,
                outputPath
        );
        Process process = Runtime.getRuntime().exec(command);
        process.waitFor();
    }
}
