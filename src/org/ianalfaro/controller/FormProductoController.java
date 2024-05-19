/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ianalfaro.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import org.ianalfaro.system.Main;

/**
 *
 * @author informatica
 */
public class FormProductoController implements Initializable {
    private Main stage;
    public int op;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    
    @FXML
    Button btnCargar, btnBuscar, btnGuardar, btnCancelar;
    
    @FXML
    TextField tfNombreProducto, tfProductoId;
    
    @FXML
    Label lblNombre;
    
    @FXML
    ImageView imgCargar, imgMostrar;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle resources) {
        //TODO
    }
    
    @FXML
    public void handleOnDrag(DragEvent event){
    
    }
    
    @FXML
    public void handleOnDrop(DragEvent event){
    
    }
    
    public void setStage(Main stage) {
        this.stage = stage;
    }

    public Main getStage() {
        return stage;
    }
}