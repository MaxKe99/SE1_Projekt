import java.util.ArrayList;

public class ParkDefault implements IParkBehavior {

	//Automatische Einparkfunktion(Vorw�rts)
	
	@Override
	public int einparkSystem(ParkhausSystem system, String[] params) {
		//Erstelle neues Car
		Car newCar = new Car(params);
		//F�ge Car in die Statistik hinzu
		Statistics stats = system.getStats();
		stats.addCar(newCar);
		system.setStats(stats);
		System.out.println("Auto wurde hinzugef�gt: "+newCar);	
		
		//Einparkvorgang 		
		if(system.getAlarm() == true) {
			System.out.println("Alarm ist aktiviert. Einparkfunktion gesperrt.");
			return -1;
		}else {
			//Automatische Einparkfunktion
			ArrayList<Integer> free = system.getFree();
			int j = free.indexOf(0);
			free.set(j, 1);
			system.setFree(free);
			return j+1;
		}
	}

}
