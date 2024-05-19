/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ianalfaro.controller;

import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
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
import org.ianalfaro.model.Compra;
import org.ianalfaro.model.DetalleCompra;
import org.ianalfaro.model.Producto;
import org.ianalfaro.system.Main;
import org.ianalfaro.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class MenuDetalleComprasController implements Initializable {
    private Main stage;
    public int op;
    
    private static Connection conexion;
    private static PreparedStatement statement;
    private static ResultSet resultSet;

    @FXML
    TableView tblDetalleCompras;
    
    @FXML
    TableColumn colDetalleCompraId, colCantidadCompra, colProducto, colCompra;
    
    @FXML
    Button btnBack, btnVaciar, btnGuardar;
    
    @FXML
    TextField tfDetalleCompraId, tfCantidadCompra;
   
    @FXML
    ComboBox cmbProducto, cmbCompra;

    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnBack){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnGuardar){
            if(tfDetalleCompraId.getText().equals("")){
                agregarDetalleCompra();
                 SuperKinalAlert.getInstance().mostrarAlertaInformacion(400);
                cargarDatos();
            }else{
                editarDetalleCompra();
                SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(505);
                SuperKinalAlert.getInstance().mostrarAlertaInformacion(500);
                cargarDatos();
            }
        }else if(event.getSource() == btnVaciar){
            if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(605).get() == ButtonType.OK){
                vaciarForm();
            }
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbCompra.setItems(listarCompra());
        cmbProducto.setItems(listarProducto());
    }
    
    public void cargarDatos(){
        tblDetalleCompras.setItems(listarDetalleCompra());
        
        colDetalleCompraId.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("detalleCompraId"));
        colCantidadCompra.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("cantidadCompra"));
        colProducto.setCellValueFactory(new PropertyValueFactory<DetalleCompra, String>("productoId"));
        colCompra.setCellValueFactory(new PropertyValueFactory<DetalleCompra, String>("compraId"));
    }
    
    public void vaciarForm(){
        tfDetalleCompraId.clear();
        tfCantidadCompra.clear();
        cmbProducto.getSelectionModel().clearSelection();
        cmbCompra.getSelectionModel().clearSelection();
    }
    
    @FXML
    public void cargarForm(){
        DetalleCompra dc = (DetalleCompra)tblDetalleCompras.getSelectionModel().getSelectedItem();
        
        if(dc != null){
            tfDetalleCompraId.setText(Integer.toString(dc.getDetalleCompraId()));
            tfCantidadCompra.setText(Integer.toString(dc.getCantidadCompra()));
            cmbProducto.getSelectionModel().select(0);
            cmbCompra.getSelectionModel().select(obtenerIndexCompra());
        }
    }
    
    public int obtenerIndexProducto(){
        int index = 0;
        String productoTbl = ((DetalleCompra)tblDetalleCompras.getSelectionModel().getSelectedItem()).getProducto();
        for(int i = 0; i <= cmbProducto.getItems().size(); i++){
            String productoCmb = cmbProducto.getItems().get(i).toString();
            
            if(productoTbl.equals(productoCmb)){
                index = i;
                break;
            }
        }
        return index;
    }
    
    public int obtenerIndexCompra(){
        int index = 0;
        String compraTbl = ((DetalleCompra)tblDetalleCompras.getSelectionModel().getSelectedItem()).getCompra();
        for(int i = 0; i <= cmbCompra.getItems().size(); i++){
            String compraCmb = cmbCompra.getItems().get(i).toString();
            
            if(compraTbl.equals(compraCmb)){
                index = i;
                break;
            }
        }
        return index;
    }
    
    public ObservableList<DetalleCompra> listarDetalleCompra(){
        ArrayList<DetalleCompra> detalleCompras = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_ListarDetalleCompras()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int detalleCompraId = resultSet.getInt("detalleCompraId");
                int cantidadCompra = resultSet.getInt("cantidadCompra");
                String producto = resultSet.getString("productoId");
                String compra = resultSet.getString("compraId");
                
                detalleCompras.add(new DetalleCompra(detalleCompraId, cantidadCompra, producto, compra));
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
        return FXCollections.observableList(detalleCompras);
    }
    
    public ObservableList<Compra> listarCompra(){
        ArrayList<Compra> compras = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_ListarCompras()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int compraId = resultSet.getInt("compraId");
                Date fechaCompra = resultSet.getDate("fechaCompra");
                double totalCompra = resultSet.getDouble("totalCompra");
                
                compras.add(new Compra(compraId, fechaCompra, totalCompra));
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
        return FXCollections.observableList(compras);
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
    
    public void agregarDetalleCompra(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_AgregarDetalleCompras(?, ?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfCantidadCompra.getText()));
            statement.setInt(2, ((Producto)cmbProducto.getSelectionModel().getSelectedItem()).getProductoId());
            statement.setInt(3, ((Compra)cmbCompra.getSelectionModel().getSelectedItem()).getCompraId());
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
    
    public void editarDetalleCompra(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_EditarDetalleCompras(?, ?, ?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfDetalleCompraId.getText()));
            statement.setInt(2, Integer.parseInt(tfCantidadCompra.getText()));
            statement.setInt(3, ((Producto)cmbProducto.getSelectionModel().getSelectedItem()).getProductoId());
            statement.setInt(4, ((Compra)cmbCompra.getSelectionModel().getSelectedItem()).getCompraId());
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

