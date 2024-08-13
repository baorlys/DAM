package com.example.dam.service.implement.transform;

import com.example.dam.enums.TransformVariable;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
    public String transform(String filePath, Map<TransformVariable, String> options) throws IOException, InterruptedException {
        String outputPath = filePath.replace(".", "_transformed.");
        String resolution = options.getOrDefault(TransformVariable.RESOLUTION, "1080p");
        String ffmpegCommand = buildFFmpegCommand(filePath, outputPath, resolution);

        ProcessBuilder processBuilder = new ProcessBuilder(ffmpegCommand.split(" "));
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();

        // Capture and log FFmpeg output
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                log.info(line);
            }
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new IOException("FFmpeg command failed with exit code " + exitCode);
        }

        return outputPath;
    }

    private static String buildFFmpegCommand(String inputVideoPath, String outputVideoPath, String resolution) {
        String scale = scaleMap.get(resolution);
        return String.format("ffmpeg -i %s -vf scale=%s %s", inputVideoPath, scale, outputVideoPath);
    }
}
