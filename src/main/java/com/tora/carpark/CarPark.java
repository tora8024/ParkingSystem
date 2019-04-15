package com.tora.carpark;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;








import org.apache.log4j.Logger;








import com.tora.carpark.eunm.StatusEunm;
import com.tora.carpark.eunm.TransportEunm;
import com.tora.carpark.fee.TransportFee;
import com.tora.carpark.space.ParkingSpace;
public class CarPark {
	Logger logger = Logger.getLogger(CarPark.class);

	private List<ParkingSpace> spaceList=new ArrayList<ParkingSpace>();

	public CarPark(){
		initalData();
	}
	
	private void initalData(){
		
		genParkingSpace(15, TransportEunm.Motorcycle, 10);
		genParkingSpace(20, TransportEunm.Car , 30);
		genParkingSpace(5, TransportEunm.SmallBus,40);		
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
	
	
	public void genParkingSpace(int amount, TransportEunm transport, int fee){
		
		for(int i =0;i<amount;i++ ){
			spaceList.add( new ParkingSpace( new TransportFee(transport, fee) , i)  );
		}
	}

	

	public List<ParkingSpace> getSpaceList() {
		return spaceList;
	}

	public void setSpaceList(List<ParkingSpace> spaceList) {
		this.spaceList = spaceList;
	}




	
}
