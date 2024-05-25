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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.ianalfaro.dao.Conexion;
import org.ianalfaro.model.Factura;
import org.ianalfaro.model.DetalleFactura;
import org.ianalfaro.model.Producto;
import org.ianalfaro.system.Main;
import org.ianalfaro.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class MenuDetalleFacturasController implements Initializable {
    private Main stage;
    public int op;
    
    private static Connection conexion;
    private static PreparedStatement statement;
    private static ResultSet resultSet;

    @FXML
    TableView tblDetalleFacturas;
    
    @FXML
    TableColumn colDetalleFacturaId, colProducto, colFactura;
    
    @FXML
    Button btnBack, btnVaciar, btnGuardar;
    
    @FXML
    TextField tfDetalleFacturaId;
   
    @FXML
    ComboBox cmbProducto, cmbFactura;

    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnBack){
            stage.menuFacturaView();
        }else if(event.getSource() == btnGuardar){
            if(tfDetalleFacturaId.getText().equals("")){
                agregarDetalleFactura();
                SuperKinalAlert.getInstance().mostrarAlertaInformacion(400);
                cargarDatos();
            }else{
                editarDetalleFactura();
                SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(505);
                SuperKinalAlert.getInstance().mostrarAlertaInformacion(500);
                cargarDatos();
            }
        }else if(event.getSource() == btnVaciar){
            if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(605).get() == ButtonType.OK){
                vaciarForm();
                cargarDatos();
            }
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbFactura.setItems(listarFactura());
        cmbProducto.setItems(listarProducto());
    }
    
    public void cargarDatos(){
        tblDetalleFacturas.setItems(listarDetalleFactura());
        
        colDetalleFacturaId.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Integer>("detalleFacturaId"));
        colFactura.setCellValueFactory(new PropertyValueFactory<DetalleFactura, String>("facturaId"));
        colProducto.setCellValueFactory(new PropertyValueFactory<DetalleFactura, String>("productoId"));
    }
    
    public void vaciarForm(){
        tfDetalleFacturaId.clear();
        cmbFactura.getSelectionModel().clearSelection();
        cmbProducto.getSelectionModel().clearSelection();
    }
    
    @FXML
    public void cargarForm(){
        DetalleFactura df = (DetalleFactura)tblDetalleFacturas.getSelectionModel().getSelectedItem();
        
        if(df != null){
            tfDetalleFacturaId.setText(Integer.toString(df.getDetalleFacturaId()));
            cmbFactura.getSelectionModel().select(obtenerIndexFactura());
            cmbProducto.getSelectionModel().select(obtenerIndexProducto());
        }
    }
    
    public int obtenerIndexFactura(){
        int index = 0;
        String facturaTbl = ((DetalleFactura)tblDetalleFacturas.getSelectionModel().getSelectedItem()).getFactura();
        for(int i = 0; i <= cmbFactura.getItems().size(); i++){
            String facturaCmb = cmbFactura.getItems().get(i).toString();
            
            if(facturaTbl.equals(facturaCmb)){
                index = i;
                break;
            }
        }
        return index;
    }
    
    public int obtenerIndexProducto(){
        int index = 0;
        String productoTbl = ((DetalleFactura)tblDetalleFacturas.getSelectionModel().getSelectedItem()).getProducto();
        for(int i = 0; i <= cmbProducto.getItems().size(); i++){
            String productoCmb = cmbProducto.getItems().get(i).toString();
            
            if(productoTbl.equals(productoCmb)){
                index = i;
                break;
            }
        }
        return index;
    }
    
    public ObservableList<DetalleFactura> listarDetalleFactura(){
        ArrayList<DetalleFactura> detalleFacturas = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_ListarDetalleFacturas()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int detalleFacturaId = resultSet.getInt("detalleFacturaId");
                String factura = resultSet.getString("facturaId");
                String producto = resultSet.getString("productoId");
                
                detalleFacturas.add(new DetalleFactura(detalleFacturaId, factura, producto));
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
        return FXCollections.observableList(detalleFacturas);
    }
    
    public ObservableList<Factura> listarFactura(){
        ArrayList<Factura> facturas = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = " CALL sp_ListarFacturas()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int facturaId = resultSet.getInt("facturaId");
                String fecha = resultSet.getString("fecha");
                String hora = resultSet.getString("hora");
                String cliente = resultSet.getString("cliente");
                String empleado = resultSet.getString("empleado");
                Double total = resultSet.getDouble("total");
            
                facturas.add(new Factura(facturaId, fecha, hora, cliente, empleado, total));
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
        
        
        return FXCollections.observableList(facturas);
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
    
    public void agregarDetalleFactura(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_AgregarDetalleFacturas(?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, ((Factura)cmbFactura.getSelectionModel().getSelectedItem()).getFacturaId());
            statement.setInt(2, ((Producto)cmbProducto.getSelectionModel().getSelectedItem()).getProductoId());
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
    
    public void editarDetalleFactura(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_EditarDetalleFacturas(?, ?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfDetalleFacturaId.getText()));
            statement.setInt(2, ((Factura)cmbFactura.getSelectionModel().getSelectedItem()).getFacturaId());
            statement.setInt(3, ((Producto)cmbProducto.getSelectionModel().getSelectedItem()).getProductoId());
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

