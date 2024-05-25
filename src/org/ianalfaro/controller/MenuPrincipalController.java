/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ianalfaro.controller;

import java.awt.Button;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import org.ianalfaro.system.Main;

/**
 *
 * @author informatica
 */
public class MenuPrincipalController implements Initializable {
    private Main stage;
    
    @FXML
    MenuItem btnMenuClientes, btnMenuCargos, btnMenuEmpleados, btnMenuFacturas, btnMenuTickets, btnMenuDistribuidores,
            btnMenuCategorias, btnMenuProductos, btnMenuPromociones, btnMenuDetallesFacturas, btnMenuCompras, btnMenuDetallesCompras;
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnMenuClientes){
            stage.menuClienteView();
        }else if(event.getSource() == btnMenuCargos){
            stage.menuCargosView();
        }else if(event.getSource() == btnMenuEmpleados){
            stage.menuEmpleadoView();
        }else if(event.getSource() == btnMenuFacturas){
            stage.menuFacturaView();
        }else if(event.getSource() == btnMenuTickets){
            stage.menuTicketSoporteView();
        }else if(event.getSource() == btnMenuDistribuidores){
            stage.menuDistribuidorView();
        }else if(event.getSource() == btnMenuCategorias){
            stage.menuCategoriaProductoView();
        }else if(event.getSource() == btnMenuProductos){
            stage.menuProductoView();
        }else if(event.getSource() == btnMenuPromociones){
            stage.menuPromocionView();
        }else if(event.getSource() == btnMenuCompras){
            stage.menuComprasView();
        }
    }
    
    public void setStage(Main stage) {
        this.stage = stage;
    }

    public Main getStage() {
        return stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
}
