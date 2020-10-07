import java.util.ArrayList;

public class ParkDefault implements IParkBehavior {

	//Automatische Einparkfunktion(Vorwärts)
	
	@Override
	public int einparkSystem(ParkhausSystem system, String[] params) {
		//Rufe Instanz des angefragten Fahrzeugtyps ab
		Car newCar = Car.getInstance(params[9]);
		newCar.initAttributes(params);
		System.out.println("Auto wurde hinzugefügt: "+params[9]+ " " + newCar);	

		//Einparkfunktion
		ArrayList<Car> spots = system.getSpots();
			
		int j = spots.indexOf(null);
		spots.set(j, newCar);
			
		system.setSpots(spots);
		return j+1;
	}

}
