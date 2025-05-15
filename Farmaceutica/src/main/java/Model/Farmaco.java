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
public class Farmaco {
    
    private String nombreFarmaco;
    private double valorFarmaco;
    private String idFarmaco;
    private ArrayList<String> presentaciones;
    private ArrayList<String> dosificaciones;

    public Farmaco() {
    }

    public Farmaco(String nombreFarmaco, double valorFarmaco, String idFarmaco, ArrayList<String> presentaciones, ArrayList<String> dosificaciones) {
        this.nombreFarmaco = nombreFarmaco;
        this.valorFarmaco = valorFarmaco;
        this.idFarmaco = idFarmaco;
        this.presentaciones = presentaciones;
        this.dosificaciones = dosificaciones;
    }

    public ArrayList<String> getDosificaciones() {
        return dosificaciones;
    }

    public void setDosificaciones(ArrayList<String> dosificaciones) {
        this.dosificaciones = dosificaciones;
    }

    public String getNombreFarmaco() {
        return nombreFarmaco;
    }

    public void setNombreFarmaco(String nombreFarmaco) {
        this.nombreFarmaco = nombreFarmaco;
    }

    public double getValorFarmaco() {
        return valorFarmaco;
    }

    public void setValorFarmaco(double valorFarmaco) {
        this.valorFarmaco = valorFarmaco;
    }

    public String getIdFarmaco() {
        return idFarmaco;
    }

    public void setIdFarmaco(String idFarmaco) {
        this.idFarmaco = idFarmaco;
    }

    public ArrayList<String> getPresentaciones() {
        return presentaciones;
    }

    public void setPresentaciones(ArrayList<String> presentaciones) {
        this.presentaciones = presentaciones;
    }

    @Override
    public String toString() {
        return "Farmaco{" + "nombreFarmaco=" + nombreFarmaco + ", valorFarmaco=" + valorFarmaco + ", idFarmaco=" + idFarmaco + ", presentaciones=" + presentaciones + ", dosificaciones=" + dosificaciones + '}';
    }
}
