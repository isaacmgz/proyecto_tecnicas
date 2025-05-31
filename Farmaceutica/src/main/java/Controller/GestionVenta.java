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
 * Clase encargada de gestionar las operaciones relacionadas con ventas,
 * incluyendo el registro, carga, cálculo de IVA y visualización en tabla.
 * La información se guarda en un archivo CSV.
 * 
 */

public class GestionVenta {

    // Ruta del archivo CSV donde se almacenan las ventas
    private static final String RUTA = "src/main/java/ArchivosPersistencia/ventas.csv";

    /**
     * Obtiene el siguiente índice disponible para una nueva venta.
     * Busca el último ID generado en el archivo y retorna el siguiente número.
     * 
     * @return El número de ID siguiente para una nueva venta.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public int getNextVentaIndex() throws IOException {
        Path path = Paths.get(RUTA);
        if (Files.notExists(path)) {
            return 1; // Si el archivo no existe, empieza desde 1
        }

        int max = 0;
        try (BufferedReader br = Files.newBufferedReader(path)) {
            br.readLine(); // Saltar la cabecera
            String line;
            while ((line = br.readLine()) != null) {
                String id = line.split(",")[0]; // Leer el campo ID
                if (id.startsWith("V")) {
                    try {
                        int n = Integer.parseInt(id.substring(1)); // Extrae número del ID (sin la V)
                        max = Math.max(max, n); // Guarda el valor máximo encontrado
                    } catch (NumberFormatException e) {
                        // Ignora líneas mal formateadas
                    }
                }
            }
        }
        return max + 1; // Retorna el siguiente número disponible
    }

    /**
     * Registra una nueva venta en el archivo CSV.
     * Si el archivo no existe, lo crea con la cabecera.
     * 
     * @param v Objeto Venta a registrar.
     * @throws IOException Si ocurre un error al escribir en el archivo.
     */
    public void registrarVenta(Venta v) throws IOException {
        Path path = Paths.get(RUTA);

        // Crea el archivo si no existe, incluyendo la cabecera
        if (Files.notExists(path)) {
            try (BufferedWriter bw = Files.newBufferedWriter(path)) {
                bw.write("idVenta,fechaVenta,nombreCliente,nombreFarmaco,"
                        + "presentacion,dosificacion,unidadesVendidas,valorTotal");
                bw.newLine();
            }
        }

        // Genera un ID único para la nueva venta
        int next = getNextVentaIndex();
        String idVenta = "V" + next;
        v.setIdVenta(idVenta);

        // Escribe la venta en el archivo CSV
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

    /**
     * Carga todas las ventas registradas desde el archivo CSV.
     * 
     * @return Lista de objetos Venta cargados del archivo.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public List<Venta> cargarTodas() throws IOException {
        List<Venta> lista = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(Paths.get(RUTA))) {
            String linea = br.readLine(); // Salta la cabecera
            while ((linea = br.readLine()) != null) {
                String[] c = linea.split(",");

                // Reconstrucción del objeto Venta a partir de los campos leídos
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

                lista.add(v); // Agrega la venta reconstruida a la lista
            }
        }

        return lista;
    }

    /**
     * Calcula el valor del IVA de una venta, dado el valor unitario y la cantidad.
     * 
     * @param valorUnitario Precio por unidad del producto.
     * @param cantidad Número de unidades vendidas.
     * @return Monto total del IVA calculado.
     */
    public double calcularIVA(double valorUnitario, int cantidad) {
        double tasaIVA = 0.19; // Tasa estándar del 19%
        return valorUnitario * tasaIVA * cantidad;
    }

    /**
     * Muestra todas las ventas en una tabla Swing (JTable).
     * Carga las ventas y las muestra en un modelo de tabla.
     * 
     * @param tabla Componente JTable donde se mostrarán los datos.
     */
    public void mostrarVentasEnTabla(JTable tabla) {
        try {
            GestionVenta gestor = new GestionVenta();
            List<Venta> listaVentas = gestor.cargarTodas();

            // Configura el modelo de la tabla
            DefaultTableModel modelo = new DefaultTableModel();
            modelo.addColumn("idVenta");
            modelo.addColumn("Fecha Venta");
            modelo.addColumn("Cliente");
            modelo.addColumn("Farmaco");
            modelo.addColumn("Presentacion");
            modelo.addColumn("Cantidad");
            modelo.addColumn("Valor Total");

            // Llena la tabla con los datos de cada venta
            for (Venta v : listaVentas) {
                modelo.addRow(new Object[]{
                    v.getIdVenta(),
                    v.getFechaVenta().toString(),
                    v.getCliente().getNombreCliente(),
                    v.getFarmaco().getNombre(),
                    v.getPresentacion().getTipo(),
                    v.getUnidadesVendidas(),
                    v.getValorTotal() + " $" // Agrega el símbolo de moneda
                });
            }

            tabla.setModel(modelo); // Aplica el modelo a la tabla

        } catch (IOException ex) {
            ex.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null, "Error al cargar las ventas.");
        }
    }

}
