package com.tora.carpark;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.tora.carpark.job.DisplayJob;
import com.tora.carpark.job.LeavingJob;
import com.tora.carpark.job.ParkingJob;


public class AppMain {

	
	public static void main(String args[]){
		CarPark cp=new CarPark();
		
		LeavingJob job1 = new LeavingJob(cp);
		Thread thread1=new Thread(job1);
		thread1.setPriority(10);

		ParkingJob job2 = new ParkingJob(cp);
		Thread thread2=new Thread(job2);
		thread2.setPriority(7);
		

		DisplayJob job3 = new DisplayJob(cp);
		Thread thread3=new Thread(job3);
		
		
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);
		executor.scheduleAtFixedRate(thread1,0,	10,	TimeUnit.SECONDS);
		executor.scheduleAtFixedRate(thread2,0,	20,	TimeUnit.SECONDS);
		executor.scheduleAtFixedRate(thread3,0,	15,	TimeUnit.SECONDS);
		
		
	}

}
