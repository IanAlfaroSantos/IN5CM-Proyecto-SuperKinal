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
import org.ianalfaro.dto.CategoriaProductoDTO;
import org.ianalfaro.model.CategoriaProducto;
import org.ianalfaro.system.Main;
import org.ianalfaro.utils.SuperKinalAlert;

/**
 *
 * @author informatica
 */
public class FormCategoriaProductoController implements Initializable {
    private Main stage;
    private int op;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;

    @FXML
    TextField tfCategoriaProductoId, tfNombreCategoria;
    
    @FXML
    TextArea taDescripcionCategoria;
    
    @FXML
    Button btnGuardar, btnCancelar;
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnGuardar){
            if(op == 1){
                if(!tfNombreCategoria.getText().equals("") && !taDescripcionCategoria.getText().equals("")){
                    agregarCategoriaProducto();
                    SuperKinalAlert.getInstance().mostrarAlertaInformacion(400);
                    stage.menuCategoriaProductoView();
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertaInformacion(600);
                    if(tfNombreCategoria.getText().equals("")){
                        tfNombreCategoria.requestFocus(); // Para enfocar un textField de forma dinámica
                    }else if(taDescripcionCategoria.getText().equals("")){
                        taDescripcionCategoria.requestFocus(); // Para enfocar un textField de forma dinámica
                    }
                }
            }else if(op == 2){
                if(!tfNombreCategoria.getText().equals("") && !taDescripcionCategoria.getText().equals("")){
                    if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(505).get() == ButtonType.OK){
                        editarCategoriaProducto();
                        CategoriaProductoDTO.getCategoriaProductoDTO().setCategoriaProducto(null);
                        SuperKinalAlert.getInstance().mostrarAlertaInformacion(500);
                        stage.menuCategoriaProductoView();
                    }else{
                        stage.menuCategoriaProductoView();
                    }
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertaInformacion(600);
                    if(tfNombreCategoria.getText().equals("")){
                        tfNombreCategoria.requestFocus(); // Para enfocar un textField de forma dinámica
                    }else if(taDescripcionCategoria.getText().equals("")){
                        taDescripcionCategoria.requestFocus(); // Para enfocar un textField de forma dinámica
                    }
                }
            }
        }else if(event.getSource() == btnCancelar){
            stage.menuCategoriaProductoView();
            CategoriaProductoDTO.getCategoriaProductoDTO().setCategoriaProducto(null);
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle resources) {
        if(CategoriaProductoDTO.getCategoriaProductoDTO().getCategoriaProducto() != null){
            cargarDatos(CategoriaProductoDTO.getCategoriaProductoDTO().getCategoriaProducto());
        }
    }
    
    public void cargarDatos(CategoriaProducto categoriaProducto){
        tfCategoriaProductoId.setText(Integer.toString(categoriaProducto.getCategoriaProductoId()));
        tfNombreCategoria.setText(categoriaProducto.getNombreCategoria());
        taDescripcionCategoria.setText(categoriaProducto.getDescripcionCategoria());
    }
    
    public void agregarCategoriaProducto(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_AgregarCategoriaProductos(?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNombreCategoria.getText());
            statement.setString(2, taDescripcionCategoria.getText());
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
        op = 1;
    }
    
    public void editarCategoriaProducto(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_EditarCategoriaProductos(?, ?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfCategoriaProductoId.getText()));
            statement.setString(2, tfNombreCategoria.getText());
            statement.setString(3, taDescripcionCategoria.getText());
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
