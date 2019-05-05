package cz.cvut.fel.dbs.gui;


import cz.cvut.fel.dbs.DAO.JpaDao;
import cz.cvut.fel.dbs.DAO.PersistenceService;
import cz.cvut.fel.dbs.Entities.Customer;
import cz.cvut.fel.dbs.Entities.Location;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class SceneController implements Initializable{
    
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
    private Map<Class, Map<String, Controller>> contrlMap;
    
    private Map<Class,  Scene> sceneMap;
    
    private Map<Integer, Class> classMap;
    
    private ObservableList data;
    
    private FXMLLoader loader;
    
    private Class activeClass;
      
    @FXML
    private TextArea leftTextArea;
    
    @FXML
    private Label leftLabel;
    
    @FXML
    private TabPane tabPane;
    
    //buttons
    @FXML
    private Button button1;
    
    @FXML
    private Button button2;
    
    @FXML
    private Button createButton;

    @FXML
    private Button editButton;
 
    @FXML
    private Button deleteButton;

    @FXML
    private Button searchButton;

    @FXML
    protected TextArea lowerTextArea;
      
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LOGGER.finer("FXML controller init");
        leftTextArea.appendText("main controller\n");
        leftLabel.setText(this.getClass().getCanonicalName()); 
        
        sceneMap = new HashMap<>();
        contrlMap = new HashMap<>();
        classMap = new HashMap<>();
        contrlMap.put(Customer.class, new HashMap<>());
        contrlMap.put(Location.class, new HashMap<>());

        initButtons();   
        initTab(Customer.class);
        initTab(Location.class);
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        tabPane.getSelectionModel().selectedItemProperty().
            addListener((ObservableValue<? extends Tab> ov, Tab t, Tab t1) -> {
            LOGGER.finer("Tab Selection changed");
            activeClass = classMap.get(tabPane.getSelectionModel().getSelectedIndex());
            switchData();
        });
        initForm(Customer.class);
        initForm(Location.class);
        initButtons();
        activeClass = Customer.class;
        //start with customer pane
        tabPane.getSelectionModel().select(0);
        data = ((ViewController) contrlMap.get(Customer.class).
            get("view")).getData();
        switchData();
    }    
    
    private void initTab(Class entityClass){
        String url = String.format("/fxml/%sView.fxml", entityClass.getSimpleName());
        loader = new FXMLLoader(getClass().getResource(url));
        try {
            VBox vb = (VBox) loader.load();
            contrlMap.get(entityClass).put("view", loader.getController());
            tabPane.getTabs().add(new Tab(entityClass.getSimpleName(), vb));
            classMap.put(classMap.size(), entityClass);
        } catch (Exception ex) {
            LOGGER.severe(ex.toString());
        }
    }
    
    private void initForm(Class entityClass){
        String url = String.format("/fxml/%sForm.fxml", entityClass.getSimpleName());
        //System.out.println(url);
        loader = new FXMLLoader(getClass().getResource(url));
         try {
            sceneMap.put(entityClass, new Scene((Parent) loader.load()));
        } catch (IOException ex) {
            LOGGER.severe(ex.toString());
            return;
        }
        FormController fc = (FormController) loader.getController();
        contrlMap.get(entityClass).put("form", fc);
       
        fc.getButton("save").setOnAction((event) -> {
            //if (showConfirmationDialog("save", entityClass)== ButtonType.YES) {
                addEntityFromForm(entityClass, event);
            //}
        });
        
        fc.getButton("cancel").setOnAction((event) -> {
            LOGGER.finer("customer form cancel btn pressed");
            closeSecondaryScene(entityClass, event);
            lowerTextArea.appendText(((Button)event.getSource()).getParent().toString());
        });
    }
     
    private void initButtons(){
        button1.setOnAction(this::button1Handler);
        button2.setOnAction(this::button2Handler);
        searchButton.setOnAction(this::searchButtonHandler);
        createButton.setOnAction(this::newButtonHandler);
        editButton.setOnAction(this::editButtonHandler);
        deleteButton.setOnAction(this::deleteButtonHandler);
    }
    
    private void newButtonHandler(ActionEvent event){

        ((FormController)getCtrl("form")).setIsNew(true);
        LOGGER.finer("create new btn pressed");
        lowerTextArea.appendText(" \"CREATE\" " + event.getTarget() + " \n");
        lowerTextArea.appendText(event.getEventType() + " \n");
        Stage inputStage = new Stage();
        inputStage.initOwner(((Button)event.getSource()).getScene().getWindow());
        lowerTextArea.appendText(activeClass.getSimpleName());
        inputStage.setScene(sceneMap.get(activeClass));
        inputStage.show();
        disableWindowButtons(true);
    }
    
    private void deleteButtonHandler(ActionEvent event){
        LOGGER.finer("delete btn pressed");
        lowerTextArea.appendText(" \"DELETE\" " + event.getTarget() + " \n");
        ViewController vc = (ViewController) getCtrl("view");
        Object entity = activeClass.cast(vc.getTable().getSelectionModel().getSelectedItem());
        if(entity != null) {
            LOGGER.finer(entity.toString());
            if (showConfirmationDialog("delete", entity) == ButtonType.YES) {
                if(PersistenceService.getInstance().getDao(entity.getClass()).remove(entity)){
                    data.remove(entity);
                    vc.updateTable();
                }
            }
        }
    }
    
    //TODO move to exception
    private Object showConfirmationDialog(String operation, Object entity){
        Alert alert = new Alert(AlertType.CONFIRMATION, null,
            ButtonType.YES, ButtonType.NO);
        alert.setGraphic(null);
        alert.setContentText(entity.toString());
        alert.setHeaderText(operation +" ?");
        alert.showAndWait();
        return alert.getResult();
    }
    
    private void editButtonHandler(ActionEvent event){
        //open edit window
        LOGGER.finer("edit btn pressed ");
        lowerTextArea.appendText("\"EDIT\" " + event.getTarget() + " \n");
        
        ViewController vc = (ViewController)getCtrl("view");
        FormController fc = (FormController)getCtrl("form");
        Object entity = vc.getTable().getSelectionModel().getSelectedItem();

        if(entity != null) {
            LOGGER.finer(entity.toString());
            try {
                lowerTextArea.appendText(loader.getLocation().toString());
                fc.setEntity(entity);
                fc.setIsNew(false);
            } catch (Exception ex) {
                LOGGER.severe(ex.toString());
                return;
            }
            Stage inputStage = new Stage();
            inputStage.initOwner(((Button)event.getSource()).getScene().getWindow());
            inputStage.setScene(sceneMap.get(activeClass));
            disableWindowButtons(true);
            inputStage.show();
        }
    }
    //TODO unused buttons
    private void  button1Handler(ActionEvent event) {
        LOGGER.finer("btn1 pressed ");
        lowerTextArea.appendText(" \"BUTTON1\" , tab=" +", " + event.getTarget() + 
            " "+ activeClass.getSimpleName() +" \n");
    }
    
    private void  button2Handler(ActionEvent event) {
        LOGGER.finer("btn2 pressed ");
        lowerTextArea.appendText(" \"BUTTON2\" , tab=" +", " + event.getTarget() + 
            " "+ activeClass.getSimpleName() +" \n");
    }
    
    private void searchButtonHandler(ActionEvent event){
        LOGGER.finer("search all btn pressed ");
        lowerTextArea.appendText(" \"SEARCH\" " + event.getTarget() + 
            " "+ activeClass.getSimpleName() +" \n");
    }
     
    private void switchData() {
        data.clear();
        LOGGER.finer(" ");
        ViewController vc = (ViewController)getCtrl("view");
        JpaDao dao = PersistenceService.getInstance().getDao(activeClass);
        data = vc.getData();
        List el = dao.findAll();
        if (el != null) {
            data.addAll(el);
            LOGGER.log(Level.FINER, "Named query result size {} ", el.size());
        } 
        LOGGER.log(Level.FINER, "Named query result is empty ");
        vc.updateTable();
    }
        
    protected void disableWindowButtons(Boolean value){
        createButton.setDisable(value);
        editButton.setDisable(value);
        deleteButton.setDisable(value);
        searchButton.setDisable(value);
    }
        
    private void addEntityFromForm(Class entityClass, ActionEvent event){
        lowerTextArea.appendText(" \"SAVE\" " + event.getTarget() + " \n");
        
        Object entity = ((FormController)getCtrl("form")).getEntity();
        if(entity == null){
            LOGGER.severe("null entity returned from form");
            return;
        }
        LOGGER.finer(entity.toString());
        if (showConfirmationDialog("Save", entity)== ButtonType.YES) {
            if(((FormController) getCtrl("form")).getIsNew()){
                if(PersistenceService.getInstance().getDao(entity.getClass()).persist(entity)){
                    data.add(entity);
                    ((ViewController)getCtrl( "view")).updateTable();
                    closeSecondaryScene(entityClass, event);
                }
            }else{
                if(PersistenceService.getInstance().getDao(entity.getClass()).merge(entity)){
                    ((ViewController)getCtrl("view")).updateTable();
                    closeSecondaryScene(entityClass, event);
                }
            }
        }
    }
    
    private void closeSecondaryScene(Class entityClass, ActionEvent event){
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        disableWindowButtons(false);
        ((FormController)getCtrl("form")).clearForm();
    }
            
    private Controller getCtrl(String type){
        return contrlMap.get(activeClass).get(type);
    }
 
}


