    package Controller;

import Model.Cliente;
import Model.Factura;
import Model.Farmaco;
import Model.Presentacion;
import Model.Venta;

/**
 *
 * @author isaacmgz
 */

public interface IVisitor {

    void visitCliente(Cliente c);

    void visitFarmaco(Farmaco f);

    void visitPresentacion(Presentacion p);

    void visitVenta(Venta v);

    void visitFactura(Factura fac);
}
