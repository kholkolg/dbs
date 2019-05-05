/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.dbs.gui;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.beans.property.StringProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


/**
 *
 * @author Olga Kholkovskaia <olga.kholkovskaya@gmail.com>
 * @param <E>
 */
public class FormController<E> implements Controller<E>, Initializable {
    
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
     
    protected E entity;
    
    private Boolean isNew;
    
    private Map<String, Button> buttonMap;
    
    private Map<String, TextField> fieldMap;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       LOGGER.finer(" ");
       entity = null;
       fieldMap = new HashMap<>();
       buttonMap = new HashMap<>();
    }
    
    public void setEntity(E entity) {
       this.entity = entity;
     }

    public E getEntity() {
       return this.entity;
    }

    public void clearForm() {
        entity = null;
        fieldMap.values().forEach((tf) -> {
            tf.clear();
        });
    }

    public Button getButton(String name) {
        return buttonMap.get(name);
    }

    public Boolean getIsNew() {
        return isNew;
    }
    
    public void setIsNew(Boolean v){
        isNew = v;
    }
    
    protected void setTextToField(String name, String text) {
        fieldMap.get(name).setText(text);
    }
    
    protected String getTextFromField(String name){
        return fieldMap.get(name).getText();
    }
    
    protected void addFieldToMap(String name, TextField tf){
        fieldMap.put(name, tf);
    }
    
    protected void addButtonToMap(String name, Button btn){
        buttonMap.put(name, btn);
    }
    
    protected StringProperty getFieldProperty(String name){
        return fieldMap.get(name).textProperty();
        
    }
 }
