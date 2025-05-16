/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.ArrayList;

/**
 *
 * @author isaacmgz
 */
public class Presentacion {
    private String tipo;
    private ArrayList<String> dosificaciones;
    private double precioPorUnidad;
    private String unidad;

    public Presentacion() {
    }

    public Presentacion(String tipo, ArrayList<String> dosificaciones, double precioPorUnidad, String unidad) {
        this.tipo = tipo;
        this.dosificaciones = dosificaciones;
        this.precioPorUnidad = precioPorUnidad;
        this.unidad = unidad;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getPrecioPorUnidad() {
        return precioPorUnidad;
    }

    public void setPrecioUnidad(double precioUnidad) {
        this.precioPorUnidad = precioUnidad;
    }

    public ArrayList<String> getDosificaciones() {
        return dosificaciones;
    }

    public void setDosificaciones(ArrayList<String> dosificaciones) {
        this.dosificaciones = dosificaciones;
    }

    @Override
    public String toString() {
        return "Presentacion{" + "tipo=" + tipo + ", dosificaciones=" + dosificaciones + ", precioUnidad=" + precioPorUnidad + ", unidad=" + unidad + '}';
    }
}
