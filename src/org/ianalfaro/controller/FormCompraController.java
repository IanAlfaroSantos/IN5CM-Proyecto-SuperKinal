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
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import org.ianalfaro.dao.Conexion;
import org.ianalfaro.dto.CompraDTO;
import org.ianalfaro.model.Compra;
import org.ianalfaro.system.Main;
import org.ianalfaro.utils.SuperKinalAlert;

/**
 *
 * @author informatica
 */
public class FormCompraController implements Initializable {
    private Main stage;
    private int op;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;

    @FXML
    TextField tfCompraId, tfFechaCompra;
    
    @FXML
    Button btnGuardar, btnCancelar;
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnGuardar){
            if(op == 1){
                if(!tfFechaCompra.getText().equals("")){
                    agregarCompra();
                    SuperKinalAlert.getInstance().mostrarAlertaInformacion(400);
                    stage.menuComprasView();
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertaInformacion(600);
                    if(tfFechaCompra.getText().equals("")){
                        tfFechaCompra.requestFocus(); // Para enfocar un textField de forma dinámica
                    }
                }
            }else if(op == 2){
                if(!tfFechaCompra.getText().equals("")){
                    if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(505).get() == ButtonType.OK){
                        editarCompra();
                        CompraDTO.getCompraDTO().setCompra(null);
                        SuperKinalAlert.getInstance().mostrarAlertaInformacion(500);
                        stage.menuComprasView();
                    }else{
                        stage.menuComprasView();
                    }
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertaInformacion(600);
                    if(tfFechaCompra.getText().equals("")){
                        tfFechaCompra.requestFocus(); // Para enfocar un textField de forma dinámica
                    }
                }
            }
        }else if(event.getSource() == btnCancelar){
            stage.menuComprasView();
            CompraDTO.getCompraDTO().setCompra(null);
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle resources) {
        if(CompraDTO.getCompraDTO().getCompra() != null){
            cargarDatos(CompraDTO.getCompraDTO().getCompra());
        }
    }
    
    public void cargarDatos(Compra compra){
        tfCompraId.setText(Integer.toString(compra.getCompraId()));
        SimpleDateFormat Date = new SimpleDateFormat("yyyy-MM-dd");
        String fecha = Date.format(compra.getFechaCompra());
        tfFechaCompra.setText(fecha);
    }
    
    public void agregarCompra(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_AgregarCompras(?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfFechaCompra.getText());
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
    
    public void editarCompra(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_EditarCompras(?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfCompraId.getText()));
            statement.setString(2, tfFechaCompra.getText());
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
