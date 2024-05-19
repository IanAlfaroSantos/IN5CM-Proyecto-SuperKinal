/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ianalfaro.system;

import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.ianalfaro.controller.FormCargoController;
import org.ianalfaro.controller.FormCategoriaProductoController;
import org.ianalfaro.controller.FormClienteController;
import org.ianalfaro.controller.FormCompraController;
import org.ianalfaro.controller.FormDistribuidorController;
import org.ianalfaro.controller.FormEmpleadoController;
import org.ianalfaro.controller.FormProductoController;
import org.ianalfaro.controller.MenuCargosController;
import org.ianalfaro.controller.MenuCategoriaProductosController;
import org.ianalfaro.controller.MenuClientesController;
import org.ianalfaro.controller.MenuComprasController;
import org.ianalfaro.controller.MenuDetalleComprasController;
import org.ianalfaro.controller.MenuPrincipalController;
import org.ianalfaro.controller.MenuTicketSoportesController;
import org.ianalfaro.controller.MenuDistribuidoresController;
import org.ianalfaro.controller.MenuEmpleadosController;
import org.ianalfaro.controller.MenuFacturasController;
import org.ianalfaro.controller.MenuProductosController;
import org.ianalfaro.controller.MenuPromocionesController;

/**
 *
 * @author alfar
 */
public class Main extends Application {
    
    private Stage stage;
    private Scene scene;
    private final String URLVIEW = "/org/ianalfaro/view/";
    
    @Override
    public void start(Stage stage) throws Exception {
        
        this.stage = stage;
        stage.setTitle("Super Kinal App");
        menuPrincipalView();
        stage.show();
    }
    
    public Initializable switchScene(String fxmlName, int width, int height) throws Exception{
        Initializable resultado;
        FXMLLoader loader = new FXMLLoader();
        
        InputStream file = Main.class.getResourceAsStream(URLVIEW + fxmlName);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(URLVIEW + fxmlName));
        
        scene = new Scene((AnchorPane) loader.load(file), width, height);
        stage.setScene(scene);
        stage.sizeToScene();
             
        resultado = (Initializable)loader.getController();
        
        return resultado;
    }
    
    public void menuPrincipalView(){
        try{
            MenuPrincipalController menuPrincipalView = (MenuPrincipalController)switchScene("MenuPrincipalView.fxml", 950, 775);
            menuPrincipalView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuClienteView(){
         try{
            MenuClientesController menuClienteView = (MenuClientesController)switchScene("MenuClientesView.fxml", 1200, 850);
            menuClienteView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuCargosView(){
         try{
            MenuCargosController menuCargoView = (MenuCargosController)switchScene("MenuCargosView.fxml", 1200, 850);
            menuCargoView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuDistribuidorView(){
         try{
            MenuDistribuidoresController menuDistribuidoresView = (MenuDistribuidoresController)switchScene("MenuDistribuidorView.fxml", 1200, 850);
            menuDistribuidoresView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuCategoriaProductoView(){
         try{
            MenuCategoriaProductosController menuCategoriaProductosView = (MenuCategoriaProductosController)switchScene("MenuCategoriaProductosView.fxml", 1200, 850);
            menuCategoriaProductosView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuComprasView(){
         try{
            MenuComprasController menuCompraView = (MenuComprasController)switchScene("MenuComprasView.fxml", 1200, 850);
            menuCompraView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuTicketSoporteView(){
         try{
            MenuTicketSoportesController menuTicketSoporteView = (MenuTicketSoportesController)switchScene("MenuTicketSoportesView.fxml", 1200, 850);
            menuTicketSoporteView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuEmpleadoView(){
         try{
            MenuEmpleadosController menuEmpleadoView = (MenuEmpleadosController)switchScene("MenuEmpleadosView.fxml", 1200, 850);
            menuEmpleadoView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuFacturaView(){
         try{
            MenuFacturasController menuFacturaView = (MenuFacturasController)switchScene("MenuFacturasView.fxml", 1200, 850);
            menuFacturaView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuPromocionView(){
         try{
            MenuPromocionesController menuPromocionView = (MenuPromocionesController)switchScene("MenuPromocionesView.fxml", 1200, 850);
            menuPromocionView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuProductoView(){
         try{
            MenuProductosController menuProductoView = (MenuProductosController)switchScene("MenuProductosView.fxml", 1500, 950);
            menuProductoView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void menuDetalleCompraView(){
         try{
            MenuDetalleComprasController menuDetalleCompraView = (MenuDetalleComprasController)switchScene("MenuDetalleComprasView.fxml", 1200, 850);
            menuDetalleCompraView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void formClienteView(int op){
         try{
            FormClienteController formClienteView = (FormClienteController)switchScene("FormClienteView.fxml", 500, 750);
            formClienteView.setOp(op);
            formClienteView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void formCargoView(int op){
         try{
            FormCargoController formCargoView = (FormCargoController)switchScene("FormCargoView.fxml", 500, 750);
            formCargoView.setOp(op);
            formCargoView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void formDistribuidorView(int op){
         try{
            FormDistribuidorController formDistribuidorView = (FormDistribuidorController)switchScene("FormDistribuidorView.fxml", 500, 750);
            formDistribuidorView.setOp(op);
            formDistribuidorView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void formCategoriaProductoView(int op){
         try{
            FormCategoriaProductoController formCategoriaProductoView = (FormCategoriaProductoController)switchScene("FormCategoriaProductoView.fxml", 500, 750);
            formCategoriaProductoView.setOp(op);
            formCategoriaProductoView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void formCompraView(int op){
         try{
            FormCompraController formCompraView = (FormCompraController)switchScene("FormCompraView.fxml", 500, 750);
            formCompraView.setOp(op);
            formCompraView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void formProductoView(int op){
         try{
            FormProductoController formProductoView = (FormProductoController)switchScene("FormProductoView.fxml", 1200, 850);
            formProductoView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void formEmpleadoView(){
         try{
            FormEmpleadoController formEmpleadoView = (FormEmpleadoController)switchScene("FormEmpleadoView.fxml", 500, 750);
            formEmpleadoView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
