/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Farmaco;
import Model.Presentacion;
import java.io.IOException;
import java.util.List;
import Controller.GestionFarmacos.*;
import Model.Cliente;
import Model.Venta;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;

/**
 *
 * @author isaacmgz
 */
public class GestionVenta {

    private static final String RUTA = "src/main/java/ArchivosPersistencia/ventas.csv";

    // Registra una venta nueva en el CSV
    public void registrarVenta(Venta v) throws IOException {
        // Asegura que exista el archivo con cabecera
        Path path = Paths.get(RUTA);
        if (Files.notExists(path)) {
            try (BufferedWriter bw = Files.newBufferedWriter(path)) {
                bw.write("idVenta,fechaVenta,nombreCliente,nombreFarmaco,"
                        + "presentacion,dosificacion,valorUnidad");
                bw.newLine();
            }
        }

        // Añade línea con datos de la venta
        try (BufferedWriter bw = Files.newBufferedWriter(
                path,
                StandardOpenOption.APPEND)) {

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
}

/*
    Construye un objeto Venta tras la venta.

    Llama a new VentaRepository().registrarVenta(miVenta);
 */
