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
public class DetalleCompra {
    private int detalleCompraId;
    private int cantidadCompra;
    private int productoId;
    private String producto;
    private int compraId;
    private String compra;
   
    public DetalleCompra(){
   
    }

    public DetalleCompra(int detalleCompraId, int cantidadCompra, String producto, String compra) {
       this.detalleCompraId = detalleCompraId;
       this.cantidadCompra = cantidadCompra;
       this.producto = producto;
       this.compra = compra;
   }

    public int getDetalleCompraId() {
        return detalleCompraId;
    }

    public int getCantidadCompra() {
        return cantidadCompra;
    }

    public int getProductoId() {
        return productoId;
    }

    public String getProducto() {
        return producto;
    }

    public int getCompraId() {
        return compraId;
    }

    public String getCompra() {
        return compra;
    }

    public void setDetalleCompraId(int detalleCompraId) {
        this.detalleCompraId = detalleCompraId;
    }

    public void setCantidadCompra(int cantidadCompra) {
        this.cantidadCompra = cantidadCompra;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public void setCompraId(int compraId) {
        this.compraId = compraId;
    }

    public void setCompra(String compra) {
        this.compra = compra;
    }

    @Override
    public String toString() {
        return "DetalleCompras{" + "detalleCompraId=" + detalleCompraId + ", cantidadCompra=" + cantidadCompra + ", productoId=" + productoId + ", compraId=" + compraId + '}';
    }
}
