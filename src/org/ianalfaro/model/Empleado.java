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
public class Empleado {
    private int empleadoId;
    private String nombreEmpleado;
    private String apellidoEmpleado;
    private double sueldo;
    private String horaEntrada;
    private String horaSalida;
    private int cargoId;
    private String cargo;
    private int encargadoId;
    private String encargado;
    
    public Empleado(){
    
    }

    public Empleado(int empleadoId, String nombreEmpleado, String apellidoEmpleado, double sueldo, String horaEntrada, String horaSalida, String cargo, String encargado) {
        this.empleadoId = empleadoId;
        this.nombreEmpleado = nombreEmpleado;
        this.apellidoEmpleado = apellidoEmpleado;
        this.sueldo = sueldo;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.cargo = cargo;
        this.encargado = encargado;
    }

    public int getEmpleadoId() {
        return empleadoId;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public String getApellidoEmpleado() {
        return apellidoEmpleado;
    }

    public double getSueldo() {
        return sueldo;
    }

    public String getHoraEntrada() {
        return horaEntrada;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public int getCargoId() {
        return cargoId;
    }

    public int getEncargadoId() {
        return encargadoId;
    }

    public String getCargo() {
        return cargo;
    }

    public String getEncargado() {
        return encargado;
    }

    public void setEmpleadoId(int empleadoId) {
        this.empleadoId = empleadoId;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public void setApellidoEmpleado(String apellidoEmpleado) {
        this.apellidoEmpleado = apellidoEmpleado;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    public void setHoraEntrada(String horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    public void setCargoId(int cargoId) {
        this.cargoId = cargoId;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public void setEncargadoId(int encargadoId) {
        this.encargadoId = encargadoId;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }

    @Override
    public String toString() {
        return "Id: " + empleadoId + " | " + nombreEmpleado + " " + apellidoEmpleado;
    }

}
