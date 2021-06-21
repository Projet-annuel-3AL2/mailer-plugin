package com.agirpourtous.mailer.core;

import com.agirpourtous.core.api.APIClient;
import com.agirpourtous.core.models.Project;
import com.agirpourtous.core.pdf.ProjectPdfGenerator;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class PdfParser {
    public String parseProjectPdf(Project project, APIClient client) throws DocumentException, IOException {
        File file = new ProjectPdfGenerator(client, project).generatePdf();
        FileInputStream fis = new FileInputStream(file);
        PdfReader reader = new PdfReader(fis);
        StringBuilder pdfContent = new StringBuilder();
        PdfReaderContentParser parser = new PdfReaderContentParser(reader);
        TextExtractionStrategy strategy;
        for (int i = 1; i <= reader.getNumberOfPages(); i++) {
            strategy = parser.processContent(i, new SimpleTextExtractionStrategy());
            pdfContent.append(strategy.getResultantText());
        }
        fis.close();
        file.delete();
        return pdfContent.toString();
    }
}
