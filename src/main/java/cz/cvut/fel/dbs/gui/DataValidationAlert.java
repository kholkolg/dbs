/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.dbs.gui;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author Olga Kholkovskaia <olga.kholkovskaya@gmail.com>
 */
public class DataValidationAlert {
    
    
    public DataValidationAlert(Class entityClass, String attr, String value){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(entityClass.getSimpleName()+ " exception");
        alert.setHeaderText("Failed to set " + attr + "\n" +
           "Value " + value + " is invalid.");
        alert.setContentText(null);

        alert.showAndWait();
    }
}
