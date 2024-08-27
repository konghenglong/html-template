package com.kh.html.template.controller;

import com.kh.html.template.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @GetMapping("/pdf/download")
    public ResponseEntity<InputStreamResource> downloadPdf() {
        try {
            Map<String, Object> model = new HashMap<>();
            model.put("userName", "John");

            byte[] pdfContent = pdfService.generatePdfFromTemplate("template.html", model);

            ByteArrayInputStream inputStream = new ByteArrayInputStream(pdfContent);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=document.pdf");
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);

            return new ResponseEntity<>(new InputStreamResource(inputStream), headers, HttpStatus.OK);

        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}