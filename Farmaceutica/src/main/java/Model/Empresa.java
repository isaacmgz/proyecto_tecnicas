/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author isaacmgz
 */
public class Empresa {
    
    private String nombreEmpresa;
    private String NIT;
    private String Telefono;
    private String email;

    public Empresa() {
    }

    public Empresa(String nombreEmpresa, String NIT, String Telefono, String email) {
        this.nombreEmpresa = nombreEmpresa;
        this.NIT = NIT;
        this.Telefono = Telefono;
        this.email = email;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getNIT() {
        return NIT;
    }

    public void setNIT(String NIT) {
        this.NIT = NIT;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Empresa{" + "nombreEmpresa=" + nombreEmpresa + ", NIT=" + NIT + ", Telefono=" + Telefono + ", email=" + email + '}';
    }
}
