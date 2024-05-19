/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ianalfaro.model;

/**
 *
 * @author informatica
 */
public class DetalleFactura {
    private int detalleFacturaId;
    private int facturaId;
    private int productoId;
    
    public DetalleFactura(){
    
    }

    public DetalleFactura(int detalleFacturaId, int facturaId, int productoId) {
        this.detalleFacturaId = detalleFacturaId;
        this.facturaId = facturaId;
        this.productoId = productoId;
    }

    public int getDetalleFacturaId() {
        return detalleFacturaId;
    }

    public int getFacturaId() {
        return facturaId;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setDetalleFacturaId(int detalleFacturaId) {
        this.detalleFacturaId = detalleFacturaId;
    }

    public void setFacturaId(int facturaId) {
        this.facturaId = facturaId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    @Override
    public String toString() {
        return "DetalleFacturas{" + "detalleFacturaId=" + detalleFacturaId + ", facturaId=" + facturaId + ", productoId=" + productoId + '}';
    }
}
