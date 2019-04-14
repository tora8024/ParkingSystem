package com.tora.carpark;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;





import org.apache.log4j.Logger;





import com.tora.carpark.eunm.StatusEunm;
import com.tora.carpark.eunm.TransportEunm;
import com.tora.carpark.fee.CarFee;
import com.tora.carpark.fee.SmallbusFee;
import com.tora.carpark.fee.MotorcycleFee;
import com.tora.carpark.space.ParkingSpace;
import com.tora.carpark.tx.TxRecord;
public class CarPark implements Runnable{
	Logger logger = Logger.getLogger(CarPark.class);

	private List<ParkingSpace> spaceList=new ArrayList<ParkingSpace>();

	public CarPark(){
		initalData();
	}
	
	private void initalData(){
		
		genParkingSpace(15, TransportEunm.Motorcycle);
		genParkingSpace(20, TransportEunm.Car);
		genParkingSpace(5, TransportEunm.SmallBus);		
	}
	
	

	
	public long countAvailableParkingSpace(TransportEunm transport){
		
		long availableParkingSpaceCount = spaceList.stream()                        
                .filter(ps -> StatusEunm.Leaving==ps.getStatus() && transport==ps.getTransportFee().getName() ).count();
		logger.debug(String.format("countAvailableParkingSpace of %s is %s",transport, String.valueOf(availableParkingSpaceCount)));
		
		return availableParkingSpaceCount;
		
	}
	
	
	public ParkingSpace getAvailableParkingSlot(TransportEunm transport){
		if(spaceList==null|| spaceList.size()==0)return null;
		ParkingSpace availableParkingSlot = spaceList.stream()                        
                .filter(ps -> StatusEunm.Leaving==ps.getStatus() && transport==ps.getTransportFee().getName() )        
                .findAny()                                      
                .orElse(null);
		logger.debug(String.format("getAvailableParkingSlot is %s", availableParkingSlot!=null? availableParkingSlot.toString():"empty"));
		
		return availableParkingSlot;
		
	}
	
	
	public void genParkingSpace(int amount, TransportEunm transport){
		
		for(int i =0;i<amount;i++ ){
			
			switch (transport){
				case Car :
					spaceList.add( new ParkingSpace(new CarFee(), i)  );
					break;
				case SmallBus :
					spaceList.add( new ParkingSpace(new SmallbusFee(), i)  );
					break;
				case Motorcycle :
					spaceList.add( new ParkingSpace(new MotorcycleFee(), i)  );
					break;
				default :
					logger.error(String.format("This transport type: %s is not definde.",transport.toString()));
					break;					
			}
		}
	}

	

	public List<ParkingSpace> getSpaceList() {
		return spaceList;
	}

	public void setSpaceList(List<ParkingSpace> spaceList) {
		this.spaceList = spaceList;
	}


	@Override
	public void run() {
		try {
			synchronized (this) {

				logger.info(String.format("Available parking slots for motorcycles: %s, for cars: %s, for small buses: %s"
						, String.valueOf(countAvailableParkingSpace(TransportEunm.Motorcycle))
						, String.valueOf(countAvailableParkingSpace(TransportEunm.Car))
						, String.valueOf(countAvailableParkingSpace(TransportEunm.SmallBus))) );
					
			}
		} catch (Exception e) {
			logger.error(String.format("Report current parking status fail, exception:%s", e.getMessage()),e);

		}
	}


	
}
