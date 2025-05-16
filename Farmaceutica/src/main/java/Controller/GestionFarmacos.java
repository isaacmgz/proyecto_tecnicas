/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Farmaco;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

/**
 *
 * @author isaacmgz
 */
public class GestionFarmacos {

    private static final String RUTA = "src/main/java/ArchivosPersistencia/farmacos.json";
    private Gson gson = new Gson();

    public List<Farmaco> leerTodos() throws Exception {
        return gson.fromJson(
                new FileReader(RUTA),
                new TypeToken<List<Farmaco>>() {
                }.getType()
        );
    }

    public void guardarTodos(List<Farmaco> lista) throws Exception {
        try (FileWriter writer = new FileWriter(RUTA)) {
            gson.toJson(lista, writer);
        }
    }
}
