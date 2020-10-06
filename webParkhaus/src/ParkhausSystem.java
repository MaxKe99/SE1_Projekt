import java.util.ArrayList;
import java.util.HashMap;

public class ParkhausSystem implements IObserver {

	private boolean alarm;
	private ArrayList<Car> spots;
	private Integer maxPark;
	private IParkBehavior parkBehav;
	private Statistics stats;

	
	public ParkhausSystem(IParkBehavior parkBehav) {
		this.spots = new ArrayList<Car>();
		this.maxPark = 10;
		while(spots.size() < maxPark) spots.add(null);
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
//		Nimmt betreffendes Fahrzeug aus der ArrayList
		ArrayList<Car> spots = getSpots();
		Car cTemp = spots.get(freeParkIndex);
		cTemp.setAttributes(params);
		
//		Berechne sumPrice, avgPrice und anzahlBesucher
		getStats().calculate(cTemp.getPreis(), cTemp.getParkdauer());

//		speichert Dauer des Parkvorgangs		
		getStats().timeData(cTemp.getParkdauer());
		
		//Zaehlt besuchten Parkplatz
		getStats().countSpots(params[7]);
		
//		Setzt Instanz zurück und setzt Position in der Arraylist auf null
		cTemp.clearAttributes();
		spots.set(freeParkIndex, null);
		setSpots(spots);	
	}
	
	
	public void changeMax(String[] params) {
		String maxParkString = params[2];
		if(!"_".equals(maxParkString)) {
			Integer maxNeu = Integer.parseInt(maxParkString);
			Integer maxAlt = getMaxPark();
			setMaxPark(maxNeu);
			if(maxAlt < maxNeu) {
				getSpots().add(null);
				getStats().getSpots().add(0);
			}else {
				getSpots().remove(getSpots().size()-1);
				getStats().getSpots().remove(getStats().getSpots().size()-1);
			}		
		}	
	}
	
		
	//Getter und Setter	
	public ArrayList<Car> getSpots() {
		return spots;
	}
	
	public void setSpots(ArrayList<Car> spots) {
		this.spots = spots;
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
