import java.util.ArrayList;

public class ParkhausSystem implements IModel {

	private ArrayList<Car> spots;
	private ArrayList<String> lastParked;
	private Integer maxPark;
	private IParkBehavior parkBehav;
	private Statistics stats;
	private ArrayList<IObserver> observers = new ArrayList<IObserver>();


	
	public ParkhausSystem(IParkBehavior parkBehav) {
		this.spots = new ArrayList<Car>();
		this.lastParked = new ArrayList<String>();
		this.maxPark = 10;
		while(spots.size() < maxPark) spots.add(null);
		this.parkBehav = parkBehav;
		this.stats = new Statistics(this);
		this.lastParked.add("PKW");
		this.lastParked.add("0");
	}
	
	public void updateLast(String type, String time) {
		lastParked.set(0, type);
		lastParked.set(1, time);
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
	
	public ViewAdmin getAdmin() {
		return (ViewAdmin) observers.get(0);
	}
	
	public ViewCustomer getCustomer() {
		return (ViewCustomer) observers.get(1);
	}
		
	//Getter und Setter	
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

	public IParkBehavior getParkBehavior() {
		return this.parkBehav;
	}
	
	public void setParkBehavior(IParkBehavior parkBehav) {
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

	@Override
	public void add(IObserver o) {
		// TODO Auto-generated method stub
		observers.add(o);
	}

	@Override
	public void remove(IObserver o) {
		// TODO Auto-generated method stub
		observers.remove(o);
	}

	@Override
	public void notifyObservers() {
		for(int i = 0; i < observers.size(); i++) {
			observers.get(i).update();
		}
	}

	public ArrayList<String> getLastParked() {
		return lastParked;
	}

	public void setLastParked(ArrayList<String> lastParked) {
		this.lastParked = lastParked;
	}

}
