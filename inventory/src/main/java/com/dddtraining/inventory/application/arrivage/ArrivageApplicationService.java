package com.dddtraining.inventory.application.arrivage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dddtraining.inventory.InventoryApplication;
import com.dddtraining.inventory.application.command.ChangeArrivageQuantityCommand;
import com.dddtraining.inventory.domain.model.arrivage.Arrivage;
import com.dddtraining.inventory.domain.model.arrivage.ArrivageId;
import com.dddtraining.inventory.domain.model.arrivage.ArrivageQuantityChanged;
import com.dddtraining.inventory.domain.model.arrivage.ArrivageRepository;
import com.dddtraining.inventory.domain.model.common.DomainEventPublisher;
import com.dddtraining.inventory.domain.model.common.DomainEventSubscriber;
import com.dddtraining.inventory.domain.model.product.ProductRepository;
import com.dddtraining.inventory.domain.model.stock.StockRepository;
import com.dddtraining.inventory.port.adapter.messaging.ativemq.JsonStockProductArrivageRep;

@Service
public class ArrivageApplicationService {
	
    private static final Logger logger = LoggerFactory
            .getLogger(InventoryApplication.class);

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ArrivageRepository arrivageRepository;
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    JmsTemplate jmsTemplate;


    public Arrivage arrivage(String andArrivageId) {

        Arrivage arrivage =
                this.arrivageRepository()
                        .arrivgeOfId(new ArrivageId(
                                andArrivageId
                        ));

        return arrivage;

    }

    @Transactional
    public void changeArrivageQuantity(ChangeArrivageQuantityCommand aCommand) {
    	
    	DomainEventSubscriber<ArrivageQuantityChanged> subscriber =
    			new DomainEventSubscriber<ArrivageQuantityChanged>() {

					@Override
					public void handleEvent(ArrivageQuantityChanged aDomainEvent) {
						
				
						Map<String, String> message = new HashMap<String, String>();
						
						message.put("arrivageId", aDomainEvent.arrivageId().id());
						message.put("stockId", aDomainEvent.stockId().id());
						message.put("quantity", String.valueOf(aDomainEvent.quantity().value()));
						
						jmsTemplate.convertAndSend("ARRIVAGE_QUANTITY_CHANGED_QUEUE", message);
						
					}

					@Override
					public Class<ArrivageQuantityChanged> subscribedToEventType() {
						return ArrivageQuantityChanged.class;
					}
    		
    		
				};

		DomainEventPublisher.instance().subscribe(subscriber);
		
		Arrivage arrivage =
			                this.arrivageRepository()
			                        .arrivgeOfId(new ArrivageId(aCommand.arrivageId()));

        arrivage.changeQuantity(aCommand.quantity());
    }



    private ArrivageRepository arrivageRepository() {
        return this.arrivageRepository;
    }
    
    //TO TEST

    @Transactional
	public void bulkUpdate(List<JsonStockProductArrivageRep> jsonStockProductArrivageReps) {

    	for(JsonStockProductArrivageRep next : jsonStockProductArrivageReps){
    		
    		Arrivage arrivage = this.arrivageRepository.arrivgeOfId(new ArrivageId(next.getArrivageId()));
    		
    		arrivage.udpdateArrivageQuantity(next.getQuantity());
    		
    	}
		
	}


}
