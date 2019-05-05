/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.dbs.gui;

import cz.cvut.fel.dbs.Entities.Location;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;



/**
 * FXML Controller class
 *
 * @author Olga Kholkovskaia <olga.kholkovskaya@gmail.com>
 */
public class LocationViewController extends ViewController<Location> {

   private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
    private ObservableList<Location> data;
    
    @FXML
    private TableView<Location> locationTable;

    @FXML
    private TableColumn<Location, Long> locationIdCol;

    @FXML
    private TableColumn<Location, String> locationNameCol;

    @FXML
    private TableColumn<Location, String> locationCoordinatesCol;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LOGGER.finer(" ");
        super.initialize(url, rb);
        super.table = locationTable;
        initColumns();
        LOGGER.log(Level.FINER, "Location table size: {0}", locationTable.getItems().size());
    }

    private void initColumns() {
        LOGGER.finer(" ");

        locationTable.setPlaceholder(new Label("Table is empty."));

        locationIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        
        locationCoordinatesCol.setCellValueFactory(new PropertyValueFactory<>("coordinates"));
       
        locationNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        // customerOrdersCol.setCellValueFactory(new PropertyValueFactory("numOrders"));
        LOGGER.finer(locationTable.getColumns().toString());
    }
    
}
