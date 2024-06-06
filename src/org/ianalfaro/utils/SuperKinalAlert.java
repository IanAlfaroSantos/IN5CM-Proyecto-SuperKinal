/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ianalfaro.utils;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author informatica
 */
public class SuperKinalAlert {
    private static SuperKinalAlert instance;
    
    private SuperKinalAlert(){
    
    }
    
    public static SuperKinalAlert getInstance(){
        if(instance == null){
            instance = new SuperKinalAlert();
        }
        return instance;
    }
    
    public void mostrarAlertaInformacion(int code){
        if(code == 400){ // Codigo 400 sirve para confirmación de resgistro
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmación Registro");
            alert.setHeaderText("Confirmación de Regitro");
            alert.setContentText("¡Registro realizado con éxito!");
            alert.showAndWait();
        }else if(code == 500){ // Codigo 500 sirve para edición de registro
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Edición Registro");
            alert.setHeaderText("Edición de Regitro");
            alert.setContentText("¡Edición realizado con éxito!");
            alert.showAndWait();
        }else if(code == 700){ // Codigo 700 sirve para eliminación de registro
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Eliminación Registro");
            alert.setHeaderText("Eliminación de Regitro");
            alert.setContentText("¡Eliminación realizado con éxito!");
            alert.showAndWait();
        }else if(code == 600){ // Codigo 600 sirve para alerta de campos pendientes
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos Pendientes");
            alert.setHeaderText("Campos Pendientes");
            alert.setContentText("¡Algunos campos necesarios aun estan vacios!");
            alert.showAndWait();
        }else if(code == 800){ // Codigo 600 sirve para alerta de usuario incorrecto
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Usuario Incorrecto");
            alert.setHeaderText("El usuario ingresado es incorrecto");
            alert.setContentText("Verificar el usuario");
            alert.showAndWait();
        }else if(code == 900){ // Codigo 600 sirve para alerta de contraseña incorrecta
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Contraseña Incorrecta");
            alert.setHeaderText("Contraseña incorrecta");
            alert.setContentText("Verificar la contraseña");
            alert.showAndWait();
        }
    }
    
    public Optional<ButtonType> mostrarAlertaConfirmacion(int code){
        Optional<ButtonType> action = null;
        
        if(code == 404){ // Codigo 404 sire para confirmar la eliminacion de un registro
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Eliminacion Registro");
            alert.setHeaderText("Eliminacion de Registro");
            alert.setContentText("¿Esta seguro de eliminar el registro?");
            action = alert.showAndWait();
        }else if(code == 505){ // Codigo 505 sirve para confirmar la edicion de registro
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Edición Registro");
            alert.setHeaderText("Edición de Registro");
            alert.setContentText("¿Esta seguro de editar el registro?");
            action = alert.showAndWait();
        }else if(code == 605){ // Codigo 605 sirve para confirmar el vacio del form
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Vaciar Form");
            alert.setHeaderText("Vacio del Form");
            alert.setContentText("¿Esta seguro de vaciar el form?");
            action = alert.showAndWait();
        }
        
        return action;
    }
    
    public void alertaSaludo(String usuario){ // Sirve para la bienvenida al usuario
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Bienvenido :D");
        alert.setHeaderText("Bienvenido " + usuario + " :D");
        alert.showAndWait();
    }
}
