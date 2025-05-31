package Controller;

import java.util.List;
import java.io.IOException;



/**
 * Interfaz genérica para la gestión de listas de objetos de tipo T.
 * Define las operaciones básicas que deben implementar las clases
 * encargadas de manejar colecciones con persistencia.
 * 
 * @param <T> Tipo de objeto que se va a manejar (por ejemplo: Farmaco, Cliente, etc.)
 * 
 * 
 */
public interface ILista<T> {
    
    /**
     * Agrega un nuevo objeto a la colección.
     * 
     * @param obj Objeto de tipo T que se desea agregar.
     */
    void agregar(T obj);
    
    /**
     * Elimina un objeto de la colección, dado su identificador.
     * 
     * @param id Identificador del objeto que se desea eliminar.
     */
    void eliminar(String id);
    
    /**
     * Obtiene un objeto de la colección según su identificador.
     * 
     * @param id Identificador del objeto que se desea obtener.
     * @return El objeto correspondiente, o null si no se encuentra.
     */
    T obtener(String id);
    
    /**
     * Carga todos los objetos de la colección desde el medio de persistencia.
     * 
     * @return Lista de objetos cargados.
     * @throws IOException Si ocurre un error de entrada/salida durante la carga.
     */
    List<T> cargarTodos() throws IOException;
    
    /**
     * Guarda todos los objetos de la colección en el medio de persistencia.
     * 
     * @param lista Lista de objetos que se desea guardar.
     * @throws IOException Si ocurre un error de entrada/salida durante el guardado.
     */
    void guardarTodos(List<T> lista) throws IOException;
}
