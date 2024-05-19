/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ianalfaro.model;

import java.sql.Date;

/**
 *
 * @author informatica
 */
public class Promocion {
    private int promocionId;
    private double precioPromocion;
    private String descripcionPromocion;
    private String fechaInicio;
    private String fechaFinalizacion;
    private int productoId;
    private String producto;
    
    public Promocion(){
    
    }

    public Promocion(int promocionId, double precioPromocion, String descripcionPromocion, String fechaInicio, String fechaFinalizacion, String producto) {
        this.promocionId = promocionId;
        this.precioPromocion = precioPromocion;
        this.descripcionPromocion = descripcionPromocion;
        this.fechaInicio = fechaInicio;
        this.fechaFinalizacion = fechaFinalizacion;
        this.producto = producto;
    }

    public int getPromocionId() {
        return promocionId;
    }

    public double getPrecioPromocion() {
        return precioPromocion;
    }

    public String getDescripcionPromocion() {
        return descripcionPromocion;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public String getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public int getProductoId() {
        return productoId;
    }

    public String getProducto() {
        return producto;
    }

    public void setPromocionId(int promocionId) {
        this.promocionId = promocionId;
    }

    public void setPrecioPromocion(double precioPromocion) {
        this.precioPromocion = precioPromocion;
    }

    public void setDescripcionPromocion(String descripcionPromocion) {
        this.descripcionPromocion = descripcionPromocion;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFinalizacion(String fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    @Override
    public String toString() {
        return "Id: " + promocionId + " | " + producto;
    }
}
