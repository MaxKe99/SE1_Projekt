import java.util.ArrayList;

public class ParkhausController implements IController{
//	Controller des Parkhauses verwaltet System, Views und die Ein- und Ausparkfunktion
	private ParkhausSystem system;
	
//	initialisiert Views und das System
	public ParkhausController() {
		system = new ParkhausSystem(new ParkDefault());
		new ViewAdmin(system);
		new ViewCustomer(system);
	}
	
	public int einparkSystem(String[] params) {
		return this.system.getParkBehaviour().einparkSystem(this.system, params);
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
//		Setzt Instanz zurück und setzt Position in der Arraylist auf null
		cTemp.clearAttributes();
		spots.set(freeParkIndex, null);
		this.system.setSpots(spots);
	}
	
	public ParkhausSystem getSystem() {
		return system;
	}

}
