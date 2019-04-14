package com.tora.carpark.fee;

import com.tora.carpark.eunm.TransportEunm;

public class CarFee extends TransportFee {

	public CarFee() {
		super(TransportEunm.Car, 30);
	}

}
