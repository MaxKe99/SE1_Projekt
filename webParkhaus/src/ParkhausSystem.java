import java.util.ArrayList;
import java.util.HashMap;

public class ParkhausSystem implements IObserver {

	private boolean alarm;
	private ArrayList<Integer> free;
	private Integer maxPark;
	private IParkBehavior parkBehav;
	private Statistics stats;

	
	public ParkhausSystem(IParkBehavior parkBehav) {
		this.free = new ArrayList<Integer>();
		this.maxPark = 10;
		while(free.size() < maxPark) free.add(0);
		this.parkBehav = parkBehav;
		this.stats = new Statistics(this);
	}
	
	@Override
	public void update(boolean alarm) {
		setAlarm(alarm);
	}
	
	public int einparkSystem(String[] params) {
		return getParkBehavior().einparkSystem(this, params);
	}
	
	public void ausparkSystem(String[] params) {
		Integer freeParkIndex = Integer.parseInt(params[7])-1;
		//Setze Parkplatz an Stelle freeParkIndex auf frei. 
		getFree().set(freeParkIndex, 0);
		
		//Berechne sumPrice, avgPrice und anzahlBesucher. Uebergebe priceString und minutenString
		getStats().calculate(params[4], params[3]);
		//Car Daten vervollständigen
		//Setze Parkdauer und Preis, wenn Auto Parkhaus verlässt
		getStats().carData(params);	
		
		//Zaehle besuchten Parkplatz
		getStats().countSpots(params[7]);
		
	}
	
	
	public void changeMax(String[] params) {
		String maxParkString = params[2];
		if(!"_".equals(maxParkString)) {
			Integer maxNeu = Integer.parseInt(maxParkString);
			Integer maxAlt = getMaxPark();
			setMaxPark(maxNeu);
			if(maxAlt < maxNeu) {
				getFree().add(0);
				getStats().getSpots().add(0);
			}else {
				getFree().remove(getFree().size()-1);
				getStats().getSpots().remove(getStats().getSpots().size()-1);
			}		
		}	
	}
	
		
	//Getter und Setter
	public ArrayList<Integer> getFree() {
		return free;
	}

	public void setFree(ArrayList<Integer> free) {
		this.free = free;
	}

	public Integer getMaxPark() {
		return maxPark;
	}

	public void setMaxPark(Integer maxPark) {
		this.maxPark = maxPark;
	}
	
	public boolean getAlarm() {
		return this.alarm;
	}
	
	public void setAlarm(boolean alarm) {
		this.alarm = alarm;
	}

	public IParkBehavior getParkBehavior() {
		return this.parkBehav;
	}
	
	public void setParkBehavior(IParkBehavior parkBehav) {
		this.parkBehav = parkBehav;
	}
	
	public Statistics getStats() {
		return this.stats;
	}
	
	public void setStats(Statistics stats) {
		this.stats = stats;
	}

}
