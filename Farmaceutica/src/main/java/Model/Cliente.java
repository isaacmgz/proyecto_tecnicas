/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author isaacmgz
 */
public class Cliente {
    
    private String nombreCliente;
    private String idCliente;
    private String emailCliente;

    public Cliente() {
    }
    
    public Cliente(String nombreCliente){
        this.nombreCliente = nombreCliente;
    }

    public Cliente(String nombreCliente, String idCliente, String emailCliente) {
        this.nombreCliente = nombreCliente;
        this.idCliente = idCliente;
        this.emailCliente = emailCliente;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    @Override
    public String toString() {
        return "Cliente{" + "nombreCliente=" + nombreCliente + ", idCliente=" + idCliente + ", emailCliente=" + emailCliente + '}';
    }
}
