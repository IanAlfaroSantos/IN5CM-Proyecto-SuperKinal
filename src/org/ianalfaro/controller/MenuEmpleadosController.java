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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.ianalfaro.dao.Conexion;
import org.ianalfaro.dto.EmpleadoDTO;
import org.ianalfaro.model.Cargo;
import org.ianalfaro.model.Empleado;
import org.ianalfaro.system.Main;
import org.ianalfaro.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class MenuEmpleadosController implements Initializable {
    private Main stage;
    public int op;
    
    private static Connection conexion;
    private static PreparedStatement statement;
    private static ResultSet resultSet;

    @FXML
    TableView tblEmpleados;
    
    @FXML
    TableColumn colEmpleadoId, colNombreEmpleado, colApellidoEmpleado, colSueldo, colHoraEntrada, colHoraSalida, colCargoId, colEncargadoId;
    
    @FXML
    Button btnBack, btnVaciar, btnGuardar, btnAsignarEncargado;
    
    @FXML
    TextField tfEmpleadoId, tfNombreEmpleado, tfApellidoEmpleado, tfSueldo, tfHoraEntrada, tfHoraSalida;
    
    @FXML
    ComboBox cmbCargoId;
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnBack){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnGuardar){
            if(tfEmpleadoId.getText().equals("")){
                agregarEmpleado();
                SuperKinalAlert.getInstance().mostrarAlertaInformacion(400);
                cargarDatos();
            }else{
                editarEmpleado();
                SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(505);
                SuperKinalAlert.getInstance().mostrarAlertaInformacion(500);
                cargarDatos();
            }
        }else if(event.getSource() == btnVaciar){
            if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(605).get() == ButtonType.OK){
                vaciarForm();
                cargarDatos();
            }
        }else if(event.getSource() == btnAsignarEncargado){
            EmpleadoDTO.getEmpleadoDTO().setEmpleado((Empleado)tblEmpleados.getSelectionModel().getSelectedItem());
            stage.formEmpleadoView();
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbCargoId.setItems(listarCargo());
    }
    
    public void cargarDatos(){
        tblEmpleados.setItems(listarEmpleado());

        colEmpleadoId.setCellValueFactory(new PropertyValueFactory<Empleado, Integer>("empleadoId"));
        colNombreEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, String>("nombreEmpleado"));
        colApellidoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, String>("apellidoEmpleado"));
        colSueldo.setCellValueFactory(new PropertyValueFactory<Empleado, Double>("sueldo"));
        colHoraEntrada.setCellValueFactory(new PropertyValueFactory<Empleado, String>("horaEntrada"));
        colHoraSalida.setCellValueFactory(new PropertyValueFactory<Empleado, String>("horaSalida"));
        colCargoId.setCellValueFactory(new PropertyValueFactory<Empleado, String>("cargo"));
        colEncargadoId.setCellValueFactory(new PropertyValueFactory<Empleado, String>("encargado"));
    }
    
    public void vaciarForm(){
        tfEmpleadoId.clear();
        tfNombreEmpleado.clear();
        tfApellidoEmpleado.clear();
        tfSueldo.clear();
        tfHoraEntrada.clear();
        tfHoraSalida.clear();
        cmbCargoId.getSelectionModel().clearSelection();
    }
    
    @FXML
    public void cargarForm(){
        Empleado e = (Empleado)tblEmpleados.getSelectionModel().getSelectedItem();
        
        if(e != null){
            tfEmpleadoId.setText(Integer.toString(e.getEmpleadoId()));
            tfNombreEmpleado.setText(e.getNombreEmpleado());
            tfApellidoEmpleado.setText(e.getApellidoEmpleado());
            tfSueldo.setText(Double.toString(e.getSueldo()));
            tfHoraEntrada.setText(e.getHoraEntrada());
            tfHoraSalida.setText(e.getHoraSalida());
            cmbCargoId.getSelectionModel().select(obtenerIndexCargo());
        }
    }
    
    public int obtenerIndexCargo(){
        int index = 0;
        String cargoTbl = ((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getCargo();
        for(int i = 0; i <= cmbCargoId.getItems().size(); i++){
            String cargoCmb = cmbCargoId.getItems().get(i).toString();
        
            if(cargoTbl.equals(cargoCmb)){
                index = i;
                break;
            }
        }   
        return index;
    }
    
    public ObservableList<Empleado> listarEmpleado(){
        ArrayList<Empleado> empleados = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_ListarEmpleados()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int empleadoId = resultSet.getInt("empleadoId");
                String nombreEmpleado = resultSet.getString("nombreEmpleado");
                String apellidoEmpleado = resultSet.getString("apellidoEmpleado");
                double sueldo = resultSet.getDouble("sueldo");
                String horaEntrada = resultSet.getString("horaEntrada");
                String horaSalida = resultSet.getString("horaSalida");
                String cargo = resultSet.getString("cargo");
                String encargado = resultSet.getString("encargado");
                
                empleados.add(new Empleado(empleadoId, nombreEmpleado, apellidoEmpleado, sueldo, horaEntrada, horaSalida, cargo, encargado));
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
        return FXCollections.observableList(empleados);
    }
    
    public ObservableList<Cargo> listarCargo(){
        ArrayList<Cargo> cargos = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_ListarCargos()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int cargoId = resultSet.getInt("cargoId");
                String nombreCargo = resultSet.getString("nombreCargo");
                String descripcionCargo = resultSet.getString("descripcionCargo");
                
                cargos.add(new Cargo(cargoId, nombreCargo, descripcionCargo));
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
        return FXCollections.observableList(cargos);
    }
    
    public void agregarEmpleado(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_AgregarEmpleados(?, ?, ?, ?, ?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNombreEmpleado.getText());
            statement.setString(2, tfApellidoEmpleado.getText());
            statement.setDouble(3, Double.parseDouble(tfSueldo.getText()));
            statement.setString(4, tfHoraEntrada.getText());
            statement.setString(5, tfHoraSalida.getText());
            statement.setInt(6, ((Cargo)cmbCargoId.getSelectionModel().getSelectedItem()).getCargoId());
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
    
    public void editarEmpleado(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_EditarEmpleados(?, ?, ?, ?, ?, ?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfEmpleadoId.getText()));
            statement.setString(2, tfNombreEmpleado.getText());
            statement.setString(3, tfApellidoEmpleado.getText());
            statement.setDouble(4, Double.parseDouble(tfSueldo.getText()));
            statement.setString(5, tfHoraEntrada.getText());
            statement.setString(6, tfHoraSalida.getText());
            statement.setInt(7, ((Cargo)cmbCargoId.getSelectionModel().getSelectedItem()).getCargoId());
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