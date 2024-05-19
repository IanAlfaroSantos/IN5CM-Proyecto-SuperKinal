/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ianalfaro.dto;

import org.ianalfaro.model.TicketSoporte;

/**
 *
 * @author informatica
 */
public class TicketSoporteDTO {
    private static TicketSoporteDTO instance;
    private TicketSoporte ticketSoporte;
    
    private TicketSoporteDTO(){
    
    }
    
    public static TicketSoporteDTO getTicketSoporteDTO(){
        if(instance == null){
            instance = new TicketSoporteDTO();
        }
        return instance;
    }

    public TicketSoporte getTicketSoporte() {
        return ticketSoporte;
    }

    public void setTicketSoporte(TicketSoporte ticketSoporte) {
        this.ticketSoporte = ticketSoporte;
    }
}
