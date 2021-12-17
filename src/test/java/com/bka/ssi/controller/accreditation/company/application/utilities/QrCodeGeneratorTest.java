/*
 * Copyright 2021 Bundesrepublik Deutschland
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bka.ssi.controller.accreditation.company.application.utilities;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class QrCodeGeneratorTest {

    private final String testQrCodePngPath = "src/test/resources/qrCode/TestQrCode.png";
    private final String testQrCodeSvgPath = "src/test/resources/qrCode/TestQrCode.svg";

    @Test
    void generateQrCodePng() {
        byte[] qrCode = null;

        try {
            qrCode = QrCodeGenerator.generateQrCodePng("http://0.0.0.0", 300, 300);
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
            qrCode = QrCodeGenerator.generateQrCodeSvg("http://0.0.0.0", 300, 300);
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
