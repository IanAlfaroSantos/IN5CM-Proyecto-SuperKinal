/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ianalfaro.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import org.ianalfaro.dao.Conexion;
import org.ianalfaro.dto.ProductoDTO;
import org.ianalfaro.model.Producto;
import org.ianalfaro.system.Main;
import org.ianalfaro.utils.SuperKinalAlert;

/**
 *
 * @author informatica
 */
public class FormProductoController implements Initializable {
    private Main stage;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    private List<File> files = null;
    
    @FXML
    Button btnCargarImagen, btnBuscar, btnBack;
    
    @FXML
    TextField tfProductoId;
    
    @FXML
    Label lblNombreProducto;
    
    @FXML
    ImageView imgCargar, imgMostrar;
    
    @FXML
    public void handleButtonAction(ActionEvent event){
       try{
       if(event.getSource() == btnBack){
            stage.menuProductoView();
        }else if(event.getSource() == btnCargarImagen){
            if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(505).get() == ButtonType.OK){
                editarImagen();
                ProductoDTO.getProductoDTO().setProducto(null);
                SuperKinalAlert.getInstance().mostrarAlertaInformacion(500);
                stage.formProductoView();
            }else{
                stage.menuEmpleadoView();
            }
        }else if(event.getSource() == btnBuscar){
            Producto producto = buscarProducto();
            if(producto != null){
                lblNombreProducto.setText(producto.getNombreProducto());
                InputStream file = producto.getImagenProducto().getBinaryStream();
                Image image = new Image(file);
                imgMostrar.setImage(image);
            }
        }
       }catch(Exception e){
           e.printStackTrace();
       } 
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle resources) {
        //TODO
    }
    
    @FXML
    public void handleOnDrag(DragEvent event){
        if(event.getDragboard().hasFiles()){
            event.acceptTransferModes(TransferMode.ANY);
        }
    }
    
    @FXML
    public void handleOnDrop(DragEvent event){
        try{
            files = event.getDragboard().getFiles();
            FileInputStream file = new FileInputStream(files.get(0));
            Image image = new Image(file);
            imgCargar.setImage(image);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void editarImagen(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_EditarImagen(?)";
            statement = conexion.prepareStatement(sql);
            FileInputStream img = new FileInputStream(files.get(0));
            statement.setBinaryStream(1, img);
            statement.execute();
        }catch(Exception e){
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
    
    public Producto buscarProducto(){
    Producto producto = null;
    
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_BuscarProductos(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfProductoId.getText()));
            resultSet = statement.executeQuery();
            if(resultSet.next()){
                int productoId = resultSet.getInt("productoId");
                String nombreProducto = resultSet.getString("nombreProducto");
                Blob imagenProducto = resultSet.getBlob("imagenProducto");
                
                producto = new Producto(productoId, nombreProducto, imagenProducto);
            }
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(resultSet != null){
                    resultSet.close();
                }
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
        return producto;
    }
    
    public void setStage(Main stage) {
        this.stage = stage;
    }

    public Main getStage() {
        return stage;
    }
}