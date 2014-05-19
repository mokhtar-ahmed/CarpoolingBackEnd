/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mytest;

import dao.LocationDAO;
import java.util.Iterator;
import java.util.List;
import pojo.Location;

/**
 *
 * @author Mo5a
 */
public class locTest {
  
    public static void main(String[] args){
    
            LocationDAO locationDAO = new LocationDAO();
            List<Location> l =locationDAO.retrieveAllLocation();
            Iterator<Location> iterator =  l.iterator();
            System.out.println(l.size());
    }
}
