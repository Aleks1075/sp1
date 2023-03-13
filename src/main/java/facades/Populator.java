///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package facades;
//
//import dtos.PersonDTO;
//import entities.*;
//
//import javax.persistence.EntityManagerFactory;
//
//import utils.EMF_Creator;
//
///**
// *
// * @author tha
// */
//public class Populator {
//    public static void populate(){
//        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
//        PersonFacade fe = PersonFacade.getFacadeExample(emf);
//        Person p = new Person("tobias@test.dk", "Tobias", "Hansen", 20);
//        Phone ph = new Phone("23232323", "Home");
//        p.addPhone(ph);
//        Hobby h = new Hobby("Movies", "https://da.wikipedia.org/wiki/Movies", "Entertainment", "Freetime");
//        p.addHobby(h);
//        CityInfo c = new CityInfo(2100, "København Ø");
//        Address a = new Address("Hovedgaden 1", "Hovedby", "test", c);
//        p.setAddressStreet(a);
//
//        PersonDTO pdto = new PersonDTO(p);
//        fe.createPerson(pdto);
//    }
//
//    public static void main(String[] args) {
//        populate();
//    }
//}
