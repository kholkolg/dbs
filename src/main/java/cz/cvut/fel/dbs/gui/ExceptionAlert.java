/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.dbs.gui;

import java.io.PrintWriter;
import java.io.StringWriter;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class ExceptionAlert {
    
    public ExceptionAlert(Exception ex, String operation, Object entity){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(ex.getClass().getSimpleName()+ " exception");
        alert.setHeaderText("Failed to " + operation + " entity:\n" +
            entity.toString() +" \n Transaction rolled back.");
        alert.setContentText("Stacktrace:");
        
        //format stacktrace 
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
            
        TextArea stackMsg = new TextArea(sw.toString());
        stackMsg.setEditable(false);
        stackMsg.setWrapText(true);
        
        GridPane.setVgrow(stackMsg, Priority.ALWAYS);
        GridPane.setHgrow(stackMsg, Priority.ALWAYS);
        GridPane msgPane = new GridPane();
        msgPane.add(stackMsg, 0, 0);
        alert.getDialogPane().setExpandableContent(msgPane);
        alert.showAndWait();
    }
    
}
