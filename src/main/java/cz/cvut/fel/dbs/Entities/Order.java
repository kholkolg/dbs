package cz.cvut.fel.dbs.Entities;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author Olga Kholkovskaia <olga.kholkovskaya@gmail.com>
 */
@Entity(name = "Order")
@Table(name = "demand")
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    //complex id consisting of customer id and timestamp
    //distance from coordinates, join with locations
    
    private OrderId id;
    
    private Customer customer;

    //private Calendar dateTime;
    
    private Location origin;
 
    private Location destination;
    

    @EmbeddedId
    public OrderId getId() {
        return id;
    }

    public void setId(OrderId id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="customer_id", insertable = false, updatable = false)
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    @OneToOne
    @JoinColumn(name = "start_osm_id", referencedColumnName="osm_id")
    public Location getOrigin() {
        return origin;
    }

    public void setOrigin(Location origin) {
        this.origin = origin;
    }
   
    @OneToOne
    @JoinColumn(name = "end_osm_id", referencedColumnName="osm_id")
    public Location getDestination() {
        return destination;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }

           
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("from: ").append(origin.getId()).append(" to: ").
            append(destination.getId()).append("\n").
            append("time: ").append(id.getDateTime().toString()).append("\n");
        //return "Order[ customer=" + customer.getFullName() + " ]";
        return sb.toString();
    }
    
}


        
//    @Temporal(javax.persistence.TemporalType.TIMESTAMP) // or Calendar
//    public Calendar getDateTime() {
//        return dateTime;
//    }
//
//    public void setDateTime(Calendar dateTime) {
//        this.dateTime = dateTime;
//    }