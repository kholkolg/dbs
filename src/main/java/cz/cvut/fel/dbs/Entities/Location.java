package cz.cvut.fel.dbs.Entities;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import cz.cvut.fel.dbs.gui.DataValidationAlert;
import java.io.Serializable;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.persistence.Transient;

import static cz.cvut.fel.dbs.utils.Regex.isNameValid;
/**
 *
 * @author Olga Kholkovskaia <olga.kholkovskaya@gmail.com>
 * has station, station parameters, 
 * 
 */
@Entity(name= "Location")
@Table(name= "location")
//@SecondaryTable(name = "station")
@NamedQuery(name = "Location.findAll", query= "select l from Location l") //@NamedQueries((@NamedQuery...),.. )
public class Location implements Serializable {
    
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
    private static final long serialVersionUID = 1L;

    private Long id;
    
    private Double longitude;
    
    private Double latitude;
    
    private String name;

    @Id
    @Column(name = "osm_id", insertable = true, updatable = false, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column( nullable = false)
    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double lon) {
        this.longitude = lon;
    }
    
    @Column(nullable = false)
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double lat) {
        this.latitude = lat;
    }

    @Column(name = "location_name", nullable = true)  
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Transient
    public String getCoordinates(){
        return longitude.toString() +", "+ latitude.toString();
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
        if (!(object instanceof Location)) {
            return false;
        }
        Location other = (Location) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
        sb.append(":\n").append("longitude: ").append(longitude).append("\n").
            append("latitude: ").append(latitude).append("\n");
        if(name != null){
            sb.append("name: ").append(name).append("\n");
        }
        return sb.toString();
    }
    
}
