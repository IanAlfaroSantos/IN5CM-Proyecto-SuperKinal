/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ianalfaro.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.ianalfaro.dao.Conexion;
import org.ianalfaro.dto.CargoDTO;
import org.ianalfaro.model.Cargo;
import org.ianalfaro.system.Main;
import org.ianalfaro.utils.SuperKinalAlert;

/**
 *
 * @author informatica
 */
public class FormCargoController implements Initializable {
    private Main stage;
    private int op;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;

    @FXML
    TextField tfCargoId, tfNombreCargo;
    
    @FXML
    TextArea taDescripcionCargo;
    
    @FXML
    Button btnGuardar, btnCancelar;
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnGuardar){
            if(op == 1){
                if(!tfNombreCargo.getText().equals("") && !taDescripcionCargo.getText().equals("")){
                    agregarCargo();
                    SuperKinalAlert.getInstance().mostrarAlertaInformacion(400);
                    stage.menuCargosView();
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertaInformacion(600);
                    if(tfNombreCargo.getText().equals("")){
                        tfNombreCargo.requestFocus(); // Para enfocar un textField de forma dinámica
                    }else if(taDescripcionCargo.getText().equals("")){
                        taDescripcionCargo.requestFocus(); // Para enfocar un textField de forma dinámica
                    }
                }
            }else if(op == 2){
                if(!tfNombreCargo.getText().equals("") && !taDescripcionCargo.getText().equals("")){
                    if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(505).get() == ButtonType.OK){
                        editarCargo();
                        CargoDTO.getCargoDTO().setCargo(null);
                        SuperKinalAlert.getInstance().mostrarAlertaInformacion(500);
                        stage.menuCargosView();
                    }else{
                        stage.menuCargosView();
                    }
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertaInformacion(600);
                    if(tfNombreCargo.getText().equals("")){
                        tfNombreCargo.requestFocus(); // Para enfocar un textField de forma dinámica
                    }else if(taDescripcionCargo.getText().equals("")){
                        taDescripcionCargo.requestFocus(); // Para enfocar un textField de forma dinámica
                    }
                }
            }
        }else if(event.getSource() == btnCancelar){
            stage.menuCargosView();
            CargoDTO.getCargoDTO().setCargo(null);
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle resources) {
        if(CargoDTO.getCargoDTO().getCargo() != null){
            cargarDatos(CargoDTO.getCargoDTO().getCargo());
        }
    }
    
    public void cargarDatos(Cargo cargo){
        tfCargoId.setText(Integer.toString(cargo.getCargoId()));
        tfNombreCargo.setText(cargo.getNombreCargo());
        taDescripcionCargo.setText(cargo.getDescripcionCargo());
    }
    
    public void agregarCargo(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_AgregarCargos(?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNombreCargo.getText());
            statement.setString(2, taDescripcionCargo.getText());
            statement.execute();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null){
                    statement.close();
                }else if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }
    
    public void editarCargo(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_EditarCargos(?, ?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfCargoId.getText()));
            statement.setString(2, tfNombreCargo.getText());
            statement.setString(3, taDescripcionCargo.getText());
            statement.execute();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null){
                    statement.close();
                }
                if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }
    
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }

    public void setOp(int op) {
        this.op = op;
    }
}
