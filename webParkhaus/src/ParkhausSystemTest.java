import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ParkhausSystemTest implements IParkhausSystemTest {
	
	ParkhausController pController = new ParkhausController();
	
	//Konstruiere die String Arrays für drei Autos
	String paramEin1 = "enter,285,1602151988008,_,_,64c4d605fcc1cb724a7fec1a7193dd41,#a86fe8,8,_,Trike,285";
	String paramAus1 = "leave,285,1602151989099,1082,81,64c4d605fcc1cb724a7fec1a7193dd41,#a86fe8,1,_,Trike,285";		
	String[] EinAuto1 = paramEin1.split(","); 
	String[] AusAuto1 = paramAus1.split(",");
		
	String paramEin2 = "enter,578,1602152541856,_,_,34da6ad2811bf2c016e3448c18fb7b71,#18a96e,3,_,PKW,578";
	String paramAus2 = "leave,578,1602152542450,582,58,34da6ad2811bf2c016e3448c18fb7b71,#18a96e,1,_,PKW,578";
	String[] EinAuto2 = paramEin2.split(","); 
	String[] AusAuto2 = paramAus2.split(","); 
		
	String paramEin3 = "enter,627,1602152048772,_,_,6ce6f4fa47f4b606b8d6d9c8b4df6912,#e45133,5,_,Zweirad,627";
	String paramAus3 = "leave,627,1602152049189,407,20,6ce6f4fa47f4b606b8d6d9c8b4df6912,#e45133,1,_,Zweirad,627";
	String[] EinAuto3 = paramEin3.split(","); 
	String[] AusAuto3 = paramAus3.split(","); 
	
	
	
	@BeforeEach
	void init() {
		//Damit Parkhaus vor jedem Test leer ist
		pController.getSystem().getStats().setCreation(System.currentTimeMillis());
	}

	
	
	@DisplayName("Einpark(Default) und Ausparkfunktion Belegungsreihenfolge")
	@Test
	public void testEinAusParkenDefault() {
		//Erstes Auto sollte ersten Parkplatz belegen
		assertEquals(1, pController.einparkSystem(EinAuto1));
		//Zweites Auto sollte zweiten Parkplatz belegen
		assertEquals(2, pController.einparkSystem(EinAuto2));
		//Drittes Auto sollte auf Parkplatz drei sein
		assertEquals(3, pController.einparkSystem(EinAuto3));
		//Erster Parkplatz wird frei
		pController.ausparkSystem(AusAuto1);
		//Es sollte vorne aufgefüllt werden
		assertEquals(1, pController.einparkSystem(EinAuto2));
	}
	
	@DisplayName("Einpark(Backward) und Ausparkfunktion Belegungsreihenfolge")
	@Test
	public void testEinAusParkenBackward() {
		pController.getSystem().setParkBehavior(new ParkBackward());
		//Erstes Auto sollte letzten Parkplatz belegen
		assertEquals(10, pController.einparkSystem(EinAuto1));
		//Zweites Auto sollte vorletzten Parkplatz belegen
		assertEquals(9, pController.einparkSystem(EinAuto2));
		//Zweites Auto ausparken
		AusAuto2[7] = "9";
		pController.ausparkSystem(AusAuto2);
		//Drittes Auto sollte dann auf dem vorletzten Parkplatz sein
		assertEquals(9, pController.einparkSystem(EinAuto3));
		//Letzter Parkplatz wird frei
		AusAuto1[7] = "10";
		pController.ausparkSystem(AusAuto1);
		//Es sollte ganz hinten aufgefüllt werden
		assertEquals(10, pController.einparkSystem(EinAuto2));
		//Wenn nun wieder Auto1 einparkt sollte der 8.
		//Parkplatz belegt werden, da 10 und 9 ja voll sind
		assertEquals(8, pController.einparkSystem(EinAuto1));
	}

	@DisplayName("Parkhaus Größe verändern")
	@Test
	public void testChangeMax() {
		pController.getSystem().setParkBehavior(new ParkBackward());
		String changeTo11 = "change_max,10,11";
		String changeTo12 = "change_max,11,12";
		String changeTo10 = "change_max,11,10";
		String changeTo9 = "change_max,10,9";
		String changeTo8 = "change_max,9,8";
		String[] to11 =  changeTo11.split(","); 
		String[] to12 =  changeTo12.split(","); 
		String[] to10 =  changeTo10.split(","); 
		String[] to9 =  changeTo9.split(","); 
		String[] to8 =  changeTo8.split(",");
		pController.getSystem().changeMax(to11);
		//Letzter belegter Parkplatz sollte 11 sein
		assertEquals(11, pController.einparkSystem(EinAuto1));
		//Parke aus, weil Parkplatz 11 im Test gleich nochmal belegt wird
		AusAuto1[7] = "11";
		pController.ausparkSystem(AusAuto1);
		assertEquals(11, pController.getSystem().getMaxPark());
		//Zu 12
		pController.getSystem().changeMax(to12);
		assertEquals(12, pController.einparkSystem(EinAuto1));
		assertEquals(12, pController.getSystem().getMaxPark());
		//zurück zu 11
		pController.getSystem().changeMax(to11);
		assertEquals(11,pController.einparkSystem(EinAuto1));
		assertEquals(11, pController.getSystem().getMaxPark());
		//zurück zu 10
		pController.getSystem().changeMax(to10);
		assertEquals(10,pController.einparkSystem(EinAuto1));
		assertEquals(10, pController.getSystem().getMaxPark());
		//zu 9
		pController.getSystem().changeMax(to9);
		assertEquals(9,pController.einparkSystem(EinAuto1));
		assertEquals(9, pController.getSystem().getMaxPark());
		//zu 8
		pController.getSystem().changeMax(to8);
		assertEquals(8,pController.einparkSystem(EinAuto1));
		assertEquals(8, pController.getSystem().getMaxPark());
	}

	@DisplayName("Teste ob Statistiken richtig erfasst werden")
	@Test
	public void testStatistics() {
		//Lasse drei Autos ein- und wieder ausparken
		pController.einparkSystem(EinAuto1);
		pController.ausparkSystem(AusAuto1);
		
		pController.einparkSystem(EinAuto2);
		pController.ausparkSystem(AusAuto2);
		
		pController.einparkSystem(EinAuto3);		
		pController.ausparkSystem(AusAuto3);

		
		//SummePreis
		float sum = (Float.parseFloat(AusAuto1[4]) +  Float.parseFloat(AusAuto2[4]) + Float.parseFloat(AusAuto3[4]))/100;
		assertEquals(sum, pController.getSystem().getStats().getSum());
		//Gesamt Dauer in Minuten
		float gesamtDauer = Float.parseFloat(AusAuto1[3]) +  Float.parseFloat(AusAuto2[3]) + Float.parseFloat(AusAuto3[3]);
		gesamtDauer /= 1000f;
		assertEquals(gesamtDauer, pController.getSystem().getStats().getGesamtDauer());
		//avgPreis
		assertEquals(sum/3, pController.getSystem().getStats().getAvgPrice());
		//avgDauer
		assertEquals(gesamtDauer/3, pController.getSystem().getStats().getAvgDauer());
		
	}

}
