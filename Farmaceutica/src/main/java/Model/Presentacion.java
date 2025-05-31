package Model;

import Controller.IVisitable;
import Controller.IVisitor;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author isaacmgz
 */
public class Presentacion implements IVisitable {
    private String tipo;
    private List<String> dosificaciones;
    private double precioPorUnidad;
    private String unidad;

    public Presentacion() {
    }
    
    public Presentacion(String tipo, List<String> dosificaciones){
        this.tipo = tipo;
        this.dosificaciones = dosificaciones;
    }

    public Presentacion(String tipo, List<String> dosificaciones, double precioPorUnidad, String unidad) {
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

    public List<String> getDosificaciones() {
        return dosificaciones;
    }

    public void setDosificaciones(List<String> dosificaciones) {
        this.dosificaciones = dosificaciones;
    }

    @Override
    public String toString() {
        return "Presentacion{" + "tipo=" + tipo + ", dosificaciones=" + dosificaciones + ", precioUnidad=" + precioPorUnidad + ", unidad=" + unidad + '}';
    }

    @Override
    public void accept(IVisitor v) {
        v.visitPresentacion(this);
    }
}
