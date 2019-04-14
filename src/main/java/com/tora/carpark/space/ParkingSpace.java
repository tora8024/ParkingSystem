package com.tora.carpark.space;

import com.tora.carpark.eunm.StatusEunm;
import com.tora.carpark.fee.TransportFee;
import com.tora.carpark.tx.TxRecord;

public class ParkingSpace {

	private String uuid;
	private TransportFee transportFee;
	private StatusEunm status=StatusEunm.Leaving; 
	private TxRecord tx=null; 
	
	public ParkingSpace(TransportFee transportFee, int index) {
		super();
		this.uuid =String.format("%s_%s", transportFee.getName().toString(), index);
		this.transportFee = transportFee;
	}


	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public TransportFee getTransportFee() {
		return transportFee;
	}

	public void setTransportFee(TransportFee transportFee) {
		this.transportFee = transportFee;
	}


	public StatusEunm getStatus() {
		return status;
	}


	public void setStatus(StatusEunm status) {
		this.status = status;
	}
	

	public TxRecord getTx() {
		return tx;
	}


	public void setTx(TxRecord tx) {
		this.tx = tx;
	}


	public String toString(){
		return String.format("[ParkingSpace]%s, status:%s, uui:%s", this.transportFee.toString(), this.status.toString(), this.uuid);
	}
	
}
