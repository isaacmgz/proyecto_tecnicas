package Controller;

import Model.*;
import java.io.IOException;
import java.util.List;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author isaacmgz
 */

public class GestionVenta {

    private static final String RUTA = "src/main/java/ArchivosPersistencia/ventas.csv";

    private int getNextVentaIndex() throws IOException {
        Path path = Paths.get(RUTA);
        if (Files.notExists(path)) {
            return 1;
        }
        int max = 0;
        try (BufferedReader br = Files.newBufferedReader(path)) {
            br.readLine(); // saltar cabecera
            String line;
            while ((line = br.readLine()) != null) {
                String id = line.split(",")[0];    
                if (id.startsWith("V")) {
                    try {
                        int n = Integer.parseInt(id.substring(1));
                        max = Math.max(max, n);
                    } catch (NumberFormatException e) {
                    }
                }
            }
        }
        return max + 1;
    }

    // ------ Registra una venta nueva en el CSV. --------
    public void registrarVenta(Venta v) throws IOException {
        // Asegura que exista el archivo con cabecera
        Path path = Paths.get(RUTA);
        if (Files.notExists(path)) {
            try (BufferedWriter bw = Files.newBufferedWriter(path)) {
                bw.write("idVenta,fechaVenta,nombreCliente,nombreFarmaco,"
                        + "presentacion,dosificacion,unidadesVendidas,valorTotal");
                bw.newLine();
            }
        }

        // calcula el siguiente ID
        int next = getNextVentaIndex();
        String idVenta = "V" + next;
        v.setIdVenta(idVenta);

        try (BufferedWriter bw = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            String linea = String.join(",",
                    v.getIdVenta(),
                    v.getFechaVenta().toString(),
                    v.getCliente().getNombreCliente(),
                    v.getFarmaco().getNombre(),
                    v.getPresentacion().getTipo(),
                    v.getDosificacion(),
                    String.valueOf(v.getUnidadesVendidas()),
                    String.valueOf(v.getValorTotal())
            );
            bw.write(linea);
            bw.newLine();
        }
    }

    // Lee todas las ventas del CSV
    public List<Venta> cargarTodas() throws IOException {
        List<Venta> lista = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(RUTA))) {
            String linea = br.readLine(); // salta cabecera
            while ((linea = br.readLine()) != null) {
                String[] c = linea.split(",");

                String idVenta = c[0];
                LocalDate fechaVenta = LocalDate.parse(c[1]);
                Cliente cliente = new Cliente(c[2]);
                Farmaco farmaco = new Farmaco(c[3]);
                Presentacion pres = new Presentacion(c[4], Collections.emptyList());
                String dosificacion = c[5];
                int unidadesVendidas = Integer.parseInt(c[6]);
                double valorTotal = Double.parseDouble(c[7]);

                Venta v = new Venta(
                        idVenta,
                        fechaVenta,
                        cliente,
                        farmaco,
                        pres,
                        dosificacion,
                        unidadesVendidas,
                        valorTotal
                );

                lista.add(v);
            }
        }
        return lista;
    }

    public double calcularIVA(double valorUnitario, int cantidad) {
        double tasaIVA = 0.19;
        return valorUnitario * tasaIVA * cantidad;
    }

    public void mostrarVentasEnTabla(JTable tabla) {
        try {
            GestionVenta gestor = new GestionVenta();

            List<Venta> listaVentas = gestor.cargarTodas(); // Asegúrate de tener este método

            DefaultTableModel modelo = new DefaultTableModel();
            modelo.addColumn("idVenta");
            modelo.addColumn("Fecha Venta");
            modelo.addColumn("Cliente");
            modelo.addColumn("Farmaco");
            modelo.addColumn("Presentacion");
            modelo.addColumn("Cantidad");
            modelo.addColumn("Valor Total");

            for (Venta v : listaVentas) {
                modelo.addRow(new Object[]{
                    v.getIdVenta(),
                    v.getFechaVenta().toString(),
                    v.getCliente().getNombreCliente(),
                    v.getFarmaco().getNombre(),
                    v.getPresentacion().getTipo(),
                    v.getUnidadesVendidas(),
                    v.getValorTotal() + " $"
                });
            }

            tabla.setModel(modelo);

        } catch (IOException ex) {
            ex.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null, "Error al cargar las ventas.");
        }
    }

}
