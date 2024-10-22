package com.pdf.controller;




import com.pdf.model.InvoiceRequest;
import com.pdf.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @PostMapping("/generate")
    public ResponseEntity<InputStreamResource> generateInvoicePdf(@RequestBody InvoiceRequest request) throws Exception {

        String pdfFilePath = pdfService.generateAndStorePdf(request);

     
        File pdfFile = new File(pdfFilePath);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(pdfFile));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + pdfFile.getName())
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }
}
