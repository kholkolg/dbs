/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.dbs.gui;


import cz.cvut.fel.dbs.Entities.Location;
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
public class LocationFormController extends FormController<Location> {
    
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
    @FXML
    private TextField locationIdTextField;

    @FXML
    private TextField locationNameTextField;
    
    @FXML
    private TextField locationLongitudeTextField;
    
    @FXML
    private TextField locationLatitudeTextField;

    @FXML
    private Button locationSaveButton;
    
    @FXML
    private Button locationCancelButton;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        addFieldsToMap();
        addButtonToMap("save", locationSaveButton);
        addButtonToMap("cancel", locationCancelButton);
        //disable button then at least one of obligatory fields is unfield
        locationSaveButton.disableProperty().bind(getFieldBinding());
    }
    @Override
    public void setEntity(Location l){
        entity = l;
        LOGGER.finer(l.toString());
        fillFields();
        locationIdTextField.setDisable(true);
    }
    
    @Override
    public Location getEntity(){
        if(entity == null){
            entity = new Location();
        }
        updateEntity();
        LOGGER.finer(entity.toString());
        return entity;
    }
    
    @Override
    public void clearForm(){
        super.clearForm();
        locationIdTextField.setDisable(false);
    }
    
     private BooleanBinding getFieldBinding(){
        BooleanBinding bb = new BooleanBinding() {
            {super.bind(getFieldProperty("id"),
                        getFieldProperty("longitude"),
                        getFieldProperty("latitude"));        
            }
            @Override
            protected boolean computeValue() {
                return (getTextFromField("id").isEmpty()
                    || getTextFromField("longitude").isEmpty()
                    || getTextFromField("latitude").isEmpty());
            }
        };
       return bb;
    }
     
    private void addFieldsToMap(){
        addFieldToMap("id", locationIdTextField);
        addFieldToMap("name", locationNameTextField);
        addFieldToMap("longitude", locationLongitudeTextField);
        addFieldToMap("latitude", locationLatitudeTextField);
    }
            
    private void fillFields(){
        setTextToField("id", entity.getId().toString());
        setTextToField("name", entity.getName());
        setTextToField("longitude", entity.getLongitude().toString());
        setTextToField("latitude", entity.getLatitude().toString());

    }

    private void updateEntity(){
        LOGGER.finer(entity.toString());
        entity.setName(getTextFromField("name"));
        try{
            entity.setId(Long.parseLong(getTextFromField("id")));
            entity.setLongitude(Double.parseDouble(getTextFromField("longitude")));
            entity.setLatitude(Double.parseDouble(getTextFromField("latitude")));
        }catch(Exception ex){
            LOGGER.severe(ex.toString());
        }
        LOGGER.finer(entity.toString());
    }
    
}
