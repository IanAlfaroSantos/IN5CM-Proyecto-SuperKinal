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
public class Cargo {
    private int cargoId;
    private String nombreCargo;
    private String descripcionCargo;
    
    public Cargo(){
    
    }

    public Cargo(int cargoId, String nombreCargo, String descripcionCargo) {
        this.cargoId = cargoId;
        this.nombreCargo = nombreCargo;
        this.descripcionCargo = descripcionCargo;
    }

    public int getCargoId() {
        return cargoId;
    }

    public String getNombreCargo() {
        return nombreCargo;
    }

    public String getDescripcionCargo() {
        return descripcionCargo;
    }

    public void setCargoId(int cargoId) {
        this.cargoId = cargoId;
    }

    public void setNombreCargo(String nombreCargo) {
        this.nombreCargo = nombreCargo;
    }

    public void setDescripcionCargo(String descripcionCargo) {
        this.descripcionCargo = descripcionCargo;
    }

    @Override
    public String toString() {
        return "Id: " + cargoId + " | " + nombreCargo;
    }
}
