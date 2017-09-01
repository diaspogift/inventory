package com.dddtraining.inventory.port.adapter.messaging.ativemq;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.dddtraining.inventory.InventoryApplication;
import com.dddtraining.inventory.application.arrivage.ArrivageApplicationService;
import com.dddtraining.inventory.application.command.AssignedStockCommand;
import com.dddtraining.inventory.application.product.ProductApplicationService;
import com.dddtraining.inventory.domain.model.stock.StockQuantityChanged;
import com.dddtraining.inventory.domain.model.stock.StockRepository;

import javax.persistence.NoResultException;

@Component
public class StockEventListener {

	
    private static final Logger logger = LoggerFactory
            .getLogger(InventoryApplication.class);
    
    @Autowired
    private ProductApplicationService productApplicationService;
    
    @Autowired
    private ArrivageApplicationService arrivageApplicationService;
    
    @Autowired
    private StockRepository stockRepository;


    @JmsListener(destination = "STOCK_CREATED_QUEUE")
    public void handleStockCreatedEvent(Map<String, String> mesage) {




        String productId = mesage.get("productId");
        String stockId = mesage.get("stockId");
        String eventVersion = mesage.get("eventVersion");
        String occurredOn = mesage.get("occurredOn");

        System.out.println("\n\n In Stock Created Queue message before = "+mesage);



       this.productApplicationService()
                .assignedStockToProduct(
                        new AssignedStockCommand(
                                productId,
                                stockId
                        )
                );

        System.out.println("\n\n In Stock Created Queue message after = "+mesage);


    }
    
    @JmsListener(destination = "STOCK_THRESHOLD_REACHED_QUEUE")
    public void handleStockThresholdReachedEvent(Map<String, String> mesage) {


    	logger.debug("\n"+mesage.toString());


    }

    
    @JmsListener(destination = "STOCK_EMPTIED_QUEUE")
    public void handleStockEmptiedEvent(Map<String, String> mesage) {

    	logger.debug("\n"+mesage.toString());


    }

    
    
    @JmsListener(destination = "STOCK_QUANTITY_CHANGED_QUEUE")
    public void handleStockQuantityEvent(Map<String , String> mesage) {



        System.out.println("\n mesage in STOCK_QUANTITY_CHANGED_QUEUE = "+mesage);
    	System.out.println("\n mesage in STOCK_QUANTITY_CHANGED_QUEUE = "+mesage);
    	
    	List<JsonStockProductArrivageRep> jsonStockProductArrivageReps = new ArrayList<JsonStockProductArrivageRep>();
    	try {
			JSONArray jsonArray = new JSONArray(mesage.get("commande"));
			for(int i = 0; i< jsonArray.length(); i++){
				
				JSONObject object =  jsonArray.getJSONObject(i);

		    	JSONObject object2 = (JSONObject) object.get("changedArrivage");
		    	
				jsonStockProductArrivageReps.add(new JsonStockProductArrivageRep(object2.getString("arrivageId"), object2.getString("productId"),
						object2.getString("lifeSpanTimeStartDate"), object2.getString("lifeSpanTimeEndDate"), 
						object2.getInt("ordering"), object2.getInt("quantity")));
			}




            this.arrivageApplicationService.bulkUpdate(jsonStockProductArrivageReps);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
			
    
    	//TO DOOOO


    }



    public ProductApplicationService productApplicationService() {
        return productApplicationService;
    }

    public StockRepository stockRepository() {
        return this.stockRepository;
    }
}
