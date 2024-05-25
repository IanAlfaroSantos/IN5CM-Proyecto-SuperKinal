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
import java.text.SimpleDateFormat;
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
import org.ianalfaro.model.Promocion;
import org.ianalfaro.model.Producto;
import org.ianalfaro.system.Main;
import org.ianalfaro.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class MenuPromocionesController implements Initializable {
    private Main stage;
    public int op;
    
    private static Connection conexion;
    private static PreparedStatement statement;
    private static ResultSet resultSet;

    @FXML
    TableView tblPromociones;
    
    @FXML
    TableColumn colPromocionId, colDescripcionPromocion, colPrecioPromocion, colFechaInicio, colFechaFinalizacion, colProducto;
    
    @FXML
    Button btnBack, btnVaciar, btnGuardar;
    
    @FXML
    TextField tfPromocionId, tfPrecioPromocion, tfFechaInicio, tfFechaFinalizacion;
    
    @FXML
    TextArea taDescripcionPromocion;
    
    @FXML
    ComboBox cmbProducto;

    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnBack){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnGuardar){
            if(tfPromocionId.getText().equals("")){
                agregarPromocion();
                SuperKinalAlert.getInstance().mostrarAlertaInformacion(400);
                cargarDatos();
            }else{
                editarPromocion();
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
        cmbProducto.setItems(listarProducto());
    }
    
    public void cargarDatos(){
        tblPromociones.setItems(listarPromociones());
        
        colPromocionId.setCellValueFactory(new PropertyValueFactory<Promocion, Integer>("promocionId"));
        colPrecioPromocion.setCellValueFactory(new PropertyValueFactory<Promocion, Double>("precioPromocion"));
        colDescripcionPromocion.setCellValueFactory(new PropertyValueFactory<Promocion, String>("descripcionPromocion"));
        colFechaInicio.setCellValueFactory(new PropertyValueFactory<Promocion, String>("fechaInicio"));
        colFechaFinalizacion.setCellValueFactory(new PropertyValueFactory<Promocion, String>("fechaFinalizacion"));
        colProducto.setCellValueFactory(new PropertyValueFactory<Promocion, String>("producto"));
    }

    public void vaciarForm(){
        tfPromocionId.clear();
        tfPrecioPromocion.clear();
        taDescripcionPromocion.clear();
        tfFechaInicio.clear();
        tfFechaFinalizacion.clear();
        cmbProducto.getSelectionModel().clearSelection();
    }
    
    @FXML
    public void cargarForm(){
        Promocion pm = (Promocion)tblPromociones.getSelectionModel().getSelectedItem();
        
        if(pm != null){
            tfPromocionId.setText(Integer.toString(pm.getPromocionId()));
            tfPrecioPromocion.setText(Double.toString(pm.getPrecioPromocion()));
            taDescripcionPromocion.setText(pm.getDescripcionPromocion());
            tfFechaInicio.setText(pm.getFechaInicio());
            tfFechaFinalizacion.setText(pm.getFechaFinalizacion());
            cmbProducto.getSelectionModel().select(0);
        }
    }
    
    public int obtenerIndexProducto(){
        int index = 0;
        String productoTbl = ((Promocion)tblPromociones.getSelectionModel().getSelectedItem()).getProducto();
        for(int i = 0; i <= cmbProducto.getItems().size(); i++){
            String productoCmb = cmbProducto.getItems().get(i).toString();
            
            if(productoTbl.equals(productoCmb)){
                index = i;
                break;
            }
        }
        return index;
    }
    
    public ObservableList<Promocion> listarPromociones(){
        ArrayList<Promocion> promociones = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = " CALL sp_ListarPromociones()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int promocionId = resultSet.getInt("promocionId");
                Double precioPromocion = resultSet.getDouble("precioPromocion");
                String descripcionPromocion = resultSet.getString("descripcionPromocion");
                String fechaInicio = resultSet.getString("fechaInicio");
                String fechaFinalizacion = resultSet.getString("fechaFinalizacion");
                String producto = resultSet.getString("productoId");
            
                promociones.add(new Promocion(promocionId, precioPromocion, descripcionPromocion, fechaInicio, fechaFinalizacion, producto));
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
        
        
        return FXCollections.observableList(promociones);
    }
    
    public ObservableList<Producto> listarProducto(){
        ArrayList<Producto> productos = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_ListarProductos()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int productoId = resultSet.getInt("productoId");
                String nombreProducto = resultSet.getString("nombreProducto");
                String descripcionProducto = resultSet.getString("descripcionProducto");
                int cantidadStock = resultSet.getInt("cantidadStock");
                Double precioVentaUnitario = resultSet.getDouble("precioVentaUnitario");
                Double precioVentaMayor = resultSet.getDouble("precioVentaMayor");
                Double precioCompra = resultSet.getDouble("precioCompra");
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
    
    public void agregarPromocion(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_AgregarPromociones(?, ?, ?, ?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfPrecioPromocion.getText());
            statement.setString(2, taDescripcionPromocion.getText());
            statement.setString(3, tfFechaInicio.getText());
            statement.setString(4, tfFechaFinalizacion.getText());
            statement.setInt(5,((Producto)cmbProducto.getSelectionModel().getSelectedItem()).getProductoId());
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
    
    public void editarPromocion(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_EditarPromociones(?, ?, ?, ?, ?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfPromocionId.getText()));
            statement.setString(2, tfPrecioPromocion.getText());
            statement.setString(3, taDescripcionPromocion.getText());
            statement.setString(4, tfFechaInicio.getText());
            statement.setString(5, tfFechaFinalizacion.getText());
            statement.setInt(6,((Producto)cmbProducto.getSelectionModel().getSelectedItem()).getProductoId());
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

