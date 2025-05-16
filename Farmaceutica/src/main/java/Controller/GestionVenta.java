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

/**
 *
 * @author isaacmgz
 */
public class GestionVenta {
    
    public static void ImportarFarmacos() {

        GestionFarmacos repoFarmaco = new GestionFarmacos();
        try {
            // lee todos los fármacos
            List<Farmaco> lista = repoFarmaco.cargarTodos();
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
