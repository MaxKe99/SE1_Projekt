import java.util.ArrayList;

public class ParkDefault implements IParkBehaviour {

	//Automatische Einparkfunktion(Vorw�rts)
	
	@Override
	public int einparkSystem(ParkhausSystem system, String[] params) {
		//Rufe Instanz des angefragten Fahrzeugtyps ab
		Car newCar = Car.getInstance(params[9]);
		newCar.initAttributes(params);
//		Setze neues Fahrzeug als Berechnungspunkt f�r aktuelle Ticketkosten f�r ViewCustomer
		system.updateLast(params[9], params[2]);
		
		System.out.println("Auto wurde hinzugef�gt: "+params[9]+ " " + newCar);	
		//Einparkfunktion
		ArrayList<Car> spots = system.getSpots();
		ArrayList<String> typ = system.getStats().getCarType();
		typ.add(newCar.getKey());
			
		int j = spots.indexOf(null);
		spots.set(j, newCar);
			
		system.setSpots(spots);
		return j+1;
	}

}
