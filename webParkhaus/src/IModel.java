import java.util.ArrayList;

public interface IModel extends IObservable {

	public ArrayList<Car> getSpots();
	public void setSpots(ArrayList<Car> spots);
	public IParkBehavior getParkBehavior();
	public void setParkBehavior(IParkBehavior parkBehav);
	public Statistics getStats();
	public void setStats(Statistics stats);
	
}
