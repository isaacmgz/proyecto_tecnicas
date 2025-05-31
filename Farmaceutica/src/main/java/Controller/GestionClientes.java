package Controller;

import Model.Cliente;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Clase que gestiona la persistencia y operaciones sobre objetos de tipo Cliente.
 * Implementa la interfaz ILista<Cliente> para estandarizar las operaciones CRUD.
 * Los datos se almacenan en un archivo CSV.
 * 
 */

public class GestionClientes implements ILista<Cliente> {

    // Ruta del archivo CSV donde se guardan los datos de los clientes.
    private static final String RUTA = "src/main/java/ArchivosPersistencia/clientes.csv";

    /**
     * Agrega un nuevo cliente al archivo CSV.
     * 
     * @param nuevo El cliente a agregar.
     */
    @Override
    public void agregar(Cliente nuevo) {
        try {
            List<Cliente> lista = cargarTodos();
            lista.add(nuevo);
            guardarTodos(lista);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,
                    "Error agregando cliente: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Elimina un cliente del archivo CSV usando su ID.
     * 
     * @param idCli El ID del cliente a eliminar.
     */
    @Override
    public void eliminar(String idCli) {
        try {
            List<Cliente> lista = cargarTodos();
            lista.removeIf(c -> c.getIdCliente().equals(idCli));
            guardarTodos(lista);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,
                    "Error eliminando cliente: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Obtiene un cliente específico a partir de su ID.
     * 
     * @param idCli El ID del cliente a buscar.
     * @return El cliente encontrado, o null si no existe.
     */
    @Override
    public Cliente obtener(String idCli) {
        try {
            for (Cliente c : cargarTodos()) {
                if (c.getIdCliente().equals(idCli)) {
                    return c;
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,
                    "Error buscando cliente: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    /**
     * Carga todos los clientes almacenados en el archivo CSV.
     * 
     * @return Una lista con todos los clientes.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    @Override
    public List<Cliente> cargarTodos() throws IOException {
        List<Cliente> lista = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(RUTA))) {
            String linea = br.readLine(); // Salta la cabecera
            while ((linea = br.readLine()) != null) {
                String[] campos = linea.split(",");
                if (campos.length >= 4) {
                    Cliente c = new Cliente(
                            campos[1], // nombreCliente
                            campos[2], // idCliente
                            campos[3]  // emailCliente
                    );
                    lista.add(c);
                }
            }
        }
        return lista;
    }

    /**
     * Guarda todos los clientes en el archivo CSV, sobrescribiendo el archivo actual.
     * 
     * @param lista Lista de clientes a guardar.
     * @throws IOException Si ocurre un error al escribir el archivo.
     */
    @Override
    public void guardarTodos(List<Cliente> lista) throws IOException {
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(RUTA))) {
            bw.write("id,nombreCliente,idCliente,emailCliente");
            bw.newLine();
            int idx = 1;
            for (Cliente c : lista) {
                bw.write(idx++ + ","
                        + c.getNombreCliente() + ","
                        + c.getIdCliente() + ","
                        + c.getEmailCliente());
                bw.newLine();
            }
        }
    }

    /**
     * Actualiza los datos de un cliente existente en el archivo CSV.
     * 
     * @param actualizado Cliente con la información actualizada.
     */
    public void actualizarCliente(Cliente actualizado) {
        try {
            List<Cliente> lista = cargarTodos();
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getIdCliente().equals(actualizado.getIdCliente())) {
                    lista.set(i, actualizado);
                    break;
                }
            }
            guardarTodos(lista);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,
                    "Error actualizando cliente: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Carga todos los clientes y los muestra en una tabla JTable.
     * 
     * @param tabla El componente JTable donde se mostrarán los datos.
     */
    public void mostrarClientesEnTabla(JTable tabla) {
        try {
            List<Cliente> lista = cargarTodos();
            DefaultTableModel modelo = new DefaultTableModel();
            modelo.addColumn("Nombre");
            modelo.addColumn("ID Cliente");
            modelo.addColumn("Email");
            for (Cliente c : lista) {
                modelo.addRow(new Object[]{
                    c.getNombreCliente(),
                    c.getIdCliente(),
                    c.getEmailCliente()
                });
            }
            tabla.setModel(modelo);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,
                    "Error al cargar los clientes: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

