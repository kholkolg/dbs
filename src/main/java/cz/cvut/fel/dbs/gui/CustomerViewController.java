/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.dbs.gui;

import cz.cvut.fel.dbs.Entities.Customer;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;



/**
 *
 * @author Olga Kholkovskaia <olga.kholkovskaya@gmail.com>
 */
public class CustomerViewController extends ViewController<Customer> {
    
    @FXML
    protected TableView<Customer> customerTable;
    
    @FXML
    private TableColumn<Customer, Long> customerIdCol;

    @FXML
    private TableColumn<Customer, String> customerNameCol;

    @FXML
    private TableColumn<Customer, String> customerAddressCol;

    @FXML
    private TableColumn<Customer, String> customerPhoneCol;

    @FXML
    private TableColumn<Customer, String> customerEmailCol;
    
    @FXML
    private TableColumn<Customer, Integer> customerOrdersCol;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        super.initialize(url, rb);
        super.table = customerTable;
        initColumns();
        LOGGER.log(Level.FINER, "Customer table size: {0}", customerTable.getItems().size());
     }

    private void initColumns() {

        LOGGER.finer("Customer table columns init");

        customerTable.setPlaceholder(new Label("Table is empty."));

        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        
        customerEmailCol.setCellValueFactory(new PropertyValueFactory<>("primaryEmail"));
       
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));

        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));

        customerOrdersCol.setCellValueFactory(new PropertyValueFactory("numOrders"));
        
        LOGGER.finer(customerTable.getColumns().toString());
    }


}
        
