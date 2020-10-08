import java.util.ArrayList;

public class ParkhausSystem implements IModel {

//	Verwaltung der parkenden Fahrzeuge, Fahrzeuge desselben Typs benutzen identische Instanzen,
//	deren Daten bei aussparken aktualisiert und zurückgesetzt werden
	private ArrayList<Car> spots;
//	Einparkzeit und Typ des letzten Fahrzeugs, welches in die Garage eingefahren ist
	private ArrayList<String> lastParked;
	
	private Integer maxPark;
	private IParkBehaviour parkBehav;
	private Statistics stats;
//	Speichert Observer/Views
	private ArrayList<IObserver> observers = new ArrayList<IObserver>();

	public ParkhausSystem(IParkBehaviour parkBehav) {
		this.spots = new ArrayList<Car>();
		this.lastParked = new ArrayList<String>();
		this.maxPark = 10;
		while(spots.size() < maxPark) spots.add(null);
		this.parkBehav = parkBehav;
		this.stats = new Statistics(this);
		this.lastParked.add("PKW");
		this.lastParked.add("0");
	}
//	Aktualisiert letztes Fahrzeug, welches in das Parkhaus eingefahren ist
	@Override
	public void updateLast(String type, String time) {
		lastParked.set(0, type);
		lastParked.set(1, time);
		}
	@Override
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
	
//	Getter und Setter
//	Entimmt den AdminView
	public ViewAdmin getAdmin() {
		return (ViewAdmin) observers.get(0);
	}
//	Entimmt den CustomerView
	public ViewCustomer getCustomer() {
		return (ViewCustomer) observers.get(1);
	}
		
	public ArrayList<Car> getSpots() {
		return spots;
	}
	
	public void setSpots(ArrayList<Car> spots) {
		this.spots = spots;
		notifyObservers();
	}

	public Integer getMaxPark() {
		return maxPark;
	}

	public void setMaxPark(Integer maxPark) {
		this.maxPark = maxPark;
	}

	public IParkBehaviour getParkBehaviour() {
		return this.parkBehav;
	}
	
	public void setParkBehavior(IParkBehaviour parkBehav) {
		this.parkBehav = parkBehav;
		notifyObservers();
	}
	
	public Statistics getStats() {
		return this.stats;
	}
	
	public void setStats(Statistics stats) {
		this.stats = stats;
		notifyObservers();
	}
	
	public ArrayList<String> getLastParked() {
		return lastParked;
	}

	public void setLastParked(ArrayList<String> lastParked) {
		this.lastParked = lastParked;
	}

//	Methoden des Modell-Patterns
	@Override
	public void add(IObserver o) {
		observers.add(o);
	}

	@Override
	public void remove(IObserver o) {
		observers.remove(o);
	}

	@Override
	public void notifyObservers() {
		for(int i = 0; i < observers.size(); i++) {
			observers.get(i).update();
		}
	}

}
