package com.example.dam.service.transform;

import com.example.dam.enums.TransformVariable;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;
@Slf4j
public class VideoTransform implements ITransformable {
    static final Map<String,String> scaleMap = Map.of(
            "144p", "256x144",
            "240p", "426x240",
            "480p", "854x480",
            "720p", "1280x720",
            "1080p", "1920x1080"
    );

    @Override
    public void transform(String filePath, String outputPath, Map<TransformVariable, String> options)
            throws IOException, InterruptedException {
        String resolution = options.getOrDefault(TransformVariable.RESOLUTION, "1080p");
        String ffmpegCommand = buildFFmpegCommand(filePath, outputPath, resolution);

        ProcessBuilder processBuilder = new ProcessBuilder(ffmpegCommand.split(" "));
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new IOException("FFmpeg command failed with exit code " + exitCode);
        }
    }

    private static String buildFFmpegCommand(String inputVideoPath, String outputVideoPath, String resolution) {
        String scale = scaleMap.get(resolution);
        return String.format("ffmpeg -i %s -vf scale=%s %s", inputVideoPath, scale, outputVideoPath);
    }
}
