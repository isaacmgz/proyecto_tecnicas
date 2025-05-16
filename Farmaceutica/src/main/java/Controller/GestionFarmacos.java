/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Farmaco;
import Model.Presentacion;
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
public class GestionFarmacos {

    private static final String RUTA = "src/main/java/ArchivosPersistencia/farmacos.json";
    private static Gson gson = new Gson();

    public static List<Farmaco> cargarTodos() throws IOException {
        try (FileReader reader = new FileReader(RUTA)) {
            return gson.fromJson(
                    reader,
                    new TypeToken<List<Farmaco>>() {
                    }.getType()
            );
        }
    }

    // si necesitas guardarlos tras cambios
    public void guardarTodos(List<Farmaco> lista) throws IOException {
        try (FileWriter writer = new FileWriter(RUTA)) {
            gson.toJson(lista, writer);
        }
    }
    
    public static void ImportarFarmaco() {

        try {
            // lee todos los fármacos
            List<Farmaco> lista = cargarTodos();
            // busca uno por id
            int idBuscado = 3;
            for (Farmaco f : lista) {
                if (f.getIdFarmaco()== idBuscado) {
                    System.out.println("Nombre: " + f.getNombre());
                    for (Presentacion p : f.getPresentaciones()) {
                        System.out.println(
                                "  - " + p.getTipo()
                                + " (dosificaciones: " + p.getDosificaciones()
                                + ", precio unidad: " + p.getPrecioPorUnidad()
                                + " " + p.getUnidad() + ")"
                        );
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
