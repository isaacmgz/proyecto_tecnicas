/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.*;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.util.List;


/**
 * Clase que implementa el patrón Visitor para generar reportes PDF
 * a partir de una factura utilizando la librería iText.
 * 
 * Esta clase visita objetos del modelo como `Factura`, `Cliente` y `Venta` 
 * y los representa visualmente en un documento PDF.
 * 
 * @author isaacmgz
 */
public class PdfReportVisitor implements IVisitor {

    // Definición de estilos de fuente
    private static final Font TITLE = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
    private static final Font NORMAL = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

    private Document document;
    private PdfPTable table;

    /**
     * Constructor que inicializa el documento PDF en la ruta de salida dada.
     * 
     * @param outputPath Ruta donde se guardará el archivo PDF generado.
     * @throws Exception si ocurre algún error al abrir o crear el archivo.
     */
    public PdfReportVisitor(String outputPath) throws Exception {
        document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(outputPath));
        document.open();
    }

    /**
     * Cierra el documento PDF agregando la tabla de ventas (si existe) antes de finalizar.
     */
    public void close() {
        if (table != null) {
            try {
                document.add(table);
            } catch (DocumentException e) {
                // Manejo de excepción al añadir la tabla
            }
        }
        document.close();
    }

    /**
     * Visita una factura y genera todo su contenido en el documento PDF.
     * 
     * @param fac Objeto `Factura` a procesar.
     */
    @Override
    public void visitFactura(Factura fac) {
        try {
            // Cabecera del documento
            document.add(new Paragraph("FACTURA Nº " + fac.getIdFactura(), TITLE));
            document.add(new Paragraph("Fecha: " + fac.getFechaEmision(), NORMAL));
            document.add(Chunk.NEWLINE);

            // Añadir datos del cliente
            fac.getCliente().accept(this);
            document.add(Chunk.NEWLINE);

            // Crear tabla de productos
            table = new PdfPTable(4);
            table.setWidths(new float[]{3, 1, 1, 1}); // columnas con diferentes anchos
            table.addCell(new PdfPCell(new Phrase("Producto", NORMAL)));
            table.addCell(new PdfPCell(new Phrase("Cantidad", NORMAL)));
            table.addCell(new PdfPCell(new Phrase("Precio U.", NORMAL)));
            table.addCell(new PdfPCell(new Phrase("Total", NORMAL)));

            // Recorrer cada línea de venta de la factura
            List<Venta> lineas = fac.getLineas();
            for (Venta linea : lineas) {
                linea.accept(this); // visita cada venta individual
            }

            // Calcular y mostrar el total
            document.add(Chunk.NEWLINE);
            double sum = lineas.stream().mapToDouble(Venta::getValorTotal).sum();
            document.add(new Paragraph("TOTAL: $ " + String.format("%.2f", sum), TITLE));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(); // Siempre cerrar el documento
        }
    }

    /**
     * Visita un cliente y añade sus datos al documento PDF.
     * 
     * @param c Objeto `Cliente` a procesar.
     */
    @Override
    public void visitCliente(Cliente c) {
        try {
            document.add(new Paragraph("Cliente: " + c.getNombreCliente(), NORMAL));
            document.add(new Paragraph("NIT: " + c.getIdCliente(), NORMAL));
            document.add(new Paragraph("Email: " + c.getEmailCliente(), NORMAL));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * Visita una venta y añade su información como una fila en la tabla PDF.
     * 
     * @param v Objeto `Venta` a procesar.
     */
    @Override
    public void visitVenta(Venta v) {
        // Añadir una fila a la tabla con la información de la venta
        table.addCell(new PdfPCell(new Phrase(v.getFarmaco().getNombre(), NORMAL)));
        table.addCell(new PdfPCell(new Phrase(String.valueOf(v.getUnidadesVendidas()), NORMAL)));

        // Calcular el precio unitario dividiendo valor total entre unidades
        table.addCell(new PdfPCell(new Phrase(
                String.format("$ %.2f", v.getValorTotal() / v.getUnidadesVendidas()), NORMAL)));

        table.addCell(new PdfPCell(new Phrase(
                String.format("$ %.2f", v.getValorTotal()), NORMAL)));
    }

    /**
     * Visita un fármaco. No implementado para el reporte PDF.
     * 
     * @param f Objeto `Farmaco` (actualmente no usado).
     */
    @Override
    public void visitFarmaco(Farmaco f) {
        // No implementado para este reporte
    }

    /**
     * Visita una presentación. No implementado para el reporte PDF.
     * 
     * @param p Objeto `Presentacion` (actualmente no usado).
     */
    @Override
    public void visitPresentacion(Presentacion p) {
        // No implementado para este reporte
    }

}
