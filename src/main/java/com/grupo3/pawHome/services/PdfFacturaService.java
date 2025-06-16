package com.grupo3.pawHome.services;

import com.grupo3.pawHome.dtos.FacturaDTO;
import com.grupo3.pawHome.dtos.LineaFacturaDTO;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

@Service
public class PdfFacturaService {

    public byte[] generarFacturaPDF(FacturaDTO factura) throws Exception {
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, out);
        document.open();

        // Fuentes
        Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Font fontSubtitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
        Font fontNormal = FontFactory.getFont(FontFactory.HELVETICA, 12);
        Font fontTablaHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);

        // Logo (si lo tienes, puedes usar Image.getInstance(path))
        // 1. Logo
        InputStream logoStream = getClass().getClassLoader().getResourceAsStream("static/images/Logocompletosinfondo.png");
        if (logoStream != null) {
            Image logo = Image.getInstance(IOUtils.toByteArray(logoStream));
            logo.scaleAbsolute(100, 100);
            logo.setAlignment(Element.ALIGN_LEFT);
            document.add(logo);
        }

        // Datos de la empresa (manuales)
        Paragraph empresa = new Paragraph("PawHome\nAv. Dr. Gómez Ulla, 13\nCádiz, 11003\nEspaña\nTel: 956 26 07 41\npawhome@gmail.com\nNIF: B00000001", fontNormal);
        document.add(empresa);

        // Espaciado
        document.add(new Paragraph(" "));

        // Fecha y número de factura
        Paragraph tituloFactura = new Paragraph("FACTURA Nº: " + factura.getIdFactura(), fontTitulo);
        tituloFactura.setAlignment(Element.ALIGN_RIGHT);
        document.add(tituloFactura);
        Paragraph fechaFactura = new Paragraph("Fecha: " + factura.getFecha(), fontNormal);
        fechaFactura.setAlignment(Element.ALIGN_RIGHT);
        document.add(fechaFactura);

        document.add(new Paragraph(" "));

        // Datos del cliente
        document.add(new Paragraph("Cliente:", fontSubtitulo));
        Paragraph cliente = new Paragraph(
                factura.getNombreUsuario() + " " + factura.getApellidosUsuario() + "\n" +
                        factura.getDireccionUsuario() + "\n" +
                        factura.getCiudadUsuario() + ", " + factura.getCpUsuario() + "\n" +
                        "DNI: " + factura.getDniUsuario() + "\n" +
                        "Tel: " + factura.getTelefonoUsuario(),
                fontNormal
        );
        document.add(cliente);

        document.add(new Paragraph(" "));

        // Tabla de productos
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{3, 2, 2, 2, 2});

        // Cabeceras
        Stream.of("Producto", "Cantidad", "Precio sin IVA", "Precio con IVA", "Total")
                .forEach(headerTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(1);
                    header.setPhrase(new Phrase(headerTitle, fontTablaHeader));
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(header);
                });

        double totalIva = 0.0;
        double baseImponible = 0.0;

        for (LineaFacturaDTO linea : factura.getLineas()) {
            double precioConIva = linea.getPrecioUnitario();
            double precioSinIva = precioConIva / 1.21;
            double totalLinea = linea.getCantidad() * precioConIva;
            double ivaLinea = totalLinea - (linea.getCantidad() * precioSinIva);

            baseImponible += linea.getCantidad() * precioSinIva;
            totalIva += ivaLinea;

            table.addCell(new PdfPCell(new Phrase(linea.getNombre(), fontNormal)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(linea.getCantidad()), fontNormal)));
            table.addCell(new PdfPCell(new Phrase(String.format("%.2f €", precioSinIva), fontNormal)));
            table.addCell(new PdfPCell(new Phrase(String.format("%.2f €", precioConIva), fontNormal)));
            table.addCell(new PdfPCell(new Phrase(String.format("%.2f €", totalLinea), fontNormal)));
        }

        document.add(table);

        document.add(new Paragraph(" "));

        // Totales
        PdfPTable totales = new PdfPTable(2);
        totales.setWidthPercentage(40);
        totales.setHorizontalAlignment(Element.ALIGN_RIGHT);

        totales.addCell(new PdfPCell(new Phrase("Base Imponible:", fontNormal)));
        totales.addCell(new PdfPCell(new Phrase(String.format("%.2f €", baseImponible), fontNormal)));

        totales.addCell(new PdfPCell(new Phrase("I.V.A. (21%):", fontNormal)));
        totales.addCell(new PdfPCell(new Phrase(String.format("%.2f €", totalIva), fontNormal)));

        totales.addCell(new PdfPCell(new Phrase("TOTAL FACTURA:", fontSubtitulo)));
        totales.addCell(new PdfPCell(new Phrase(String.format("%.2f €", factura.getPrecio()), fontSubtitulo)));

        document.add(totales);

        document.add(new Paragraph(" "));
        document.add(new Paragraph("Para acreditar la validez de esta factura, debe llevar el sello de la empresa receptora o ser firmada por un responsable de la misma.", fontNormal));

        document.close();
        return out.toByteArray();
    }
}
