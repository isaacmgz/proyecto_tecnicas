package Controller;

import Model.Venta;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import javax.swing.JTable;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Locale;

/**
 *
 * @author isaacmgz
 */

public class GestionFactura {

    private static final String OUTPUT_PATH = "src/main/java/ArchivosPersistencia/factura_simple.pdf";
    private static final Font TITLE_FONT = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
    private static final Font LABEL_FONT = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
    private static final Font VALUE_FONT = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
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
        addTotalsSection(table);
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

        String logoPath = "src/main/java/images/download.png";
        Image logo = Image.getInstance(logoPath);
        logo.scaleToFit(80, 80);
        table.addCell(createCell(logo, Rectangle.NO_BORDER));

        PdfPCell info = new PdfPCell();
        info.setBorder(Rectangle.NO_BORDER);
        info.addElement(new Paragraph("Farmaceutica SAS", TITLE_FONT));
        info.addElement(new Paragraph("CIF/NIF: 123456", LABEL_FONT));
        info.addElement(new Paragraph("Direccion: 123 Aº #456", VALUE_FONT));
        info.addElement(new Paragraph("Tel: +57 123456   Email: My@Company.com", VALUE_FONT));
        table.addCell(info);

        document.add(table);
    }

    private void addClientSection(Venta venta) throws DocumentException {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(5);

        PdfPCell left = createCell("FACTURAR A:", LABEL_FONT, true);
        left.addElement(new Paragraph(venta.getCliente().getNombreCliente(), VALUE_FONT));
        left.addElement(new Paragraph("NIT: " + venta.getCliente().getIdCliente(), VALUE_FONT));
        left.addElement(new Paragraph("Email: " + venta.getCliente().getEmailCliente(), VALUE_FONT));
        table.addCell(left);

        PdfPCell right = createCell(
                String.format(Locale.getDefault(),
                        "FACTURA Nº: %s\nFecha emisión: %s\nVencimiento: %s",
                        venta.getIdVenta(), LocalDate.now(), LocalDate.now().plusYears(1)
                ), VALUE_FONT, false
        );
        table.addCell(right);

        document.add(table);
    }

    private void addTableSection(JTable swTable) throws DocumentException {
        PdfPTable pdfTable = new PdfPTable(swTable.getColumnCount());
        pdfTable.setWidthPercentage(100);
        pdfTable.setSpacingBefore(10);
        pdfTable.getDefaultCell().setBorderColor(LIGHT_BLUE);

        for (int i = 0; i < swTable.getColumnCount(); i++) {
            pdfTable.addCell(createCell(swTable.getColumnName(i), LABEL_FONT, false));
        }

        for (int row = 0; row < swTable.getRowCount(); row++) {
            for (int col = 0; col < swTable.getColumnCount(); col++) {
                Object cell = swTable.getValueAt(row, col);
                pdfTable.addCell(createCell(cell != null ? cell.toString() : "", VALUE_FONT, false));
            }
        }

        document.add(pdfTable);
    }

    private void addTotalsSection(JTable table) throws DocumentException {
        double sumaImpuesto = 0;
        double sumaTotal = 0;
        int colGravamen = 5;
        int colTotal = 6;
        for (int r = 0; r < table.getRowCount(); r++) {
            String gravStr = table.getValueAt(r, colGravamen).toString()
                                .replace("$", "").replace(",", "");
            String totStr  = table.getValueAt(r, colTotal).toString()
                                .replace("$", "").replace(",", "");
            try {
                sumaImpuesto += Double.parseDouble(gravStr);
            } catch (NumberFormatException ex) {}
            try {
                sumaTotal += Double.parseDouble(totStr);
                sumaTotal += Double.parseDouble(gravStr);
            } catch (NumberFormatException ex) {}
        }

        PdfPTable summary = new PdfPTable(new float[]{5, 3});
        summary.setWidthPercentage(50);
        summary.setHorizontalAlignment(Element.ALIGN_RIGHT);
        summary.setSpacingBefore(10);

        summary.addCell(createCell("IMPUESTOS TOTALES:", LABEL_FONT, true));
        summary.addCell(createCell(MONEY.format(sumaImpuesto), VALUE_FONT, false));

        summary.addCell(createCell("TOTAL A PAGAR:", LABEL_FONT, true));
        summary.addCell(createCell(MONEY.format(sumaTotal), VALUE_FONT, false));

        document.add(summary);
    }

    private PdfPCell createCell(String text, Font font, boolean noBorder) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        if (noBorder) cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(5);
        return cell;
    }

    private PdfPCell createCell(Image img, int border) {
        PdfPCell cell = new PdfPCell(img);
        cell.setBorder(border);
        return cell;
    }

    private void addDivider() throws DocumentException {
        LineSeparator ls = new LineSeparator(1f, 100f, LIGHT_BLUE, Element.ALIGN_CENTER, -2f);
        document.add(new Chunk(ls));
    }

    private void closeDocument() {
        if (document != null && document.isOpen()) document.close();
    }
}

