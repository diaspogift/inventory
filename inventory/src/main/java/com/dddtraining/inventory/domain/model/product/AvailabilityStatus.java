package com.dddtraining.inventory.domain.model.product;

public enum AvailabilityStatus {
	
	CREATED {
		public boolean isProductCreated(){
			return true;
		}
	},
	
	STOCK_PROVIDED{
		public boolean isStockProvided(){
			return true;
		}
		
	},
	
	THRESHOLD_REACHED{
		public boolean isThresholdReached(){
			return true;
		}
		
	},
	
	STOCK_CLEARED{
		public boolean isStockCleared(){
			return true;
		}
		
	}

}
