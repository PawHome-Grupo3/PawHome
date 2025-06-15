package com.grupo3.pawHome.services;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class BackgroundImageEvent extends PdfPageEventHelper {

    private Image background;

    public BackgroundImageEvent(String imagePath) {
        try {
            this.background = Image.getInstance(imagePath);
            this.background.scaleAbsolute(PageSize.A4);
            this.background.setAbsolutePosition(0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        try {
            PdfContentByte canvas = writer.getDirectContentUnder();
            canvas.addImage(background);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
