package com.tora.carpark.tx;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.tora.carpark.CarPark;

public class TxRecord {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	private Logger logger = Logger.getLogger(TxRecord.class);
	
	private Date parkingTime;
	private Date leavingTime;
	private long parkingFee=0;
	private long totalParkingTime=0;
	
	public TxRecord(Date parkingTime){
		this.parkingTime=parkingTime;
	}
	
	public void calculateParkingFee(int price){
		try{
			logger.debug(String.format("leavingTime:%s, parkingTime:%s" , sdf.format(leavingTime), sdf.format(parkingTime)));
			int timeDiff= (int)Math.ceil((Double.valueOf(this.leavingTime.getTime()-this.parkingTime.getTime())/Double.valueOf(1000))); //seconds
			
			this.totalParkingTime=timeDiff*60; //one second as one minute
			logger.debug(String.format("timeDiff:%s sec. totalParkingTime:%s sec.",timeDiff,this.totalParkingTime));
			int hours=(int)Math.ceil(Double.valueOf(totalParkingTime)/Double.valueOf(60*60));
			logger.debug(String.format("calculate parked hours %s", hours));
	
			this.parkingFee= hours * price;
		}catch(Exception e){
			throw e;
		}
	}
	
	
	public Date getParkingTime() {
		return parkingTime;
	}
	public void setParkingTime(Date parkingTime) {
		this.parkingTime = parkingTime;
	}
	public Date getLeavingTime() {
		return leavingTime;
	}
	public void setLeavingTime(Date leavingTime) {
		this.leavingTime = leavingTime;
	}
	public long getParkingFee() {
		return parkingFee;
	}
	public void setParkingFee(long parkingFee) {
		this.parkingFee = parkingFee;
	}

	public long getTotalParkingTime() {
		return totalParkingTime;
	}

	public void setTotalParkingTime(long totalParkingTime) {
		this.totalParkingTime = totalParkingTime;
	}
	
	
	
}
