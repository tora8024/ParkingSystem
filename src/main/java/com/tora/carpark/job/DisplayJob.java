package com.tora.carpark.job;

import org.apache.log4j.Logger;

import com.tora.carpark.CarPark;
import com.tora.carpark.eunm.TransportEunm;
public class DisplayJob implements Runnable {

	Logger logger = Logger.getLogger(DisplayJob.class);
	protected CarPark cp;
	public DisplayJob(CarPark cp) {
		this.cp=cp;
	}
	


	@Override
	public void run() {
		try {
			synchronized (cp) {

				logger.info(String.format("Available parking slots for motorcycles: %s, for cars: %s, for small buses: %s"
						, String.valueOf(cp.countAvailableParkingSpace(TransportEunm.Motorcycle))
						, String.valueOf(cp.countAvailableParkingSpace(TransportEunm.Car))
						, String.valueOf(cp.countAvailableParkingSpace(TransportEunm.SmallBus))) );
					
			}
		} catch (Exception e) {
			logger.error(String.format("Report current parking status fail, exception:%s", e.getMessage()),e);

		}
	}
		

}
