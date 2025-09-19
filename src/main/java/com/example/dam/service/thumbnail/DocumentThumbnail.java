package com.example.dam.service.thumbnail;

import com.example.dam.global.processor.DocumentProcessor;
import com.example.dam.global.processor.DocumentProcessorFactory;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DocumentThumbnail implements IGenerateThumbnail {
    @Override
    public void generateThumbnail(String filePath, String outputPath, int scaleTarget) throws IOException {
        DocumentProcessor processor = DocumentProcessorFactory.getProcessor(filePath);
        processor.processDocument(filePath, outputPath);

        try (PDDocument document = PDDocument.load(new File(outputPath))) {
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            BufferedImage bim = pdfRenderer.renderImageWithDPI(0, 600); // Render the first page
            ImageIO.write(bim, "JPEG", new File(outputPath));

            Thumbnails.of(outputPath)
                    .size(scaleTarget, scaleTarget)
                    .outputQuality(1.0f)
                    .toFile(new File(outputPath));
        }
    }





}
