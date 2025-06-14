package com.grupo3.pawHome.services;

import com.grupo3.pawHome.dtos.FacturaDTO;
import com.grupo3.pawHome.dtos.LineaFacturaDTO;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PdfFacturaService {

    public byte[] generarFacturaPDF(FacturaDTO factura) throws Exception {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, out);
        document.open();

        Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Font fontNormal = FontFactory.getFont(FontFactory.HELVETICA, 12);

        document.add(new Paragraph("Factura Nº: " + factura.getIdFactura(), fontTitulo));
        document.add(new Paragraph("Fecha: " + factura.getFecha(), fontNormal));
        document.add(new Paragraph("Cliente: " + factura.getNombreUsuario() + " " + factura.getApellidosUsuario(), fontNormal));
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{2, 3, 1, 2, 2});

        table.addCell("Producto");
        table.addCell("Descripción");
        table.addCell("Cantidad");
        table.addCell("Precio Unitario");
        table.addCell("Total");

        for (LineaFacturaDTO linea : factura.getLineas()) {
            table.addCell(linea.getNombre());
            table.addCell(linea.getDescripcion());
            table.addCell(String.valueOf(linea.getCantidad()));
            table.addCell(String.format("%.2f €", linea.getPrecioUnitario()));
            table.addCell(String.format("%.2f €", linea.getCantidad() * linea.getPrecioUnitario()));
        }

        document.add(table);

        document.add(new Paragraph(" "));
        document.add(new Paragraph("Total Factura: " + String.format("%.2f €", factura.getPrecio()), fontTitulo));

        document.close();
        return out.toByteArray();
    }
}
