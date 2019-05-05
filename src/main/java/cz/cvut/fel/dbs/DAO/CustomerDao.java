package cz.cvut.fel.dbs.DAO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import cz.cvut.fel.dbs.Entities.Customer;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Olga Kholkovskaia <olga.kholkovskaya@gmail.com>
 */
public class CustomerDao extends JpaDao<Long, Customer>{
    
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
//    public CustomerDao(){
//        super();
////        LOGGER.finer(" ");
////        entityManager = PersistenceService.getInstance().
////            getEntityManagerFactory().createEntityManager();
//    }
    
    public CustomerDao(EntityManagerFactory emf){
        super();
        LOGGER.finer(" ");
        entityManager = emf.createEntityManager();
    }
    
}
