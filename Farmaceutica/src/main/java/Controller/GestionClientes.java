/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Cliente;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author isaacmgz
 */
public class GestionClientes {

    private static final String RUTA = "src/main/java/ArchivosPersistencia/clientes.csv";

    public List<Cliente> cargarTodos() throws IOException {
        List<Cliente> lista = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(RUTA))) {
            String linea = br.readLine(); // salta cabecera
            while ((linea = br.readLine()) != null) {
                String[] campos = linea.split(",");
                Cliente c = new Cliente(
                        campos[1],
                        campos[2],
                        campos[3]
                );
                lista.add(c);
            }
        }
        return lista;
    }

    // Funcion necesaria para actualizar la lista¡  
    public void guardarTodos(List<Cliente> lista) throws IOException {
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(RUTA))) {
            bw.write("id,nombreCliente,idCliente,emailCliente");
            bw.newLine();
            for (Cliente c : lista) {
                bw.write(
                        c.getIdCliente() + ","
                        + c.getNombreCliente() + ","
                        + c.getIdCliente() + ","
                        + c.getEmailCliente()
                );
                bw.newLine();
            }
        }
    }

    public void agregarCliente(Cliente nuevo) throws IOException {
        List<Cliente> lista = cargarTodos();
        lista.add(nuevo);
        guardarTodos(lista);
    }

    public void eliminarPorIdCliente(String idCli) throws IOException {
        List<Cliente> lista = cargarTodos();
        lista.removeIf(c -> c.getIdCliente().equals(idCli));
        guardarTodos(lista);
    }
    
    public Cliente buscarPorIdCliente(String idCli) throws IOException {
    for (Cliente c : cargarTodos()) {
        if (c.getIdCliente().equals(idCli)) {
            return c;
        }
    }
    return null;
}

    public void actualizarCliente(Cliente actualizado) throws IOException {
        List<Cliente> lista = cargarTodos();

        for (int i = 0; i < lista.size(); i++) {
            Cliente c = lista.get(i);

            if (c.getIdCliente().equals(actualizado.getIdCliente())) {
                // sustituye la entrada antigua por la nueva
                lista.set(i, actualizado);
                break;
            }
        }
        guardarTodos(lista);
    }
}
