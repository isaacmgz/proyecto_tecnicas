package Model;

import java.time.LocalDate;
import java.util.ArrayList;
import com.itextpdf.text.*;

/**
 *
 * @author isaacmgz
 */
public class Factura {
    
    private String idFactura;
    private LocalDate fechaEmision;
    private LocalDate fechaVencimiento;
    private Empresa empresa;
    private Cliente cliente;
    private ArrayList<Farmaco> farmacos;
    private Image logo;

    public Factura() {
    }

    public Factura(String idFactura, LocalDate fechaEmision, LocalDate fechaVencimiento, Empresa empresa, Cliente cliente, ArrayList<Farmaco> farmacos, Image logo) {
        this.idFactura = idFactura;
        this.fechaEmision = fechaEmision;
        this.fechaVencimiento = fechaVencimiento;
        this.empresa = empresa;
        this.cliente = cliente;
        this.farmacos = farmacos;
        this.logo = logo;
    }

    public String getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(String idFactura) {
        this.idFactura = idFactura;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ArrayList<Farmaco> getFarmacos() {
        return farmacos;
    }

    public void setFarmacos(ArrayList<Farmaco> farmacos) {
        this.farmacos = farmacos;
    }

    public Image getLogo() {
        return logo;
    }

    public void setLogo(Image logo) {
        this.logo = logo;
    }

    @Override
    public String toString() {
        return "Factura{" + "idFactura=" + idFactura + ", fechaEmision=" + fechaEmision + ", fechaVencimiento=" + fechaVencimiento + ", empresa=" + empresa + ", cliente=" + cliente + ", farmacos=" + farmacos + ", logo=" + logo + '}';
    }
}
