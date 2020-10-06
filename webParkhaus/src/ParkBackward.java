import java.util.ArrayList;

public class ParkBackward implements IParkBehavior {

	//Automatische Einparkfunktion(Rückwärts)
	
	@Override
	public int einparkSystem(ParkhausSystem system, String[] params) {
		//Rufe Instanz des angefragten Fahrzeugtyps ab
		Car newCar = Car.getInstance(params[9]);
		newCar.initAttributes(params);
		System.out.println("Auto wurde hinzugefügt: "+params[9]+ " " + newCar);	
		
		//Einparkvorgang 		
		if(system.getAlarm() == true) {
			System.out.println("Alarm ist aktiviert. Einparkfunktion gesperrt.");
			return -1;
		}else {
			//Einparkfunktion
			ArrayList<Car> spots = system.getSpots();
			
			int j = spots.lastIndexOf(null);
			spots.set(j, newCar);
			
			system.setSpots(spots);
			return j+1;
		}
	}

}
