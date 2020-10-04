

public class Car {
	
	private String nr;
	private String ticket;
	private String farbe;
	private String spot;
	private String parkdauer;
	private String preis;

	
	
	public Car(String[] params) {
		this.nr = params[1];
		this.ticket = "None";
		this.farbe = params[8];
		this.parkdauer = "0";
		this.preis = "None";
		this.spot = "None";	
	}
	
	
	//Getter und Setter
	public String getFarbe() {
		return farbe;
	}
	
	
	public void setFarbe(String farbe) {
		this.farbe = farbe;
	}
	
	
	public String getParkdauer() {
		return parkdauer;
	}
	
	
	public void setParkdauer(String parkdauer) {
		this.parkdauer = parkdauer;
	}


	public String getNr() {
		return nr;
	}


	public void setNr(String nr) {
		this.nr = nr;
	}


	public String getTicket() {
		return ticket;
	}


	public void setTicket(String ticket) {
		this.ticket = ticket;
	}


	public String getSpot() {
		return spot;
	}


	public void setSpot(String spot) {
		this.spot = spot;
	}

	public String getPreis() {
		return preis;
	}

	public void setPreis(String preis) {
		this.preis = preis;
	}
	
	
}
