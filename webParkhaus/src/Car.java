import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Car {
	
	private String nr;
	private String farbe;
	private String parkdauer;
	private String preis;
    private final String key;
    
    private static final ConcurrentMap<String, Car> multitons = new ConcurrentHashMap<>();


    private Car(String key) {
    	this.key = key;	
    }

    public static Car getInstance(final String key) {
        return multitons.computeIfAbsent(key, Car::new);
    }
	
	
	//Getter und Setter
    
    public void initAttributes(String[] params) {
		this.nr = params[1];
		this.farbe = params[6];
    }
    
    public void setAttributes(String[] params) {
		this.parkdauer = params[3];
		this.preis = params[4];
    }
    
    public void clearAttributes() {
		this.nr = "None";
		this.farbe = "None";
		this.parkdauer = "0";
		this.preis = "None";
    }
    
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

	public String getPreis() {
		return preis;
	}

	public void setPreis(String preis) {
		this.preis = preis;
	}
	
	
}
