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
    private String factura;
    private int productoId;
    private String producto;
    
    public DetalleFactura(){
    
    }

    public DetalleFactura(int detalleFacturaId, String factura, String producto) {
        this.detalleFacturaId = detalleFacturaId;
        this.factura = factura;
        this.producto = producto;
    }

    public int getDetalleFacturaId() {
        return detalleFacturaId;
    }

    public int getFacturaId() {
        return facturaId;
    }

    public String getFactura() {
        return factura;
    }
    
    public int getProductoId() {
        return productoId;
    }

    public String getProducto() {
        return producto;
    }
    
    public void setDetalleFacturaId(int detalleFacturaId) {
        this.detalleFacturaId = detalleFacturaId;
    }

    public void setFacturaId(int facturaId) {
        this.facturaId = facturaId;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }
    
    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    @Override
    public String toString() {
        return "Id: " + detalleFacturaId + " | " + factura + " " + producto;
    }
}
