package com.example.dam.service.transform;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class UtilsTransform {
    private UtilsTransform() {
        // Empty private constructor
    }

    public static BufferedImage scaleImage(BufferedImage originalImage, int targetScale) {
        // Calculate the aspect ratio
        double aspectRatio = (double) originalImage.getWidth() / originalImage.getHeight();
        int width;
        int height;

        if (originalImage.getWidth() > originalImage.getHeight()) {
            width = targetScale;
            height = (int) (targetScale / aspectRatio);
        } else {
            height = targetScale;
            width = (int) (targetScale * aspectRatio);
        }

        // Ensure that the width and height do not exceed the target dimensions
        if (width > targetScale) {
            width = targetScale;
            height = (int) (width / aspectRatio);
        } else if (height > targetScale) {
            height = targetScale;
            width = (int) (height * aspectRatio);
        }

        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(originalImage, 0, 0, width, height, null);
        g2d.dispose();

        return resizedImage;
    }

    public static BufferedImage cropCenterImage(BufferedImage image, int width, int height) {
        int imgWidth = image.getWidth();
        int imgHeight = image.getHeight();

        // Calculate the top-left corner of the cropping area
        int x = (imgWidth - width) / 2;
        int y = (imgHeight - height) / 2;

        // Ensure cropping area is within the bounds of the original image
        if (x < 0 || y < 0 || x + width > imgWidth || y + height > imgHeight) {
            throw new IllegalArgumentException("Crop area is out of bounds");
        }

        // Crop the image
        return image.getSubimage(x, y, width, height);
    }

    public static BufferedImage cropHeightImage(BufferedImage image, int cropHeight) {
        int imgWidth = image.getWidth();
        int imgHeight = image.getHeight();

        // Calculate the top-left corner of the cropping area
        int x = 0;
        int y = (imgHeight - cropHeight) / 2;

        // Ensure cropping area is within the bounds of the original image
        if (cropHeight > imgHeight) {
            throw new IllegalArgumentException("Crop height is larger than image height");
        }

        // Crop the image
        return image.getSubimage(x, y, imgWidth, cropHeight);
    }

    public static void convertDocxToPdf(String docxPath, String pdfPath) throws IOException {
//        try (FileInputStream fis = new FileInputStream(docxPath);
//             XWPFDocument docx = new XWPFDocument(fis);
//             PdfWriter writer = new PdfWriter(pdfPath);
//             PdfDocument pdf = new PdfDocument(writer);
//             Document pdfDoc = new Document(pdf)) {
//
//            for (XWPFParagraph paragraph : docx.getParagraphs()) {
//                pdfDoc.add(new Paragraph(paragraph.getText()));
//            }
//        }
    }
}
