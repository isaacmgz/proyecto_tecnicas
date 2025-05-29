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
 *
 * @author isaacmgz
 */

public class GestionClientes implements ILista<Cliente> {

    private static final String RUTA = "src/main/java/ArchivosPersistencia/clientes.csv";

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

    @Override
    public List<Cliente> cargarTodos() throws IOException {
        List<Cliente> lista = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(RUTA))) {
            String linea = br.readLine(); // salta cabecera
            while ((linea = br.readLine()) != null) {
                String[] campos = linea.split(",");
                if (campos.length >= 4) {
                    Cliente c = new Cliente(
                            campos[1], // nombre
                            campos[2], // idCliente
                            campos[3] // email
                    );
                    lista.add(c);
                }
            }
        }
        return lista;
    }

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

    // --- Actualiza cliente existente. ------------
     
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

    // -- Muestra todos los clientes en un JTable. --
     
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
