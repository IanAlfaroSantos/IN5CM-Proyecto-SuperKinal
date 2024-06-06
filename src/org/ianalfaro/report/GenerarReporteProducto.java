/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ianalfaro.report;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.ianalfaro.dao.Conexion;
import win.zqxu.jrviewer.JRViewerFX;

/**
 *
 * @author informatica
 */
public class GenerarReporteProducto {
    private static GenerarReporteProducto instance;
    
    private static Connection conexion = null;
    
    private GenerarReporteProducto(){
    
    }
    
    public static GenerarReporteProducto getInstance(){
        if(instance == null){
            instance = new GenerarReporteProducto();
        }
        return instance;
    }
    
    public void generarProducto(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            
            Map<String, Object> parametros = new HashMap<>();
            
            Stage reportStage = new Stage();
            
            JasperPrint reporte = JasperFillManager.fillReport(GenerarReporteProducto.class.getResourceAsStream("/org/ianalfaro/report/Producto.jasper"), parametros, conexion);
            
            JRViewerFX reportView = new JRViewerFX(reporte);
            
            Pane root = new Pane();
            root.getChildren().add(reportView);
            
            reportView.setPrefSize(1365, 740);
            
            Scene scene = new Scene(root);
            reportStage.setScene(scene);
            reportStage.setTitle("Producto");
            reportStage.show();
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
