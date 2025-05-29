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
 *
 * @author isaacmgz
 */


public class GestionFarmacos implements ILista<Farmaco> {

    private static final String RUTA = "src/main/java/ArchivosPersistencia/farmacos.json";
    private static final Gson gson = new Gson();

    @Override
    public void agregar(Farmaco nuevo) {
        try {
            List<Farmaco> lista = cargarTodos();
            lista.add(nuevo);
            guardarTodos(lista);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void eliminar(String id) {
        try {
            int idFarmaco = Integer.parseInt(id);
            List<Farmaco> lista = cargarTodos();
            lista.removeIf(f -> f.getIdFarmaco() == idFarmaco);
            guardarTodos(lista);
        } catch (IOException | NumberFormatException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Farmaco obtener(String id) {
        try {
            int idFarmaco = Integer.parseInt(id);
            for (Farmaco f : cargarTodos()) {
                if (f.getIdFarmaco() == idFarmaco) {
                    return f;
                }
            }
        } catch (IOException | NumberFormatException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Farmaco> cargarTodos() throws IOException {
        try (FileReader reader = new FileReader(RUTA)) {
            List<Farmaco> lista = gson.fromJson(
                reader,
                new TypeToken<List<Farmaco>>() {}.getType()
            );
            return lista != null ? lista : new ArrayList<>();
        }
    }

    @Override
    public void guardarTodos(List<Farmaco> lista) throws IOException {
        try (FileWriter writer = new FileWriter(RUTA)) {
            gson.toJson(lista, writer);
        }
    }
}