import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Car {
	private String nr;
	private String farbe;
	private String parkdauer;
	private String preis;
//	Key zur Identifizierung der angefragten Instanz von Car
    private final String key;
//  Speicherort der einzelnen Instanzen
    private static final ConcurrentMap<String, Car> multitons = new ConcurrentHashMap<>();

//	Konstruktor
    private Car(String key) {
    	this.key = key;	
    }
//	Methode zum Abrufen der Instanz, der Key ist enthalten in den Parametern
//  computeIfAbsent erstellt eine neue Car-Instanz, falls für den angefragten Key keine Instanz existiert
    public static Car getInstance(final String key) {
        return multitons.computeIfAbsent(key, Car::new);
    }
//	Übernimmt die Funktion des alten Konstruktors
    public void initAttributes(String[] params) {
		this.nr = params[1];
		this.farbe = params[6];
    }
    
    public void setAttributes(String[] params) {
		this.parkdauer = params[3];
		this.preis = params[4];
    }
//  setzt Instanz wieder zurück, sobald diese "ausgeparkt" wird.
    public void clearAttributes() {
		this.nr = "None";
		this.farbe = "None";
		this.parkdauer = "0";
		this.preis = "None";
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

	public String getPreis() {
		return preis;
	}

	public void setPreis(String preis) {
		this.preis = preis;
	}
	public String getKey() {
		return key;
	}
	
	
}
