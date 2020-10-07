import java.util.ArrayList;

public class ParkBackward implements IParkBehavior {

	//Automatische Einparkfunktion(R�ckw�rts)
	
	@Override
	public int einparkSystem(ParkhausSystem system, String[] params) {
		//Rufe Instanz des angefragten Fahrzeugtyps ab
		Car newCar = Car.getInstance(params[9]);
		newCar.initAttributes(params);
		
		system.updateLast(params[9], params[2]);
		
		System.out.println("Auto wurde hinzugef�gt: "+params[9]+ " " + newCar);	
		//Einparkfunktion
		ArrayList<Car> spots = system.getSpots();
		ArrayList<String> typ = system.getStats().getCarType();
		typ.add(newCar.getKey());
			
		int j = spots.lastIndexOf(null);
		spots.set(j, newCar);
			
		system.setSpots(spots);
		return j+1;
	}

}
