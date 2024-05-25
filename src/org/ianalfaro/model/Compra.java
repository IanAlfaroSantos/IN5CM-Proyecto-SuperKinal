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
public class Compra {
    private int compraId;
    private Date fechaCompra;
    private double totalCompra;
    
    public Compra(){
    
    }

    public Compra(int compraId, Date fechaCompra, double totalCompra) {
        this.compraId = compraId;
        this.fechaCompra = fechaCompra;
    }

    public int getCompraId() {
        return compraId;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public double getTotalCompra() {
        return totalCompra;
    }

    public void setCompraId(int compraId) {
        this.compraId = compraId;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public void setTotalCompra(double totalCompra) {
        this.totalCompra = totalCompra;
    }

    @Override
    public String toString() {
        return "Id: " + compraId + " | " + fechaCompra + " " + totalCompra;
    }
}
