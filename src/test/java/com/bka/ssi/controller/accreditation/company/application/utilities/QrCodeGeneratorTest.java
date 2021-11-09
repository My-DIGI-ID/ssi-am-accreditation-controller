package com.bka.ssi.controller.accreditation.company.application.utilities;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class QrCodeGeneratorTest {

    private static QrCodeGenerator qrCodeGenerator;
    private final String testQrCodePngPath = "src/test/resources/qrCode/TestQrCode.png";
    private final String testQrCodeSvgPath = "src/test/resources/qrCode/TestQrCode.svg";

    @BeforeAll
    static void setUp() {
        qrCodeGenerator = new QrCodeGenerator();
    }

    @Test
    void generateQrCodePng() {
        byte[] qrCode = null;

        try {
            qrCode = qrCodeGenerator.generateQrCodePng("http://0.0.0.0", 300, 300);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        assertNotNull(qrCode);

        File f = new File(testQrCodePngPath);

        try {
            byte[] testQr = Files.readAllBytes(f.toPath());

            assertArrayEquals(testQr, qrCode);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void generateQrCodeSvg() {
        String qrCode = null;

        try {
            qrCode = qrCodeGenerator.generateQrCodeSvg("http://0.0.0.0", 300, 300);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        assertNotNull(qrCode);

        File f = new File(testQrCodeSvgPath);

        try {
            String testQr = Files.readString(f.toPath());

            assertEquals(testQr, qrCode);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }
}
