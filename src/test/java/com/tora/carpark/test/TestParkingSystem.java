package com.tora.carpark.test;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.tora.carpark.CarPark;
import com.tora.carpark.eunm.StatusEunm;
import com.tora.carpark.eunm.TransportEunm;
import com.tora.carpark.space.ParkingSpace;
import com.tora.carpark.tx.TxRecord;


public class TestParkingSystem {

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	Logger logger = Logger.getLogger(TestParkingSystem.class);

	@Test
	public void confirmParkingSlot(){
		try{
			CarPark cp=new CarPark();			
			Assert.assertTrue(cp.countAvailableParkingSpace(TransportEunm.Motorcycle)==15 
								&& cp.countAvailableParkingSpace(TransportEunm.Car) ==20 
								&& cp.countAvailableParkingSpace(TransportEunm.SmallBus)==5);		
		}catch(Exception e){
			logger.error(String.format("confirmParkingSlot fail, exception:%s",e.getMessage()),e);
			Assert.fail();
		}
	}
	
	
	@Test
	public void noParkingSlot(){
		try{
			CarPark cp=new CarPark();
			cp.setSpaceList(new ArrayList<ParkingSpace>());
			cp.genParkingSpace(0, TransportEunm.Motorcycle);
			ParkingSpace  parkingSpace  =cp.getAvailableParkingSlot(TransportEunm.Motorcycle);
			
			Assert.assertEquals(null,parkingSpace);
		}catch(Exception e){
			logger.error(String.format("noParkingSlot fail, exception:%s",e.getMessage()),e);
			Assert.fail();
		}
	}
	
	
	@Test
	public void notDefineTransport(){
		try{
			CarPark cp=new CarPark();
			cp.setSpaceList(new ArrayList<ParkingSpace>());
			cp.genParkingSpace(1, TransportEunm.Motorcycle);
			ParkingSpace  parkingSpace  =cp.getAvailableParkingSlot(TransportEunm.Car);
			
			Assert.assertEquals(null,parkingSpace);
		}catch(Exception e){
			logger.error(String.format("notDefineTransport fail, exception:%s",e.getMessage()),e);
			Assert.fail();
		}
	}
	

	@Test
	public void parkingFee(){
		try{
			CarPark cp=new CarPark();
			ParkingSpace  parkingSpace  =cp.getAvailableParkingSlot(TransportEunm.Car);
			parkingSpace.setStatus(StatusEunm.Parking);
			Date parkingTime=sdf.parse("2018-10-10 05:26:00.000");
			TxRecord tx=new TxRecord(parkingTime);
			parkingSpace.setStatus(StatusEunm.Leaving);
			tx.setLeavingTime(sdf.parse("2018-10-10 05:26:00.000"));
			parkingSpace.setTx(tx);
			parkingSpace.getTx().calculateParkingFee(parkingSpace.getTransportFee().getPrice());
			
			Assert.assertEquals(0L,parkingSpace.getTx().getParkingFee());
		}catch(Exception e){
			logger.error(String.format("parkingJob fail, exception:%s",e.getMessage()),e);
			Assert.fail();
		}
	}
	
	@Test
	public void longTimeParkingFee(){
		try{
			CarPark cp=new CarPark();
			ParkingSpace  parkingSpace  =cp.getAvailableParkingSlot(TransportEunm.Car);
			parkingSpace.setStatus(StatusEunm.Parking);
			Date parkingTime=sdf.parse("2018-10-10 05:26:00.000");
			TxRecord tx=new TxRecord(parkingTime);
			parkingSpace.setStatus(StatusEunm.Leaving);
			tx.setLeavingTime(sdf.parse("2018-10-10 06:26:00.000"));
			parkingSpace.setTx(tx);
			parkingSpace.getTx().calculateParkingFee(parkingSpace.getTransportFee().getPrice());
			
			Assert.assertEquals(1800L,parkingSpace.getTx().getParkingFee());
		}catch(Exception e){
			logger.error(String.format("parkingJob fail, exception:%s",e.getMessage()),e);
			Assert.fail();
		}
	}
}
