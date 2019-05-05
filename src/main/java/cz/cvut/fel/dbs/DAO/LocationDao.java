package cz.cvut.fel.dbs.DAO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import cz.cvut.fel.dbs.Entities.Location;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Olga Kholkovskaia <olga.kholkovskaya@gmail.com>
 */
public class LocationDao extends JpaDao<Long, Location>{

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
//    public LocationDao(){
//        super();
//        LOGGER.finer(" ");
//        entityManager = PersistenceService.getInstance().
//            getEntityManagerFactory().createEntityManager();
//    }
    
    public LocationDao(EntityManagerFactory emf){
        super();
        LOGGER.finer(" ");
        entityManager = emf.createEntityManager();
    }
}
