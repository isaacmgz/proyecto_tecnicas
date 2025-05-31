package Model;

import Controller.IVisitable;
import Controller.IVisitor;
import java.time.LocalDate;

/**
 *
 * @author isaacmgz
 */
public class Venta implements IVisitable{

    private String idVenta;
    private LocalDate fechaVenta;
    private Cliente cliente;
    private Farmaco farmaco;
    private Presentacion presentacion;
    private String dosificacion;
    private double valorTotal;
    private int unidadesVendidas;

    public Venta() {
    }

    public Venta(String idVenta, LocalDate fechaVenta, Cliente cliente, Farmaco farmaco, Presentacion presentacion, String dosificacion, int unidadesVendidas, double valorTotal) {
        this.idVenta = idVenta;
        this.fechaVenta = fechaVenta;
        this.cliente = cliente;
        this.farmaco = farmaco;
        this.presentacion = presentacion;
        this.dosificacion = dosificacion;
        this.valorTotal = valorTotal;
        this.unidadesVendidas = unidadesVendidas;
    }

    public String getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(String idVenta) {
        this.idVenta = idVenta;
    }

    public LocalDate getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(LocalDate fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Farmaco getFarmaco() {
        return farmaco;
    }

    public void setFarmaco(Farmaco farmaco) {
        this.farmaco = farmaco;
    }

    public Presentacion getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(Presentacion presentacion) {
        this.presentacion = presentacion;
    }

    public String getDosificacion() {
        return dosificacion;
    }

    public void setDosificacion(String dosificacion) {
        this.dosificacion = dosificacion;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public int getUnidadesVendidas() {
        return unidadesVendidas;
    }

    public void setUnidadesVendidas(int unidadesVendidas) {
        this.unidadesVendidas = unidadesVendidas;
    }

    @Override
    public void accept(IVisitor v) {
        v.visitVenta(this);
    }

    
}
