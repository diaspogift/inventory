package com.dddtraining.inventory.domain.model;

import com.dddtraining.inventory.domain.model.arrivage.ArrivageRepository;
import com.dddtraining.inventory.domain.model.product.ProductRepository;
import com.dddtraining.inventory.domain.model.stock.StockRepository;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;


@Component
public class DomainRegistry implements ApplicationContextAware {


    private static ApplicationContext applicationContext;


    public static ProductRepository productRepository() {
        return (ProductRepository) applicationContext.getBean(ProductRepository.class);
    }


    public static ArrivageRepository arrivageRepository() {
        return (ArrivageRepository) applicationContext.getBean(ArrivageRepository.class);
    }



    public static StockRepository stockRepository() {
        return (StockRepository ) applicationContext.getBean(StockRepository.class);
    }


    public static EntityManagerFactory entityManagerFactory() {
        return (EntityManagerFactory ) applicationContext.getBean(EntityManagerFactory.class);
    }




    @Override
    public synchronized void setApplicationContext(ApplicationContext anApplicationContext) throws BeansException {

        System.out.println("\n In setApplicationContext and applicationContext =  "+anApplicationContext);

        if (DomainRegistry.applicationContext == null) {


            DomainRegistry.applicationContext = anApplicationContext;


        }
    }
}