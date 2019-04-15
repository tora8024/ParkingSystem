package com.tora.carpark.job;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;




import com.tora.carpark.CarPark;
import com.tora.carpark.eunm.StatusEunm;
import com.tora.carpark.eunm.TransportEunm;
import com.tora.carpark.space.ParkingSpace;
import com.tora.carpark.tx.TxRecord;

public abstract  class Job implements Runnable {

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	Logger logger = Logger.getLogger(Job.class);
	
	protected StatusEunm status;
	protected int maxRandomNumber;
	protected CarPark cp;

	
	
	public Job( StatusEunm status,CarPark cp, int maxRandomNumber) {
		super();
		this.status = status;
		this.cp=cp;
		this.maxRandomNumber = maxRandomNumber;
	}

	@Override
    public void run() {
		
        int randomInt = ThreadLocalRandom.current().nextInt(0, maxRandomNumber+1);
		logger.debug(String.format("[Job:%s]random number:%s.",status, randomInt));
		
		if(randomInt==0){
			logger.debug(String.format("[Job:%s]not thing happen.",status) );
			return ;
		}

        for(int i=0;i<randomInt;i++){   
        	jobHandler();
        }
    }
	
	public void jobHandler(){
		logger.info("do something.");
		
	}
	
	public TransportEunm getRandomTransport(){

		int randomTransport = ThreadLocalRandom.current().nextInt(0, TransportEunm.values().length);
		return TransportEunm.values()[randomTransport];
		
	}
	
	

	public ParkingSpace chooseSomeoneToLeave(){
		List<ParkingSpace> parkedSlotList =  cp.getSpaceList().stream()                        
                .filter(ps -> StatusEunm.Parking==ps.getStatus() ).collect(Collectors.toList());
		
		if(parkedSlotList==null || parkedSlotList.size()==0){
			return null;
		}
		
		int randomTxRecord = ThreadLocalRandom.current().nextInt(0, parkedSlotList.size());
		return parkedSlotList.get(randomTxRecord);
		
	}
	    


	public StatusEunm getAction() {
		return status;
	}

	public void setAction(StatusEunm action) {
		this.status = action;
	}


	public int getMaxRandomNumber() {
		return maxRandomNumber;
	}

	public void setMaxRandomNumber(int maxRandomNumber) {
		this.maxRandomNumber = maxRandomNumber;
	}

	public CarPark getCp() {
		return cp;
	}

	public void setCp(CarPark cp) {
		this.cp = cp;
	}

}
