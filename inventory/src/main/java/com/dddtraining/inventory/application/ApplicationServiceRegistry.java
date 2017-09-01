package com.dddtraining.inventory.application;

import com.dddtraining.inventory.application.arrivage.ArrivageApplicationService;
import com.dddtraining.inventory.application.product.ProductApplicationService;
import com.dddtraining.inventory.application.stock.StockApplicationService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationServiceRegistry implements ApplicationContextAware{



    private static ApplicationContext applicationContext;



    public static ProductApplicationService productApplicationService(){

        return (ProductApplicationService) applicationContext.getBean(ProductApplicationService.class);
    }
    public static StockApplicationService stockApplicationService(){

        return (StockApplicationService) applicationContext.getBean(StockApplicationService.class);
    }
    public static ArrivageApplicationService arrivageApplicationService(){

        return (ArrivageApplicationService) applicationContext.getBean(ArrivageApplicationService.class);
    }

    @Override
    public synchronized void setApplicationContext(ApplicationContext anApplicationContext) throws BeansException {


        System.out.println(" \n In setApplicationContext anApplicationContext = "+anApplicationContext);

        if (ApplicationServiceRegistry.applicationContext == null) {
            ApplicationServiceRegistry.applicationContext = anApplicationContext;
        }

    }
}
