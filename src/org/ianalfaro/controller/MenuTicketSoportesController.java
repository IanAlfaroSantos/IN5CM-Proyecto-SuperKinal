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
import org.ianalfaro.model.Cliente;
import org.ianalfaro.model.Factura;
import org.ianalfaro.model.TicketSoporte;
import org.ianalfaro.system.Main;
import org.ianalfaro.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class MenuTicketSoportesController implements Initializable {
    private Main stage;
    public int op;
    
    private static Connection conexion;
    private static PreparedStatement statement;
    private static ResultSet resultSet;

    @FXML
    TableView tblTicketSoportes;
    
    @FXML
    TableColumn colTicketSoporteId, colDescripcionTicket, colEstatus, colCliente, colFacturaId;
    
    @FXML
    Button btnBack, btnVaciar, btnGuardar;
    
    @FXML
    TextField tfTicketSoporteId;
    
    @FXML
    TextArea taDescripcionTicket;
    
    @FXML
    ComboBox cmbEstatus, cmbCliente, cmbFacturaId;

    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnBack){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnGuardar){
            if(tfTicketSoporteId.getText().equals("")){
                agregarTicketSoporte();
                SuperKinalAlert.getInstance().mostrarAlertaInformacion(400);
                cargarDatos();
            }else{
                editarTicketSoporte();
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
        cargarCmbEstatus();
        cmbCliente.setItems(listarCliente());
        cmbFacturaId.setItems(listarFactura());
    }
    
    public void cargarDatos(){
        tblTicketSoportes.setItems(listarTicketSoporte());
        
        colTicketSoporteId.setCellValueFactory(new PropertyValueFactory<TicketSoporte, Integer>("ticketSoporteId"));
        colDescripcionTicket.setCellValueFactory(new PropertyValueFactory<TicketSoporte, String>("descripcionTicket"));
        colEstatus.setCellValueFactory(new PropertyValueFactory<TicketSoporte, String>("estatus"));
        colCliente.setCellValueFactory(new PropertyValueFactory<TicketSoporte, String>("cliente"));
        colFacturaId.setCellValueFactory(new PropertyValueFactory<TicketSoporte, String>("factura"));
    }
    
    public void cargarCmbEstatus(){
        cmbEstatus.getItems().add("En proceso");
        cmbEstatus.getItems().add("Finalizado");
    }
    
    public void vaciarForm(){
        tfTicketSoporteId.clear();
        taDescripcionTicket.clear();
        cmbEstatus.getSelectionModel().clearSelection();
        cmbCliente.getSelectionModel().clearSelection();
        cmbFacturaId.getSelectionModel().clearSelection();
    }
    
    @FXML
    public void cargarForm(){
        TicketSoporte ts = (TicketSoporte)tblTicketSoportes.getSelectionModel().getSelectedItem();
        
        if(ts != null){
            tfTicketSoporteId.setText(Integer.toString(ts.getTicketSoporteId()));
            taDescripcionTicket.setText(ts.getDescripcionTicket());
            cmbEstatus.getSelectionModel().select(0);
            cmbCliente.getSelectionModel().select(obtenerIndexCliente());
            cmbFacturaId.getSelectionModel().select(obtenerIndexFactura());
        }
    }
    
    public int obtenerIndexCliente(){
        int index = 0;
        String clienteTbl = ((TicketSoporte)tblTicketSoportes.getSelectionModel().getSelectedItem()).getCliente();
        for(int i = 0; i <= cmbCliente.getItems().size(); i++){
            String clienteCmb = cmbCliente.getItems().get(i).toString();
            
            if(clienteTbl.equals(clienteCmb)){
                index = i;
                break;
            }
        }
        
        return index;
    }
    
    public int obtenerIndexFactura(){
        int index = 0;
        String facturaTbl = ((TicketSoporte)tblTicketSoportes.getSelectionModel().getSelectedItem()).getFactura();
        for(int i = 0; i <= cmbFacturaId.getItems().size(); i++){
            String facturaCmb = cmbFacturaId.getItems().get(i).toString();
            
            if(facturaTbl.equals(facturaCmb)){
                index = i;
                break;
            }
        }
        
        return index;
    }
    
    public ObservableList<TicketSoporte> listarTicketSoporte(){
        ArrayList<TicketSoporte> ticketSoportes = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_ListarTicketSoportes()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int ticketSoporteId = resultSet.getInt("ticketSoporteId");
                String descripcionTicket = resultSet.getString("descripcionTicket");
                String estatus = resultSet.getString("estatus");
                String cliente = resultSet.getString("cliente");
                String factura = resultSet.getString("factura");
                
                ticketSoportes.add(new TicketSoporte(ticketSoporteId, descripcionTicket, estatus, cliente, factura));
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
        return FXCollections.observableList(ticketSoportes);
    }
    
    public ObservableList<Cliente> listarCliente(){
        ArrayList<Cliente> clientes = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_ListarClientes()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int clienteId = resultSet.getInt("clienteId");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                String telefono = resultSet.getString("telefono");
                String direccion = resultSet.getString("direccion");
                String nit = resultSet.getString("nit");
                
                clientes.add(new Cliente(clienteId, nombre, apellido, telefono, direccion, nit));
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
        return FXCollections.observableList(clientes);
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
    
    public void agregarTicketSoporte(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_AgregarTicketSoportes(?, ?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, taDescripcionTicket.getText());
            statement.setInt(2, ((Cliente)cmbCliente.getSelectionModel().getSelectedItem()).getClienteId());
            statement.setInt(3, ((Factura)cmbFacturaId.getSelectionModel().getSelectedItem()).getFacturaId());
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
    
    public void editarTicketSoporte(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_EditarTicketSoportes(?, ?, ?, ?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfTicketSoporteId.getText()));
            statement.setString(2, taDescripcionTicket.getText());
            statement.setString(3, (cmbEstatus.getSelectionModel().getSelectedItem().toString()));
            statement.setInt(4, ((Cliente)cmbCliente.getSelectionModel().getSelectedItem()).getClienteId());
            statement.setInt(5, ((Factura)cmbFacturaId.getSelectionModel().getSelectedItem()).getFacturaId());
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

