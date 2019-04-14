package com.tora.carpark.fee;

import com.tora.carpark.eunm.TransportEunm;

public class TransportFee {
	private TransportEunm name;
	private int price;

	public TransportFee() {
		super();
	}

	public TransportFee(TransportEunm name, int fee) {
		super();
		this.name = name;
		this.price = fee;
	}

	public TransportEunm getName() {
		return name;
	}

	public void setName(TransportEunm name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setFee(int price) {
		this.price = price;
	}
	
	public String toString(){
		return String.format("Transport Name:%s, price:%s", this.name.toString(), this.price);
	}

}
