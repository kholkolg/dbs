package cz.cvut.fel.dbs.DAO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import cz.cvut.fel.dbs.Entities.Customer;
import cz.cvut.fel.dbs.Entities.Location;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Olga Kholkovskaia <olga.kholkovskaya@gmail.com>
 */
public class PersistenceService {
    private final String persistenceUnit = "slon";// "dbs4PU";
    
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private static final PersistenceService INSTANCE = new PersistenceService();

    private EntityManagerFactory emf;
    
    private final Map<String, JpaDao> daos;

    public static PersistenceService getInstance() {
        LOGGER.finer(" ");
        return INSTANCE;
    }
    
    public JpaDao getDao(Class entityClass){
        return daos.get(entityClass.getSimpleName());
    }

    public void closeEntityManagerFactory() {
        LOGGER.finer(" ");
        if (emf != null) {
            emf.close();
            emf = null;
        }
        LOGGER.finer("EMF closed");
    }
    
    private PersistenceService() {
        LOGGER.finer(" ");
        emf = Persistence.createEntityManagerFactory(persistenceUnit);
        daos = new HashMap<>();
        daos.put(Customer.class.getSimpleName(), new CustomerDao(emf));
        daos.put(Location.class.getSimpleName(), new LocationDao(emf));
        
    }
 
}
