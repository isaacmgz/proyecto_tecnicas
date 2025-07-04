package Model;
import Controller.IVisitable;
import Controller.IVisitor;
import java.util.ArrayList;

/**
 *
 * @author isaacmgz
 */
public class Farmaco implements IVisitable{
    
    private String nombre;
    private double valorFarmaco;
    private int idFarmaco;
    private ArrayList<Presentacion> presentaciones;
    private ArrayList<String> dosificaciones;

    public Farmaco() {
    }
    
    public Farmaco(String nombreFarmaco) {
        this.nombre = nombreFarmaco;
    }

    public Farmaco(String nombreFarmaco, double valorFarmaco, int idFarmaco, ArrayList<Presentacion> presentaciones, ArrayList<String> dosificaciones) {
        this.nombre = nombreFarmaco;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombreFarmaco) {
        this.nombre = nombreFarmaco;
    }

    public double getValorFarmaco() {
        return valorFarmaco;
    }

    public void setValorFarmaco(double valorFarmaco) {
        this.valorFarmaco = valorFarmaco;
    }

    public int getIdFarmaco() {
        return idFarmaco;
    }

    public void setIdFarmaco(int idFarmaco) {
        this.idFarmaco = idFarmaco;
    }

    public ArrayList<Presentacion> getPresentaciones() {
        return presentaciones;
    }

    public void setPresentaciones(ArrayList<Presentacion> presentaciones) {
        this.presentaciones = presentaciones;
    }

    @Override
    public String toString() {
        return "Farmaco{" + "nombre=" + nombre + ", valorFarmaco=" + valorFarmaco + ", idFarmaco=" + idFarmaco + ", presentaciones=" + presentaciones + ", dosificaciones=" + dosificaciones + '}';
    }

    @Override
    public void accept(IVisitor v) {
        v.visitFarmaco(this);
    }
}
