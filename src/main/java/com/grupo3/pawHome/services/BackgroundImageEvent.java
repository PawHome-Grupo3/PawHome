package com.grupo3.pawHome.services;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.InputStream;

public class BackgroundImageEvent extends PdfPageEventHelper {

    private Image backgroundImage;

    public BackgroundImageEvent(InputStream imageStream) {
        try {
            byte[] imageBytes = imageStream.readAllBytes(); // Java 9+
            this.backgroundImage = Image.getInstance(imageBytes);
            this.backgroundImage.setAbsolutePosition(0, 0);
            this.backgroundImage.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        try {
            PdfContentByte canvas = writer.getDirectContentUnder();
            canvas.addImage(backgroundImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
