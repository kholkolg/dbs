package cz.cvut.fel.dbs.DAO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.List;

/**
 *
 * @author Olga Kholkovskaia <olga.kholkovskaya@gmail.com>
 * @param <K>
 * @param <E>
 */
public interface Dao<K, E> {
    
    Boolean persist(E entity);
    
    Boolean remove(E entity);
    
    Boolean refresh(E entity);
    
    Boolean merge(E entity);
    
    E findById(K id);
    
    Boolean contains(E entity);
    
    List<E> findAll();
}
