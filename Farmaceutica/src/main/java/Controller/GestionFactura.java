/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Cliente;
import Model.Farmaco;
import Model.Presentacion;
import Model.Venta;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import javax.swing.*;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;

/**
 *
 * @author isaacmgz
 */
public class GestionFactura {

    private static final String OUTPUT_PATH = "src/main/java/ArchivosPersistencia/factura_simple.pdf";
    private static final String[] CSV_HEADER = {
        "idVenta", "fechaVenta", "nombreCliente", "nombreFarmaco",
        "presentacion", "dosificacion", "unidadesVendidas", "valorTotal"
    };
    private static final Font TITLE_FONT = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
    private static final Font LABEL_FONT = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
    private static final DecimalFormat MONEY = new DecimalFormat("#,##0.00 $");
    private static final BaseColor LIGHT_BLUE = new BaseColor(173, 216, 230);
    private Document document;

    public void crearFactura(JTable table, Venta venta) throws Exception {
        initDocument();
        addHeaderSection();
        addDivider();
        addClientSection(venta);
        addDivider();
        addTableSection(table);
        addTotalsSection(table, venta.getUnidadesVendidas(), venta.getValorTotal());
        closeDocument();
    }

    private void initDocument() throws Exception {
        document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(OUTPUT_PATH));
        document.open();
    }

    private void addHeaderSection() throws Exception {
        PdfPTable table = new PdfPTable(new float[]{1, 3});
        table.setWidthPercentage(100);

        Image logo = Image.getInstance(getClass().getResource("/images/download.png"));
        logo.scaleToFit(80, 80);
        table.addCell(createCell(logo, Rectangle.NO_BORDER));

        PdfPCell info = new PdfPCell();
        info.setBorder(Rectangle.NO_BORDER);
        info.addElement(new Paragraph("Farmaceutica SAS", TITLE_FONT));
        info.addElement(new Paragraph("CIF/NIF: 123456", LABEL_FONT));
        info.addElement(new Paragraph("Direccion: 123 Aº #456"));
        info.addElement(new Paragraph("Tel: +57 123456   Email: My@Company.com"));
        table.addCell(info);

        document.add(table);
    }

    private void addClientSection(Venta venta) throws DocumentException {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(5);

        PdfPCell left = createCell("FACTURAR A:", LABEL_FONT, true);
        left.addElement(new Paragraph(venta.getCliente().getNombreCliente()));
        left.addElement(new Paragraph("CIF: " + venta.getCliente().getIdCliente()));
        left.addElement(new Paragraph("Email: " + venta.getCliente().getEmailCliente()));
        table.addCell(left);

        PdfPCell right = createCell(
                String.format("FACTURA Nº: %s\nFecha emisión: %s\nVencimiento: %s",
                        venta.getIdVenta(), LocalDate.now(), LocalDate.now().plusYears(1)
                ), LABEL_FONT, false
        );
        table.addCell(right);

        document.add(table);
    }

    private void addTableSection(JTable swTable) throws DocumentException {
        PdfPTable pdfTable = new PdfPTable(swTable.getColumnCount());
        pdfTable.setWidthPercentage(100);
        pdfTable.setSpacingBefore(10);
        pdfTable.getDefaultCell().setBorderColor(LIGHT_BLUE);

        // headers
        for (int i = 0; i < swTable.getColumnCount(); i++) {
            pdfTable.addCell(swTable.getColumnName(i));
        }

        // rows
        for (int row = 0; row < swTable.getRowCount(); row++) {
            for (int col = 0; col < swTable.getColumnCount(); col++) {
                Object cell = swTable.getValueAt(row, col);
                pdfTable.addCell(cell != null ? cell.toString() : "");
            }
        }

        document.add(pdfTable);
    }

    private void addTotalsSection(JTable table, int unitsSold, double totalValue) throws DocumentException {
        PdfPTable summary = new PdfPTable(new float[]{7, 2});
        summary.setWidthPercentage(50);
        summary.setHorizontalAlignment(Element.ALIGN_RIGHT);
        summary.setSpacingBefore(10);

        summary.addCell(createCell("UNIDADES VENDIDAS:", LABEL_FONT, true));
        summary.addCell(createCell(String.valueOf(unitsSold), LABEL_FONT, false));

        summary.addCell(createCell("TOTAL:", LABEL_FONT, true));
        summary.addCell(createCell(MONEY.format(totalValue), LABEL_FONT, false));

        document.add(summary);
    }

    private void addDivider() throws DocumentException {
        LineSeparator ls = new LineSeparator(1f, 100f, LIGHT_BLUE, Element.ALIGN_CENTER, -2f);
        document.add(new Chunk(ls));
    }

    private PdfPCell createCell(String text, Font font, boolean noBorder) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        if (noBorder) {
            cell.setBorder(Rectangle.NO_BORDER);
        }
        cell.setPadding(5);
        return cell;
    }

    private PdfPCell createCell(Image img, int border) {
        PdfPCell cell = new PdfPCell(img);
        cell.setBorder(border);
        return cell;
    }

    private void closeDocument() {
        if (document != null && document.isOpen()) {
            document.close();
        }
    }
}
