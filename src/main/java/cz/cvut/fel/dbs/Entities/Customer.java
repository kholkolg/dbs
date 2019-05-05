package cz.cvut.fel.dbs.Entities;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import cz.cvut.fel.dbs.gui.DataValidationAlert;
import java.io.Serializable;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import static cz.cvut.fel.dbs.utils.Regex.isEmailValid;
import static cz.cvut.fel.dbs.utils.Regex.isNameValid;
import static cz.cvut.fel.dbs.utils.Regex.isPhoneValid;
import java.util.HashSet;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

/**
 *
 * @author Olga Kholkovskaia <olga.kholkovskaya@gmail.com>
 * find active orders
 * find emails, add email through customer without email class?
 * address from three columns
 * 
 * 
 */
@Entity(name= "Customer")
@Table(name = "customer")
@NamedQuery(name = "Customer.findAll", query= "select c from Customer c") //@NamedQueries((@NamedQuery...),.. )
public class Customer implements Serializable {
    
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String surname;

    private String phone;

    private String city;

    private String street;

    private String building;

    private String primaryEmail;

    private String secondaryEmail;
    
    private Set<Order> orders;
     

    public Customer() {

        LOGGER.finer("new empty customer");
    }

    public Customer(Long id, String name, String surname, String phoneNumber, String city,
        String street, String building, String email1, String email2) {
        this.id = id;
        this.name = name;
        this.phone = phoneNumber;
        this.city = city;
        this.street = street;
        this.building = building;
        this.primaryEmail = email1;
        this.secondaryEmail = email2;
        this.orders = new HashSet<>();
        LOGGER.log(Level.FINER, "new customer {0}", this);
    }
    
        
    @Id  
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen_sequence")
	@SequenceGenerator(name="seq_gen_sequence", sequenceName = "customer_id_seq" ,allocationSize = 1)
    @Column(name = "customer_id", updatable = false, nullable = false)
    public Long getId() {
        LOGGER.log(Level.FINER, "id={0}", id);
        return id;
    }

    public void setId(Long id) {
        LOGGER.log(Level.FINER, "new id={0}", id);
        this.id = id;
    }

    @Column(name = "name1", nullable = false)
    public String getName() {
        LOGGER.log(Level.FINER, "name={0}", name);
        return name;
    }
    
    public void setName(String name) {
        if(isNameValid(name)){
            this.name = name;
            LOGGER.log(Level.FINER, "new name={0}", name);
        }else{
            DataValidationAlert alert = new DataValidationAlert(this.getClass(), "name", name);
        }
    }
    
    @Column(name = "name2", nullable = false)
    public String getSurname() {
        LOGGER.log(Level.FINER, "name={0}", surname);
        return surname;
    }
    
    public void setSurname(String surname) {
        if(isNameValid(surname)){
            this.surname = surname;
            LOGGER.log(Level.FINER, "new name={0}", surname);
        }else{
            DataValidationAlert alert = new DataValidationAlert(this.getClass(), "surname", surname);
        }
    }
    
    @Column(name = "phone_number", nullable = false, unique = true)
    public String getPhone() {
        LOGGER.log(Level.FINER, "phone={0}", phone);
        return phone;
    }

    public void setPhone(String phone) {
        if (isPhoneValid(phone)){
	        String clean = phone.replaceAll("[^\\d]", "" );
            if(clean.length() >= 9 && clean.length() <= 12){
                this.phone = String.format("%012d" , Long.parseLong(clean));
            }
	    }else{
            DataValidationAlert alert = new DataValidationAlert(this.getClass(), "phone number", name);
        }
    }

    @Column(name="email1", nullable = false)
    public String getPrimaryEmail() {
        return primaryEmail;
    }
    
    public void setPrimaryEmail(String email) {
        if (isEmailValid(email)){
            this.primaryEmail = email;
 	    }else{
            DataValidationAlert alert = new DataValidationAlert(this.getClass(), "primary_email", email);
        }
    }

    @Column(name="email2", nullable = true)
    public String getSecondaryEmail() {
        return secondaryEmail;
    }

    public void setSecondaryEmail(String email) {
        if (isEmailValid(email)){
            this.secondaryEmail = email;
 	    }else{
            DataValidationAlert alert = new DataValidationAlert(this.getClass(), "secondary_email", email);
        }
    }
      
    @Column(nullable = false)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        if(isNameValid(city)){
            this.city = city;
        }else{
            new DataValidationAlert(this.getClass(), "city", city);
        }
    }
    
    @Column(nullable = false)
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
    
    @Column(nullable = false)
    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }
    
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<Order> getOrders(){
        return orders;
    }
    
    public void setOrders(Set<Order> orders){
        this.orders = orders;        
    }    
    
    public void addOrder(Order order){
        order.setCustomer(this);
        this.orders.add(order);
    }
    
    public void removeOrder(Order order){
        this.orders.remove(order);
        order.setCustomer(null);
    }
    
    @Transient
    public Integer getNumOrders(){
        return this.orders.size();
    }
    
    @Transient
    public String getAddress() {
        return street + " " + building+ ", " + city;
    }
    
    @Transient
    public String getFullName() {
        return surname + ", " + name;
    }
    
    @Transient
    public String getStatistics(){
        StringBuilder sb = new StringBuilder(this.toString());
        sb.append("\n   Number of orders: ").append(getNumOrders()).append("\n");
        orders.forEach((order) -> {
            sb.append(order.toString()).append("\n");
        });      
        return sb.toString();
    }
        
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(name).append("\n").
            append("Surname: ").append(surname).append("\n").
            append("City: ").append(city).append("\n").
            append("Address: ").append(street).append(", ").
            append(building).append("\n").
            append("Phone: ").append(phone).append("\n").
            append("Primary email: ").append(primaryEmail).append("\n");
        if(secondaryEmail != null){
           sb.append("Secondary email: ").append(secondaryEmail).append("\n");
        }
        return sb.toString();
    }
 
}
