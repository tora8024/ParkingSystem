package com.tora.carpark.job;

import java.util.Date;

import com.tora.carpark.CarPark;
import com.tora.carpark.eunm.StatusEunm;
import com.tora.carpark.eunm.TransportEunm;
import com.tora.carpark.space.ParkingSpace;
import com.tora.carpark.tx.TxRecord;

public class ParkingJob extends Job {

	public ParkingJob(CarPark cp) {
		super(StatusEunm.Parking , cp, 2);
	}
	

	@Override
	public void jobHandler(){
		try {
			Thread.yield();
			synchronized (cp) { 
				
	        	TransportEunm randomTransport =getRandomTransport();
	        	ParkingSpace availableParkingSlot = cp.getAvailableParkingSlot(randomTransport);
	        	Date parkingTime=new Date();
	        	if(availableParkingSlot==null){
	    			logger.info(String.format("A %s has entered at %s hour %s minute, but left because there are no vacant %s parking spaces"
	    					, randomTransport.toString().toLowerCase()
	    					, parkingTime.getHours()
	    					, parkingTime.getMinutes()
	    					, randomTransport.toString().toLowerCase()));
	    			return;
	        	}
	        	availableParkingSlot.setStatus(StatusEunm.Parking);
	        	availableParkingSlot.setTx(new TxRecord(parkingTime));
	        	
	        	
	        	logger.info(String.format("A %s has entered at %s hours %s minute and parked in parking space %s. Remaining parking spaces for %s is %s"
	        			, randomTransport.toString().toLowerCase()
						, parkingTime.getHours()
						, parkingTime.getMinutes()
						, availableParkingSlot.getUuid()
						, randomTransport.toString().toLowerCase()
						, cp.countAvailableParkingSpace(randomTransport)));
	        
			}
		} catch (Exception e) {
			logger.error(String.format("[Job:%s]jobHandler fail, exception:%s",status, e.getMessage()),e);

		}

	}
		

}
