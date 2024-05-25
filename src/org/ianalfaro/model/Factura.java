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
public class Factura {
    private int facturaId;
    private String fecha;
    private String hora;
    private int clienteId;
    private String cliente;
    private int empleadoId;
    private String empleado;
    private double total;
    
    public Factura(){
    
    }

    public Factura(int facturaId, String fecha, String hora, String cliente, String empleado, double total) {
        this.facturaId = facturaId;
        this.fecha = fecha;
        this.hora = hora;
        this.cliente = cliente;
        this.empleado = empleado;
        this.total = total;
    }

    public int getFacturaId() {
        return facturaId;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public int getClienteId() {
        return clienteId;
    }

    public String getCliente() {
        return cliente;
    }

    public int getEmpleadoId() {
        return empleadoId;
    }

    public String getEmpleado() {
        return empleado;
    }

    public double getTotal() {
        return total;
    }

    public void setFacturaId(int facturaId) {
        this.facturaId = facturaId;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public void setEmpleadoId(int empleadoId) {
        this.empleadoId = empleadoId;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Id: " + facturaId + " | " + fecha + " " + total;
    }
}
