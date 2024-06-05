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
public class GenerarReporteFactura {
    private static GenerarReporteFactura instance;
    
    private static Connection conexion = null;
    
    private GenerarReporteFactura(){
    
    }
    
    public static GenerarReporteFactura getInstance(){
        if(instance == null){
            instance = new GenerarReporteFactura();
        }
        return instance;
    }
    
    public void generarFactura(int facId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("facId", facId);
            
            Stage reportStage = new Stage();
            
            JasperPrint reporte = JasperFillManager.fillReport(GenerarReporteFactura.class.getResourceAsStream("/org/ianalfaro/report/Factura.jasper"), parametros, conexion);
            
            JRViewerFX reportView = new JRViewerFX(reporte);
            
            Pane root = new Pane();
            root.getChildren().add(reportView);
            
            reportView.setPrefSize(1000, 800);
            
            Scene scene = new Scene(root);
            reportStage.setScene(scene);
            reportStage.setTitle("Factura");
            reportStage.show();
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
