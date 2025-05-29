package Controller;

import java.util.List;
import java.io.IOException;

/**
 *
 * @author isaacmgz
 */

public interface ILista<T> {
    
    void agregar(T obj);
    
    void eliminar(String id);
    
    T obtener(String id);
    
    List<T> cargarTodos() throws IOException;
    
    void guardarTodos(List<T> lista) throws IOException;
}