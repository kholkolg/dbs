package cz.cvut.fel.dbs.DAO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import cz.cvut.fel.dbs.gui.ExceptionAlert;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Olga Kholkovskaia <olga.kholkovskaya@gmail.com>
 * @param <K> id type
 * @param <E> entity class
 */
public abstract class JpaDao<K, E> implements Dao<K, E> {
    
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
    protected Class<E> entityClass;
    
    @PersistenceContext
    protected EntityManager entityManager;
    
    public JpaDao() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[1];
        LOGGER.log(Level.FINER, "class {0}", this.entityClass);
  }
    
    @Override
    public Boolean persist(E entity){
        LOGGER.finer(entity.toString());
        try{
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
            entityManager.flush();
            entityManager.getTransaction().commit();
            LOGGER.log(Level.FINER, "Transaction commited");
            return true;
        }catch (Exception ex){
           entityManager.getTransaction().rollback();
           LOGGER.log(Level.SEVERE, "Transaction rolled back: {0}", ex.toString());
           ExceptionAlert alert = new ExceptionAlert(ex, "create", entity);
           return false;
        }
    }
    
    @Override
    public Boolean refresh(E entity){
        LOGGER.finer(entity.toString());
       
        try{
            entityManager.getTransaction().begin();
            entityManager.refresh(entity);
            //entityManager.getTransaction().commit();
            //LOGGER.log(Level.FINER, "Transaction commited");
            return true;
        }catch (Exception ex){
           entityManager.getTransaction().rollback();
           LOGGER.log(Level.SEVERE, "Transaction rolled back: {0}", ex.toString());
           ExceptionAlert alert = new ExceptionAlert(ex, "refresh", entity) ;
           return false;
        }
    }
    
    @Override
    public Boolean merge(E entity){
        LOGGER.finer(entity.toString());
       try{
            entityManager.getTransaction().begin();
            entityManager.merge(entity);
            entityManager.getTransaction().commit();
            LOGGER.log(Level.FINER, "Merged {0}", entity.toString());
            return true;
        }catch (Exception ex){
            entityManager.getTransaction().rollback();
            LOGGER.log(Level.SEVERE, "Transaction rolled back: {0}", ex.toString());
            ExceptionAlert alert = new ExceptionAlert(ex, "merge", entity);
           return false;
        }
        
    }
    
    @Override
    public Boolean remove(E entity){
        LOGGER.finer(entity.toString());
        try{
            entityManager.getTransaction().begin();
            entityManager.remove(entity);
            entityManager.getTransaction().commit();
            LOGGER.log(Level.FINER, "Transaction commited");
            return true;
        }catch (Exception ex){
           entityManager.getTransaction().rollback();
           LOGGER.log(Level.SEVERE, "Transaction rolled back: {0}", ex.toString());
           ExceptionAlert alert = new ExceptionAlert(ex, "remove", entity);
           return false;
        }
    }
        
       
    @Override
    public E findById(K id){
        LOGGER.finer(id.toString());
        return entityManager.find(getEntityClass(), id);
    }
    
    protected Class<E> getEntityClass(){
        LOGGER.finer(" ");
        return this.entityClass;
    }
    
    @Override
    public List<E> findAll(){
        LOGGER.finer(" ");
        List<E> entities = new ArrayList<>();
        try{
            entities = entityManager.createNamedQuery(entityClass.getSimpleName()+".findAll").getResultList();
            LOGGER.log(Level.FINER, "Named query result size ", entities.size());
            return entities;
        }catch (Exception ex){
           LOGGER.log(Level.SEVERE, "DB error: {0}", ex.toString());
           ExceptionAlert alert = new ExceptionAlert(ex, "find all", this.entityClass);
        }
        return entities;
     }
    
    @Override
    public Boolean contains(E entity){
        return entityManager.contains(entity);
    }
}
