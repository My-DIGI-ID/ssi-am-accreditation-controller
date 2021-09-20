package com.bka.ssi.controller.accreditation.company.application.utilities;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

public class QRCodeGeneratorTest {

    private static QRCodeGenerator qrCodeGenerator;
    private final String testQRCodePath = "src/test/resources/qrCode/testQRCode.png";
    private final String testQRCodeSvgPath = "src/test/resources/qrCode/testQRCode.svg";
    
    @BeforeAll
    static void setUp() {
        qrCodeGenerator = new QRCodeGenerator();
    }

    @Test
    void generateQRCode() {
        byte[] qrCode = null;

        try {
            qrCode = qrCodeGenerator.generateQRCode("http://0.0.0.0", 300, 300);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        assertNotNull(qrCode);

        File f = new File(testQRCodePath);

        try {
            byte[] testQR = Files.readAllBytes(f.toPath());

            assertArrayEquals(testQR, qrCode);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }
    
    @Test
    void generateQRCodeSvg() {
    	String qrCode = null;

        try {
            qrCode = qrCodeGenerator.generateQRCodeSvg("http://0.0.0.0", 300, 300);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        assertNotNull(qrCode);

        File f = new File(testQRCodeSvgPath);

        try {
            String testQR = Files.readString(f.toPath());

            assertEquals(testQR, qrCode);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }
}
