/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.dbs.gui;

import cz.cvut.fel.dbs.gui.Controller;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

/**
 *
 * @author Olga Kholkovskaia <olga.kholkovskaya@gmail.com>
 * @param <E>
 */
public class ViewController<E> implements Controller<E>, Initializable {
    
    protected static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
    protected ObservableList<E> data;
    //table
    protected TableView table;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LOGGER.finer(" ");
        data = FXCollections.observableArrayList();
    }

    protected void updateTable() {
        table.getItems().clear();
        table.getItems().addAll(data);
    }

    protected TableView<E> getTable() {
        return table;
    }

    protected ObservableList<E> getData() {
        return data;
    }
    
}
