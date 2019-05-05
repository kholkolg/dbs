package cz.cvut.fel.dbs.Entities;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;

/**
 *
 * @author Olga Kholkovskaia <olga.kholkovskaya@gmail.com>
 */
@Embeddable
public class OrderId implements Serializable {
 
    private Long customerId;
     
    private Calendar dateTime;

    public OrderId() {
    }
 
    public OrderId(Long customerId, Calendar dateTime) {
        this.customerId = customerId;
        this.dateTime = dateTime;
    }
 
    @Column(name = "customer_id")
    public Long getCustomerId() {
        return customerId;
    }
    
    @Column(name = "demand_ts")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Calendar getDateTime() {
        return dateTime;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setDateTime(Calendar dateTime) {
        this.dateTime = dateTime;
    }
    
    
     @Override
    public boolean equals(Object o) {
        if(o == null) return false;
        if (this == o) return true;
        if (!(o instanceof OrderId)) return false;
        OrderId that = (OrderId) o;
        return Objects.equals(getCustomerId(), that.getCustomerId()) &&
                Objects.equals(getDateTime(), that.getDateTime());
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(getCustomerId(), getDateTime());
    }
}