package Controller;

import Model.Farmaco;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

/**
 * Clase encargada de la gestión de fármacos mediante persistencia en un archivo JSON.
 * Implementa las operaciones básicas de agregar, eliminar, obtener, listar y guardar fármacos.
 * Utiliza la librería Gson para la serialización y deserialización de objetos.
 * 
 * 
 */
public class GestionFarmacos implements ILista<Farmaco> {

    // Ruta al archivo JSON que contiene la lista de fármacos
    private static final String RUTA = "src/main/java/ArchivosPersistencia/farmacos.json";
    
    // Instancia de Gson para manejar la conversión entre objetos y JSON
    private static final Gson gson = new Gson();

    /**
     * Agrega un nuevo fármaco a la lista persistente.
     * 
     * @param nuevo El objeto Farmaco que se desea agregar.
     */
    @Override
    public void agregar(Farmaco nuevo) {
        try {
            List<Farmaco> lista = cargarTodos(); // Carga la lista actual desde el archivo
            lista.add(nuevo); // Agrega el nuevo fármaco
            guardarTodos(lista); // Guarda la lista actualizada de vuelta en el archivo
        } catch (IOException ex) {
            ex.printStackTrace(); // Imprime el error en consola si ocurre una excepción de E/S
        }
    }

    /**
     * Elimina un fármaco de la lista persistente, dado su ID como cadena.
     * 
     * @param id El ID del fármaco a eliminar (en formato String).
     */
    @Override
    public void eliminar(String id) {
        try {
            int idFarmaco = Integer.parseInt(id); // Convierte el ID de String a entero
            List<Farmaco> lista = cargarTodos(); // Carga la lista actual
            lista.removeIf(f -> f.getIdFarmaco() == idFarmaco); // Elimina el fármaco con ese ID
            guardarTodos(lista); // Guarda la lista modificada
        } catch (IOException | NumberFormatException ex) {
            ex.printStackTrace(); // Captura errores de conversión o E/S
        }
    }

    /**
     * Obtiene un fármaco a partir de su ID.
     * 
     * @param id El ID del fármaco que se desea obtener (en formato String).
     * @return El objeto Farmaco correspondiente, o null si no se encuentra o hay error.
     */
    @Override
    public Farmaco obtener(String id) {
        try {
            int idFarmaco = Integer.parseInt(id); // Convierte el ID a entero
            for (Farmaco f : cargarTodos()) { // Recorre todos los fármacos
                if (f.getIdFarmaco() == idFarmaco) {
                    return f; // Retorna el fármaco si encuentra coincidencia
                }
            }
        } catch (IOException | NumberFormatException ex) {
            ex.printStackTrace(); // Maneja errores en la lectura o conversión
        }
        return null; // Retorna null si no se encuentra el fármaco o hay error
    }

    /**
     * Carga todos los fármacos desde el archivo JSON.
     * 
     * @return Una lista con todos los objetos Farmaco almacenados.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    @Override
    public List<Farmaco> cargarTodos() throws IOException {
        try (FileReader reader = new FileReader(RUTA)) { // Abre el archivo para lectura
            List<Farmaco> lista = gson.fromJson(
                reader,
                new TypeToken<List<Farmaco>>() {}.getType() // Especifica el tipo de lista a deserializar
            );
            return lista != null ? lista : new ArrayList<>(); // Retorna lista vacía si no hay datos
        }
    }

    /**
     * Guarda la lista de fármacos completa en el archivo JSON.
     * 
     * @param lista La lista de objetos Farmaco a guardar.
     * @throws IOException Si ocurre un error al escribir en el archivo.
     */
    @Override
    public void guardarTodos(List<Farmaco> lista) throws IOException {
        try (FileWriter writer = new FileWriter(RUTA)) { // Abre el archivo para escritura
            gson.toJson(lista, writer); // Escribe la lista en formato JSON
        }
    }
}
