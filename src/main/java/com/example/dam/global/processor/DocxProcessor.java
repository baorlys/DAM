package com.example.dam.global.processor;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.FileInputStream;
import java.io.IOException;

public class DocxProcessor implements DocumentProcessor {
    @Override
    public void processDocument(String filePath, String outputPath) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath);
             XWPFDocument docx = new XWPFDocument(fis);
             PdfWriter writer = new PdfWriter(outputPath);
             PdfDocument pdf = new PdfDocument(writer);
             Document pdfDoc = new Document(pdf)) {

            for (XWPFParagraph paragraph : docx.getParagraphs()) {
                pdfDoc.add(new Paragraph(paragraph.getText()));
            }
        }
    }
}