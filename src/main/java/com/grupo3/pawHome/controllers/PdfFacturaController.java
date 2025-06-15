package com.grupo3.pawHome.controllers;

import com.grupo3.pawHome.config.MyUserDetails;
import com.grupo3.pawHome.dtos.FacturaDTO;
import com.grupo3.pawHome.entities.Adopcion;
import com.grupo3.pawHome.entities.Usuario;
import com.grupo3.pawHome.services.AdopcionService;
import com.grupo3.pawHome.services.FacturaService;
import com.grupo3.pawHome.services.PdfFacturaService;
import com.grupo3.pawHome.services.UsuarioService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class PdfFacturaController {
    private final UsuarioService usuarioService;
    private final FacturaService facturaService;
    private final PdfFacturaService pdfFacturaService;
    private final AdopcionService adopcionService;

    public PdfFacturaController(UsuarioService usuarioService,
                                FacturaService facturaService,
                                PdfFacturaService pdfFacturaService,
                                AdopcionService adopcionService) {
        this.usuarioService = usuarioService;
        this.facturaService = facturaService;
        this.pdfFacturaService = pdfFacturaService;
        this.adopcionService = adopcionService;
    }

    @GetMapping("/perfil/facturas/pdf/{id}")
    public ResponseEntity<byte[]> descargarPDF(@PathVariable Integer id, @AuthenticationPrincipal MyUserDetails userDetails) throws Exception {
        Usuario usuario = userDetails.getUsuario();
        List<FacturaDTO> facturasUsuario = facturaService.obtenerFacturasPorUsuario(usuario.getId());

        // Filtrar por ID
        FacturaDTO factura = facturasUsuario.stream()
                .filter(f -> f.getIdFactura().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Factura no encontrada o no pertenece al usuario."));

        byte[] pdfBytes = pdfFacturaService.generarFacturaPDF(factura);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", "factura_" + id + ".pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/adopciones/{id}/documento")
    public void generarDocumentoAdopcion(@PathVariable Integer id, HttpServletResponse response) throws Exception {
        Optional<Adopcion> adopcionOpt = adopcionService.findById(id);
        if (adopcionOpt.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Adopción no encontrada");
            return;
        }

        Adopcion adopcion = adopcionOpt.get();

        // Preparar respuesta HTTP
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=adopcion_" + id + ".pdf");

        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        // 1. Logo
        String logoPath = "src/main/resources/static/images/Logocompletosinfondo.png";
        if (Files.exists(Paths.get(logoPath))) {
            Image logo = Image.getInstance(logoPath);
            logo.scaleAbsolute(100, 100);
            logo.setAlignment(Element.ALIGN_LEFT);
            document.add(logo);
        }

        // 2. Título del contrato
        Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
        Paragraph titulo = new Paragraph("CONTRATO DE ADOPCIÓN DE ANIMAL DE COMPAÑÍA", titleFont);
        titulo.setAlignment(Element.ALIGN_CENTER);
        titulo.setSpacingAfter(20);
        document.add(titulo);

        // 3. Introducción legal
        Paragraph intro = new Paragraph(
                "Entre la Fundación Protectora de Animales y el/la ciudadano/a adoptante identificado a continuación, " +
                        "se celebra el presente contrato de adopción, en cumplimiento de las disposiciones legales sobre protección animal, " +
                        "conforme a la Ley 84 de 1989 y demás normativas vigentes.\n\n", new Font(Font.FontFamily.HELVETICA, 11));
        intro.setAlignment(Element.ALIGN_JUSTIFIED);
        intro.setSpacingAfter(15);
        document.add(intro);

        // 4. Datos del Adoptante
        document.add(new Paragraph("DATOS DEL ADOPTANTE", subTitulo()));

        PdfPTable tablaAdoptante = new PdfPTable(2);
        tablaAdoptante.setWidthPercentage(100);
        tablaAdoptante.setSpacingAfter(15);
        tablaAdoptante.addCell(celdaHeader("Nombre del Adoptante"));
        tablaAdoptante.addCell(adopcion.getUsuario().getPerfilDatos().getNombre() + " " + adopcion.getUsuario().getPerfilDatos().getApellidos());
        tablaAdoptante.addCell(celdaHeader("Email"));
        tablaAdoptante.addCell(adopcion.getUsuario().getEmail());
        tablaAdoptante.addCell(celdaHeader("DNI"));
        tablaAdoptante.addCell(adopcion.getUsuario().getPerfilDatos().getDni());
        tablaAdoptante.addCell(celdaHeader("Dirección"));
        tablaAdoptante.addCell(adopcion.getUsuario().getPerfilDatos().getDireccion());
        tablaAdoptante.addCell(celdaHeader("Ocupación"));
        tablaAdoptante.addCell(adopcion.getOcupacion());
        tablaAdoptante.addCell(celdaHeader("Tiene hijos"));
        tablaAdoptante.addCell(adopcion.getHijos());
        tablaAdoptante.addCell(celdaHeader("Experiencia con animales"));
        tablaAdoptante.addCell(adopcion.getExperienciaPrevia());
        document.add(tablaAdoptante);

        // 5. Datos del Animal
        document.add(new Paragraph("DATOS DEL ANIMAL", subTitulo()));
        PdfPTable tablaAnimal = new PdfPTable(2);
        tablaAnimal.setWidthPercentage(100);
        tablaAnimal.setSpacingAfter(15);
        tablaAnimal.addCell(celdaHeader("Nombre del Animal"));
        tablaAnimal.addCell(adopcion.getAnimal().getNombre());
        tablaAnimal.addCell(celdaHeader("Fecha de Nacimiento"));
        tablaAnimal.addCell(String.valueOf(adopcion.getAnimal().getFechaNacimiento()));
        tablaAnimal.addCell(celdaHeader("Género"));
        tablaAnimal.addCell(adopcion.getAnimal().isGenero() ? "Macho" : "Hembra");
        document.add(tablaAnimal);

        // 6. Cláusulas del contrato
        document.add(new Paragraph("CLÁUSULAS DEL CONTRATO", subTitulo()));

        Paragraph clausulas = new Paragraph();
        clausulas.setSpacingBefore(10);
        clausulas.setSpacingAfter(10);

        clausulas.add(new Paragraph("1. OBJETO DEL CONTRATO", boldText()));
        clausulas.add(new Paragraph(
                "El presente contrato tiene por objeto la entrega en adopción del animal mencionado, bajo el compromiso del adoptante de brindarle un hogar seguro, estable y digno.", normalText()));

        clausulas.add(new Paragraph("2. OBLIGACIONES DEL ADOPTANTE", boldText()));
        clausulas.add(new Paragraph(
                "- Proporcionar alimentación, atención veterinaria y afecto.\n" +
                        "- No abandonar ni transferir la tenencia sin informar a la Fundación.\n" +
                        "- Permitir visitas de seguimiento si son requeridas.\n" +
                        "- Asumir la total responsabilidad por su bienestar.", normalText()));

        clausulas.add(new Paragraph("3. CONDICIONES DE VIDA", boldText()));
        clausulas.add(new Paragraph("Lugar donde vivirá: " + adopcion.getDondeVivira(), normalText()));
        clausulas.add(new Paragraph("Tiempo que pasará solo: " + adopcion.getTiempoSolo() + " horas al día", normalText()));
        clausulas.add(new Paragraph("Veterinario de confianza: " + adopcion.getVeterinario(), normalText()));

        clausulas.add(new Paragraph("4. ACEPTACIÓN DE CONDICIONES", boldText()));
        clausulas.add(new Paragraph("El adoptante declara aceptar los términos del presente contrato y comprometerse al cumplimiento de los mismos de forma permanente.", normalText()));

        document.add(clausulas);

        // 7. Firma
        document.add(new Paragraph("FIRMA DE CONFORMIDAD", subTitulo()));

        PdfPTable firmaTable = new PdfPTable(2);
        firmaTable.setWidthPercentage(100);
        firmaTable.setSpacingBefore(30);
        firmaTable.addCell(celdaHeader("Firma del Adoptante"));
        firmaTable.addCell(celdaHeader("Fecha"));
        firmaTable.addCell(adopcion.getFirma());
        firmaTable.addCell(adopcion.getFechaFormulario().toString());
        document.add(firmaTable);

        document.close();
    }

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

//    @GetMapping("/adopciones/{id}/documento")
//    public void generarDocumentoAdopcion(@PathVariable Integer id, HttpServletResponse response) throws Exception {
//        Optional<Adopcion> adopcionOpt = adopcionService.findById(id);
//        if (adopcionOpt.isEmpty()) {
//            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Adopción no encontrada");
//            return;
//        }
//
//        Adopcion adopcion = adopcionOpt.get();
//
//        response.setContentType("application/pdf");
//        response.setHeader("Content-Disposition", "inline; filename=adopcion_" + id + ".pdf");
//
//        pdfService.generarPdfAdopcion(adopcion, response.getOutputStream());
//    }

}
