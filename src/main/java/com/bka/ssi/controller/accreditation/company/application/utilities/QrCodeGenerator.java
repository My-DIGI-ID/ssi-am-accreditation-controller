package com.bka.ssi.controller.accreditation.company.application.utilities;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.jfree.graphics2d.svg.ViewBox;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;

public class QrCodeGenerator {

    public static byte[] generateQrCodePng(String text, int width, int height)
        throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();

        return pngData;
    }

    public static String generateQrCodeSvg(String text, int width, int height)
        throws WriterException {
        Hashtable<EncodeHintType, Object> hintMap = new Hashtable<>();

        hintMap.put(EncodeHintType.ERROR_CORRECTION,
            ErrorCorrectionLevel.L);

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix =
            qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hintMap);

        int crunchifyWidth = bitMatrix.getWidth();
        int crunchifyHeight = bitMatrix.getHeight();

        BufferedImage image =
            new BufferedImage(crunchifyWidth, crunchifyHeight, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();

        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, crunchifyWidth, crunchifyHeight);
        graphics.setColor(Color.BLACK);

        for (int i = 0; i < crunchifyWidth; i++) {
            for (int j = 0; j < crunchifyHeight; j++) {
                if (bitMatrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }

        SVGGraphics2D svgGraphics = new SVGGraphics2D(crunchifyWidth, crunchifyHeight);
        svgGraphics.drawImage(image, 0, 0, width, height, null);

        ViewBox viewBox = new ViewBox(0, 0, crunchifyWidth, crunchifyHeight);

        return svgGraphics.getSVGElement(null, true, viewBox, null, null);
    }
}
