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
public class TicketSoporte {
    private int ticketSoporteId;
    private String descripcionTicket;
    private String estatus;
    private int clienteId;
    private String cliente;
    private int facturaId;
    private String factura;
    
    public TicketSoporte(){
    
    }

    public TicketSoporte(int ticketSoporteId, String descripcionTicket, String estatus, String cliente, String factura) {
        this.ticketSoporteId = ticketSoporteId;
        this.descripcionTicket = descripcionTicket;
        this.estatus = estatus;
        this.cliente = cliente;
        this.factura = factura;
    }

    public int getTicketSoporteId() {
        return ticketSoporteId;
    }

    public String getDescripcionTicket() {
        return descripcionTicket;
    }

    public String getEstatus() {
        return estatus;
    }

    public int getClienteId() {
        return clienteId;
    }

    public String getCliente() {
        return cliente;
    }

    public int getFacturaId() {
        return facturaId;
    }

    public String getFactura() {
        return factura;
    }

    public void setTicketSoporteId(int ticketSoporteId) {
        this.ticketSoporteId = ticketSoporteId;
    }

    public void setDescripcionTicket(String descripcionTicket) {
        this.descripcionTicket = descripcionTicket;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public void setFacturaId(int facturaId) {
        this.facturaId = facturaId;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    @Override
    public String toString() {
        return "TicketSoporte{" + "ticketSoporteId=" + ticketSoporteId + ", descripcionTicket=" + descripcionTicket + ", estatus=" + estatus + ", clienteId=" + clienteId + ", cliente=" + cliente + ", facturaId=" + facturaId + ", factura=" + factura + '}';
    }
}
