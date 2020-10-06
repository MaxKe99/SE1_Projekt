import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class ParkBackward implements IParkBehavior {

	//Automatische Einparkfunktion(R�ckw�rts)
	
	@Override
	public int einparkSystem(ParkhausSystem system, String[] params) {
		//Erstelle neues Car
		Car newCar = Car.getInstance(params[9]);
		newCar.initAttributes(params);
		//F�ge Car in die Statistik hinzu
		System.out.println("Auto wurde hinzugef�gt: "+params[9]+ " " + newCar);	
		
		//Einparkvorgang 		
		if(system.getAlarm() == true) {
			System.out.println("Alarm ist aktiviert. Einparkfunktion gesperrt.");
			return -1;
		}else {
			//Automatische Einparkfunktion
			ArrayList<Car> spots = system.getSpots();
			
			int j = spots.lastIndexOf(null);
			spots.set(j, newCar);
			
			system.setSpots(spots);
			return j+1;
		}
	}

}
