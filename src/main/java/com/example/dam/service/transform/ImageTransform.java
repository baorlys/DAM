package com.example.dam.service.transform;

import com.example.dam.enums.TransformVariable;
import com.example.dam.global.service.CommonService;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class ImageTransform implements ITransformable {

    @Override
    public void transform(String filePath, String outputPath, Map<TransformVariable, String> options) throws IOException {
        BufferedImage inputImage = ImageIO.read(new File(filePath));
        CommonService.throwIsNull(inputImage, "Image not found");

        int width = Integer.parseInt(options.getOrDefault(TransformVariable.WIDTH, String.valueOf(inputImage.getWidth())));
        int height = Integer.parseInt(options.getOrDefault(TransformVariable.HEIGHT, String.valueOf(inputImage.getHeight())));

        // Resize the image
        Image scaledImage = inputImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(scaledImage, 0, 0, null);
        g2d.dispose();

        // Save the image
        File output = new File(outputPath);

        ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
        ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        float quality = Float.parseFloat(options.getOrDefault(TransformVariable.QUALITY, "50"))/100;
        param.setCompressionQuality(quality);

        writer.setOutput(ImageIO.createImageOutputStream(output));
        writer.write(null, new IIOImage(outputImage, null, null), param);
        writer.dispose();

    }



}
