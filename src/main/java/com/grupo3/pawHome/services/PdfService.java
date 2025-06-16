package com.grupo3.pawHome.services;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;

public class PdfService {

    private Font subTitulo() {
        return new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD, BaseColor.DARK_GRAY);
    }

    private Font boldText() {
        return new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
    }

    private Font normalText() {
        return new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);
    }

    private PdfPCell celdaHeader(String texto) {
        Font font = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, BaseColor.WHITE);
        PdfPCell cell = new PdfPCell(new Phrase(texto, font));
        cell.setBackgroundColor(BaseColor.GRAY);
        cell.setPadding(6);
        return cell;
    }
}
