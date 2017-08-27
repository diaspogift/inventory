package com.dddtraining.inventory.application.arrivage;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dddtraining.inventory.application.command.ChangeArrivageQuantityCommand;
import com.dddtraining.inventory.domain.model.arrivage.Arrivage;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ArrivageApplicationServiceTest {

	
	@Autowired
	private  ArrivageApplicationService arrivageApplicationService;
	
	
	

    @Test
    public void testChangeArrivageQuantity() {
    	
    	
    	ChangeArrivageQuantityCommand changeArrivageQuantityCommand = 
    			new ChangeArrivageQuantityCommand(
    					"ARR12349",
    					10);
    	
    	Arrivage arrivage = 
    			this.arrivageApplicationService()
    			.arrivage("ARR12349");
    	
    	assertEquals(5000, arrivage.quantity().value());
    	
    	this.arrivageApplicationService()
    	.changeArrivageQuantity(changeArrivageQuantityCommand);
    	
    	
    	 arrivage = 
    			this.arrivageApplicationService()
    			.arrivage("ARR12349");
    	
    	assertEquals(10, arrivage.quantity().value());
    	
    	

    }



	public ArrivageApplicationService arrivageApplicationService() {
		return this.arrivageApplicationService;
	}

    

}