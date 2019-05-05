package cz.cvut.fel.dbs;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import cz.cvut.fel.dbs.DAO.PersistenceService;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import dbs4.logger.MyLogger;

/**
 *
 * @author olga
 */
public class MainApp extends Application {
       
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
    
    
    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));
        LOGGER.log(Level.FINER, "fxml root loaded{0}", root.toString());
        Scene scene = new Scene(root);
        //scene.getStylesheets().add("/styles/Styles.css");
        
        primaryStage.setTitle("FXML JPA 1");
        LOGGER.finer("Setting scene.");
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }
 
    @Override
    public void stop(){
        LOGGER.finer("Stopping emf and exiting.");
        PersistenceService ps = PersistenceService.getInstance();
        ps.closeEntityManagerFactory();
}
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        MyLogger.setup();
        launch(args);
    }
    
}


