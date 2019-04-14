package com.tora.carpark.job;

import java.util.Date;

import com.tora.carpark.CarPark;
import com.tora.carpark.eunm.StatusEunm;
import com.tora.carpark.space.ParkingSpace;
import com.tora.carpark.tx.TxRecord;

public class LeavingJob extends Job {

	public LeavingJob(CarPark cp) {
		super( StatusEunm.Leaving , cp, 2);
	}
	
		

	@Override
	public void jobHandler(){
		try {
			synchronized (cp) {
		
	        	ParkingSpace parkedSlot = chooseSomeoneToLeave();
	        	
	        	if(parkedSlot==null){
	        		logger.debug("No parked slot found.");
	        		return;
	        	}
	        	
	        	parkedSlot.setStatus(StatusEunm.Leaving);
	        	TxRecord tx =parkedSlot.getTx();
	        	tx.setLeavingTime(new Date());
	        	tx.calculateParkingFee(parkedSlot.getTransportFee().getPrice());
	        	parkedSlot.setTx(tx);
	        	
	        	logger.info(String.format("The %s parked at parking space %s has left at %s hour %s minute. Total time parked is %s hours %s minutes, for a total parking fee of %s. Available parking spaces for %s is %s"
	        			, parkedSlot.getTransportFee().getName().toString().toLowerCase()
						, parkedSlot.getUuid()
						, parkedSlot.getTx().getLeavingTime().getHours()
						, parkedSlot.getTx().getLeavingTime().getMinutes()
						, parkedSlot.getTx().getTotalParkingTime()/(60*60)
						, parkedSlot.getTx().getTotalParkingTime()/(60)
						, parkedSlot.getTx().getParkingFee()
						, parkedSlot.getTransportFee().getName().toString().toLowerCase()
						, cp.countAvailableParkingSpace(parkedSlot.getTransportFee().getName())));
	        	
			}

		} catch (Exception e) {
			logger.error(String.format("[Job:%s]jobHandler fail, exception:%s",status, e.getMessage()),e);
	
		}
	}
}
