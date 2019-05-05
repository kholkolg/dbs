package dbs4.logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MyLogger {
    static private FileHandler fileTxt;
    static private SimpleFormatter formatterTxt;

    static private FileHandler fileHTML;
    static private Formatter formatterHTML;

    static public void setup() {
           // get the global logger to configure it
            Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
           // suppress the logging output to the console
            Logger rootLogger = Logger.getLogger("");
            Handler[] handlers = rootLogger.getHandlers();
            if (handlers[0] instanceof ConsoleHandler) {
                rootLogger.removeHandler(handlers[0]);
            }
            //formatterTxt = new SimpleFormatter();
            formatterHTML = new HTMLFormatter();
            logger.setLevel(Level.ALL);
            try {
                fileHTML = new FileHandler("Logging.html");
                fileHTML.setFormatter(formatterHTML);
                logger.addHandler(fileHTML);
//                fileTxt = new FileHandler("Logging.txt");
//                fileTxt.setFormatter(formatterTxt);
//                logger.addHandler(fileTxt);
        } catch (IOException|SecurityException ex) {
           logger.severe(ex.toString());
        }
    }
}