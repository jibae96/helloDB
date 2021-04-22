package org.example.csemall;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class MainApp {
    public static void main(String[] args) {

        // create spring container
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:beans.xml");

        // beans의 offerDao 가져오기
        OfferDao offerDao = (OfferDao) context.getBean("offerDao");

        // dao의 method 호출
        System.out.println("The record count is "+offerDao.getRowCount());

        List<Offer> offerList = offerDao.getOffers();

        for(Offer offer : offerList){
            System.out.println(offer);
        }

        Offer offer = new Offer();
        offer.setName("trudy");
        offer.setEmail("trudy@hansung.ac.kr");
        offer.setText("instructor of spring framework");

        if(offerDao.insert(offer)){
            System.out.println("object is inserted successfully");
        }else
            System.out.println("object insert failed");

        offer = offerDao.getOffer("trudy");
        offer.setName("Jungin");
        if(offerDao.update(offer)){
            System.out.println("Object is updated successfully");
        }else
            System.out.println("Object update failed");

        offer = offerDao.getOffer("Jungin");
        System.out.println(offer);
        if(offerDao.delete(offer.getId())){
            System.out.println("object is deleted successfully");
        }else
            System.out.println("object delete failed");

        // close spring container
        context.close();
    }
}
