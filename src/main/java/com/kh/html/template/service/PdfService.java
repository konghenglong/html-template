package com.kh.html.template.service;

import com.itextpdf.html2pdf.HtmlConverter;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

@Service
public class PdfService {

    @Autowired
    private Configuration freemarkerConfig;

    public byte[] generatePdfFromTemplate(String templateName, Map<String, Object> model) throws IOException {
        try {
            Template template = freemarkerConfig.getTemplate(templateName);
            String htmlContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            HtmlConverter.convertToPdf(htmlContent, outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new IOException("Error generating PDF", e);
        }
    }
}