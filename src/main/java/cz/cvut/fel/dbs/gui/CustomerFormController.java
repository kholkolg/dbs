/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.dbs.gui;


import cz.cvut.fel.dbs.Entities.Customer;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Olga Kholkovskaia <olga.kholkovskaya@gmail.com>
 */
public class CustomerFormController extends FormController<Customer> {
    
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
    @FXML
    private Button customerCancelButton; 
    
    @FXML
    private Button customerSaveButton;
    
    @FXML
    private TextField customerNameTextField;
    
    @FXML
    private TextField customerSurnameTextField;
    
    @FXML
    private TextField customerPhoneTextField;
    
    @FXML
    private TextField customerEmail1TextField;
    
    @FXML
    private TextField customerEmail2TextField;
    
    @FXML
    private TextField customerCityTextField;
    
    @FXML
    private TextField customerStreetTextField;
    
    @FXML
    private TextField customerBuildingTextField;
    
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        addFieldsToMap();
        addButtonToMap("save", customerSaveButton);
        addButtonToMap("cancel", customerCancelButton);
        //disable button then at least one of obligatory fields is unfield
        customerSaveButton.disableProperty().bind(getFieldBinding());
    }
      
    @Override
    public void setEntity(Customer c){
        entity = c;
        LOGGER.finer(c.toString());
        fillFields();
    }
    
    @Override
    public Customer getEntity(){
        if(entity == null){
            entity = new Customer();
        }
        updateEntity();
        LOGGER.finer(entity.toString());
        return entity;
    }
    
    private BooleanBinding getFieldBinding(){
        BooleanBinding bb = new BooleanBinding() {
            {super.bind(getFieldProperty("name"),
                        getFieldProperty("surname"),
                        getFieldProperty("city"),
                        getFieldProperty("street"),
                        getFieldProperty("building"),
                        getFieldProperty("phone"),
                        getFieldProperty("email1"));        
            }
            @Override
            protected boolean computeValue() {
                return (getTextFromField("name").isEmpty()
                    || getTextFromField("surname").isEmpty()
                    || getTextFromField("city").isEmpty() 
                    || getTextFromField("street").isEmpty()
                    || getTextFromField("building").isEmpty()
                    || getTextFromField("phone").isEmpty())
                    || getTextFromField("email1").isEmpty();
            }
        };
       return bb;
    }
    
    private void addFieldsToMap(){
        addFieldToMap("name", customerNameTextField);
        addFieldToMap("surname", customerSurnameTextField);
        addFieldToMap("phone", customerPhoneTextField);
        addFieldToMap("city", customerCityTextField);
        addFieldToMap("street", customerStreetTextField);
        addFieldToMap("building", customerBuildingTextField);
        addFieldToMap("email1", customerEmail1TextField);
        addFieldToMap("email2", customerEmail2TextField);
    }
            
    private void fillFields(){
        setTextToField("name", entity.getName());
        setTextToField("surname", entity.getSurname());
        setTextToField("phone", entity.getPhone());
        setTextToField("email1", entity.getPrimaryEmail());
        setTextToField("email2", entity.getSecondaryEmail());
        setTextToField("city", entity.getCity());
        setTextToField("street", entity.getStreet());
        setTextToField("building", entity.getBuilding());
    }

    private void updateEntity(){
        LOGGER.finer(entity.toString());
        entity.setName(getTextFromField("name"));
        entity.setSurname(getTextFromField("surname"));
        entity.setPhone(getTextFromField("phone"));
        entity.setCity(getTextFromField("city"));
        entity.setStreet(getTextFromField("street"));
        entity.setBuilding(getTextFromField("building"));
        entity.setPrimaryEmail(getTextFromField("email1"));
        entity.setSecondaryEmail(getTextFromField("email2"));
        LOGGER.finer(entity.toString());
    }
}
