/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ianalfaro.controller;

import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.ianalfaro.dao.Conexion;
import org.ianalfaro.dto.ProductoDTO;
import org.ianalfaro.model.CategoriaProducto;
import org.ianalfaro.model.Distribuidor;
import org.ianalfaro.model.Producto;
import org.ianalfaro.report.GenerarReporteProducto;
import org.ianalfaro.system.Main;
import org.ianalfaro.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class MenuProductosController implements Initializable {
    private Main stage;
    public int op;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;

    @FXML
    TableView tblProductos;
    
    @FXML
    TableColumn colProductoId, colNombreProducto, colDescripcionProducto, colCantidadStock, colPrecioVentaU, colPrecioVentaM, colPrecioCompra, colImagen, colDistribuidor, colCategoriaProducto;
    
    @FXML
    Button btnBack, btnVaciar, btnGuardar, btnAgregarImagen, btnReporteProducto;
    
    @FXML
    TextField tfProductoId, tfNombreProducto, tfCantidadStock, tfPrecioVentaU, tfPrecioVentaM, tfPrecioCompra;
    
    @FXML
    TextArea taDescripcionProducto;
    
    @FXML
    ComboBox cmbDistribuidor, cmbCategoriaProducto;

    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnBack){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnGuardar){
            if(tfProductoId.getText().equals("")){
                agregarProducto();
                SuperKinalAlert.getInstance().mostrarAlertaInformacion(400);
                cargarDatos();
            }else{
                editarProducto();
                SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(505);
                SuperKinalAlert.getInstance().mostrarAlertaInformacion(500);
                cargarDatos();
            }
        }else if(event.getSource() == btnVaciar){
            if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(605).get() == ButtonType.OK){
                vaciarForm();
                cargarDatos();
            }
        }else if(event.getSource() == btnAgregarImagen){
            ProductoDTO.getProductoDTO().setProducto((Producto)tblProductos.getSelectionModel().getSelectedItem());
            stage.formProductoView();
        }else if(event.getSource() == btnReporteProducto){
            GenerarReporteProducto.getInstance().generarProducto();
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbDistribuidor.setItems(listarDistribuidor());
        cmbCategoriaProducto.setItems(listarCategoriaProducto());
    }
    
    public void cargarDatos(){
            tblProductos.setItems(listarProducto()); 
        
            colProductoId.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("productoId"));
            colNombreProducto.setCellValueFactory(new PropertyValueFactory<Producto, String>("nombreProducto"));
            colDescripcionProducto.setCellValueFactory(new PropertyValueFactory<Producto, String>("descripcionProducto"));
            colCantidadStock.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("cantidadStock"));
            colPrecioVentaU.setCellValueFactory(new PropertyValueFactory<Producto, Double>("precioVentaUnitario"));
            colPrecioVentaM.setCellValueFactory(new PropertyValueFactory<Producto, Double>("precioVentaMayor"));
            colPrecioCompra.setCellValueFactory(new PropertyValueFactory<Producto, Double>("precioCompra"));
            colImagen.setCellValueFactory(new PropertyValueFactory<Producto, Blob>("imagenProducto"));
            colDistribuidor.setCellValueFactory(new PropertyValueFactory<Producto, String>("distribuidor"));
            colCategoriaProducto.setCellValueFactory(new PropertyValueFactory<Producto, String>("categoriaProducto"));
        
    }
    
    public void vaciarForm(){
        tfProductoId.clear();
        tfNombreProducto.clear();
        taDescripcionProducto.clear();
        tfCantidadStock.clear();
        tfPrecioVentaU.clear();
        tfPrecioVentaM.clear();
        tfPrecioCompra.clear();
        cmbDistribuidor.getSelectionModel().clearSelection();
        cmbCategoriaProducto.getSelectionModel().clearSelection();
    }
    
    public void cargarForm(){
        Producto pd = (Producto)tblProductos.getSelectionModel().getSelectedItem();
        
        if(pd != null){
            tfProductoId.setText(Integer.toString(pd.getProductoId()));
            tfNombreProducto.setText(pd.getNombreProducto());
            taDescripcionProducto.setText(pd.getDescripcionProducto());
            tfCantidadStock.setText(Integer.toString(pd.getCantidadStock()));
            tfPrecioVentaU.setText(Double.toString(pd.getPrecioVentaUnitario()));
            tfPrecioVentaM.setText(Double.toString(pd.getPrecioVentaMayor()));
            tfPrecioCompra.setText(Double.toString(pd.getPrecioCompra()));
            cmbDistribuidor.getSelectionModel().select(obtenerIndexDistribuidor());
            cmbCategoriaProducto.getSelectionModel().select(obtenerIndexCategoriaProducto());
        }
    }
    
    public int obtenerIndexDistribuidor(){
        int index = 0;
        String distribuidorTbl = ((Producto)tblProductos.getSelectionModel().getSelectedItem()).getDistribuidor();
        for(int i = 0; i <= cmbDistribuidor.getItems().size(); i++){
            String distribuidorCmb = cmbDistribuidor.getItems().get(i).toString();
            
            if(distribuidorTbl.equals(distribuidorCmb)){
                index = i;
                break;
            }
        }
        return index;
    }
    
    public int obtenerIndexCategoriaProducto(){
        int index = 0;
        String categoriaProductoTbl = ((Producto)tblProductos.getSelectionModel().getSelectedItem()).getCategoriaProducto();
        for(int i = 0; i <= cmbCategoriaProducto.getItems().size(); i++){
            String categoriaProductoCmb = cmbCategoriaProducto.getItems().get(i).toString();
            
            if(categoriaProductoTbl.equals(categoriaProductoCmb)){
                index = i;
                break;
            }
        }
        return index;
    }
    
    public ObservableList<Producto> listarProducto(){
        ArrayList<Producto> productos = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = " CALL sp_ListarProductos()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int productoId = resultSet.getInt("productoId");
                String nombreProducto = resultSet.getString("nombreProducto");
                String descripcionProducto = resultSet.getString("descripcionProducto");
                int cantidadStock = resultSet.getInt("cantidadStock");
                double precioVentaUnitario = resultSet.getDouble("precioVentaUnitario");
                double precioVentaMayor = resultSet.getDouble("precioVentaMayor");
                double precioCompra = resultSet.getDouble("precioCompra");
                Blob imagenProducto = resultSet.getBlob("imagenProducto");                
                String distribuidor = resultSet.getString("distribuidor");
                String categoriaProducto = resultSet.getString("categoriaProducto");
            
                productos.add(new Producto(productoId, nombreProducto, descripcionProducto, cantidadStock, precioVentaUnitario, precioVentaMayor, precioCompra, imagenProducto, distribuidor, categoriaProducto));
            }
        }catch(SQLException e){
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
        return FXCollections.observableList(productos);
    }
    
    public ObservableList<CategoriaProducto> listarCategoriaProducto(){
        ArrayList<CategoriaProducto> categoriaProducto = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_ListarCategoriaProductos()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int categoriaProductoId = resultSet.getInt("categoriaProductoId");
                String nombreCategoria = resultSet.getString("nombreCategoria");
                String descripcionCategoria = resultSet.getString("descripcionCategoria");
                
                categoriaProducto.add(new CategoriaProducto(categoriaProductoId, nombreCategoria, descripcionCategoria));
            }
        }catch(SQLException e){
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
        return FXCollections.observableList(categoriaProducto);
    }
    
    public ObservableList<Distribuidor> listarDistribuidor(){
        ArrayList<Distribuidor> distribuidores = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_ListarDistribuidores()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int distribuidorId = resultSet.getInt("distribuidorId");
                String nombreDistribuidor = resultSet.getString("nombreDistribuidor");
                String direccionDistribuidor = resultSet.getString("direccionDistribuidor");
                String nitDistribuidor = resultSet.getString("nitDistribuidor");
                String telefonoDistribuidor = resultSet.getString("telefonoDistribuidor");
                String web = resultSet.getString("web");
                
                distribuidores.add(new Distribuidor(distribuidorId, nombreDistribuidor, direccionDistribuidor, nitDistribuidor, telefonoDistribuidor, web));
            }
        }catch(SQLException e){
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
        return FXCollections.observableList(distribuidores);
    }
    
    public void agregarProducto(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_AgregarProductos(?, ?, ?, ?, ?, ?, ?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNombreProducto.getText());
            statement.setString(2, taDescripcionProducto.getText());
            statement.setInt(3, Integer.parseInt(tfCantidadStock.getText()));
            statement.setDouble(4, Double.parseDouble(tfPrecioVentaU.getText()));
            statement.setDouble(5, Double.parseDouble(tfPrecioVentaM.getText()));
            statement.setDouble(6, Double.parseDouble(tfPrecioCompra.getText()));
            statement.setInt(7,((Distribuidor)cmbDistribuidor.getSelectionModel().getSelectedItem()).getDistribuidorId());
            statement.setInt(8,((CategoriaProducto)cmbCategoriaProducto.getSelectionModel().getSelectedItem()).getCategoriaProductoId());
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
    
     public void editarProducto(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_EditarProductos(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfProductoId.getText()));
            statement.setString(2, tfNombreProducto.getText());
            statement.setString(3, taDescripcionProducto.getText());
            statement.setInt(4, Integer.parseInt(tfCantidadStock.getText()));
            statement.setDouble(5, Double.parseDouble(tfPrecioVentaU.getText()));
            statement.setDouble(6, Double.parseDouble(tfPrecioVentaM.getText()));
            statement.setDouble(7, Double.parseDouble(tfPrecioCompra.getText()));
            statement.setInt(8,((Distribuidor)cmbDistribuidor.getSelectionModel().getSelectedItem()).getDistribuidorId());
            statement.setInt(9,((CategoriaProducto)cmbCategoriaProducto.getSelectionModel().getSelectedItem()).getCategoriaProductoId());
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
    
    public void setStage(Main stage) {
        this.stage = stage;
    }

    public Main getStage() {
        return stage;
    }
}

