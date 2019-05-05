/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.dbs.utils;

import java.util.regex.Pattern;

/**
 *
 * @author Olga Kholkovskaia <olga.kholkovskaya@gmail.com>
 */
public class Regex {
    static Pattern namePattern = Pattern.compile("[A-Za-z]+([\\s-'][A-Za-z][a-z]+)?");
    static Pattern emailPattern = Pattern.compile("[a-z][\\w\\.-]*['a-z0-9]+@[a-z0-9]\\w*[a-z0-9']\\.[a-z]{2,3}"); //[a-z][\\w\\.-]*[a-z0-9]@[a-z0-9]\\w*[a-z0-9']\\.[a-z]{2,3}");
    static Pattern phonePattern = Pattern.compile("\\+?\\(?[\\d]{1,3}\\)?[\\s-]?[\\d-\\s]{9,12}");
    
   
    public static Boolean isNameValid(String str){
        
        return str == null || namePattern.matcher(str).matches();
    }
    
    public static Boolean isEmailValid(String str){
        return str == null || emailPattern.matcher(str).matches();
    }
    
    public static Boolean isPhoneValid(String str){
        return  str == null || phonePattern.matcher(str).matches();
    }
    
}
