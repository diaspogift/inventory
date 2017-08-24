package com.dddtraining.inventory.application;

import com.dddtraining.inventory.application.command.RegisterProductArrivageCommand;
import com.dddtraining.inventory.domain.model.arrivage.Arrivage;
import com.dddtraining.inventory.domain.model.arrivage.ArrivageId;
import com.dddtraining.inventory.domain.model.arrivage.ArrivageRepository;
import com.dddtraining.inventory.domain.model.product.Product;
import com.dddtraining.inventory.domain.model.product.ProductId;
import com.dddtraining.inventory.domain.model.product.ProductRepository;
import com.dddtraining.inventory.domain.model.stock.Quantity;
import com.dddtraining.inventory.domain.model.stock.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ArrivageApplicationService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ArrivageRepository arrivageRepository;
    @Autowired
    private StockRepository stockRepository;


    public Arrivage arrivage(String andArrivageId){

        Arrivage arrivage =
                this.arrivageRepository()
                .arrivgeOfId(new ArrivageId(
                        andArrivageId
                ));

        return  arrivage;

    }

    public void decrementArrivageQuantityOf(String arrivageId, int quantity) {

        Arrivage arrivage =
                this.arrivageRepository()
                .arrivgeOfId(new ArrivageId(arrivageId));

        arrivage.decrementQuantityOf(quantity);
    }

    private ArrivageRepository arrivageRepository() {
        return this.arrivageRepository;
    }


}
