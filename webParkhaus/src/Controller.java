import java.util.ArrayList;

public class Controller implements IController{
	
	private ParkhausSystem system;
	

	public Controller() {
		system = new ParkhausSystem(new ParkDefault());
		new ViewAdmin(system);
		new ViewCustomer(system);
	}
	
	public int einparkSystem(String[] params) {
		return this.system.getParkBehavior().einparkSystem(this.system, params);
	}
	
	public void ausparkSystem(String[] params) {
		Integer freeParkIndex = Integer.parseInt(params[7])-1;
//		Nimmt betreffendes Fahrzeug aus der ArrayList
		ArrayList<Car> spots = this.system.getSpots();
		Car cTemp = spots.get(freeParkIndex);
		cTemp.setAttributes(params);
//		Berechne sumPrice, avgPrice und anzahlBesucher
		this.system.getStats().calculate(cTemp.getPreis(), cTemp.getParkdauer(), params[2]);

//		speichert Dauer des Parkvorgangs		
		this.system.getStats().timeData(cTemp.getParkdauer());
		
		//Zaehlt besuchten Parkplatz
		this.system.getStats().countSpots(params[7]);
//		Setzt Instanz zur�ck und setzt Position in der Arraylist auf null
		cTemp.clearAttributes();
		spots.set(freeParkIndex, null);
		this.system.setSpots(spots);
	}
	
	public ParkhausSystem getSystem() {
		return system;
	}

}
