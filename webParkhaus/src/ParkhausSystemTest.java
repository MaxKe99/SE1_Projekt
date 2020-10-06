import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ParkhausSystemTest implements IParkhausSystemTest {
	
	ParkhausSystem system;
	ParkhausState state;
	
	//Konstruiere die String Arrays für drei Autos
	String paramEin1 = "enter,198,1600287775708,_,_,a18f72fad189b4353bf37eb4b9c1bb56,#8c581d,6,red,PKW,198";
	String paramAus1 = "leave,198,1600287779236,3509,351,0b277c939866d49e2b00b39fca224e1d,#8c581d,1,red,PKW,198";		
	String[] EinAuto1 = paramEin1.split(","); 
	String[] AusAuto1 = paramAus1.split(",");
		
	String paramEin2 = "enter,204,1600287830797,_,_,10212b62b42bf41b8c287ab045ab0b2c,#fc6e11,3,red,SUV,204";
	String paramAus2 = "leave,204,1600287832803,1985,199,27b017fdf143cfabb809da0d19b5f0c1,#fc6e11,2,red,SUV,204";
	String[] EinAuto2 = paramEin2.split(","); 
	String[] AusAuto2 = paramAus2.split(","); 
		
	String paramEin3 = "enter,948,1600288100348,_,_,4dbb42a885e54d4898cfc8f0ded95a2a,#a034ca,2,red,Quad,948";
	String paramAus3 = "leave,948,1600288102327,1961,196,4e702cc3cf14bd15c873c321aa0aba9a,#a034ca,3,red,Quad,948";
	String[] EinAuto3 = paramEin3.split(","); 
	String[] AusAuto3 = paramAus3.split(","); 
	
	
	@BeforeEach
	void init() {
		//Damit Parkhaus vor jedem Test leer ist
		system = new ParkhausSystem(new ParkDefault());
		state = new ParkhausState();
		state.add(system);
		//Setze Alarm auf false
		state.notify(false);
	}

	@DisplayName("Alarmfunktion")
	@Test
	public void testAlarmFunktion() {
		//Zu Beginn sollte Alarm aus sein.
		assertEquals(false, system.getAlarm());
		//Parke Auto ein
		assertEquals(1, system.einparkSystem(AusAuto1));
		//Löse Alarm aus
		state.notify(true);
		//ParkhausSystem Klasse sollte informiert worden sein
		assertEquals(true, system.getAlarm());
		//Einparken sollte gesperrt sein
		assertEquals(-1, system.einparkSystem(AusAuto2));
		//Ausparken sollte weiterhin funktionieren
		system.ausparkSystem(AusAuto1);
		//Daher sollte nun der erste Parkplatz wieder frei sein
		assertEquals(null, system.getSpots().get(0));
		//Alarm wieder austellen
		state.notify(false);
		//Nun sollte das zweite Auto wieder einparken können
		assertEquals(1, system.einparkSystem(AusAuto2));
	}

	@DisplayName("Einpark(Default) und Ausparkfunktion Belegungsreihenfolge")
	@Test
	public void testEinAusParkenDefault() {
		//Erstes Auto sollte ersten Parkplatz belegen
		assertEquals(1,system.einparkSystem(EinAuto1));
		//Zweites Auto sollte zweiten Parkplatz belegen
		assertEquals(2,system.einparkSystem(EinAuto2));
		//Zweites Auto ausparken
		system.ausparkSystem(AusAuto2);
		//Drittes Auto sollte auf Parkplatz zwei sein
		assertEquals(2,system.einparkSystem(EinAuto3));
		//Erster Parkplatz wird frei
		system.ausparkSystem(AusAuto1);
		//Es sollte vorne aufgefüllt werden
		assertEquals(1,system.einparkSystem(EinAuto2));
		//Wenn nun wieder Auto1 einparkt sollte der dritte
		//Parkplatz belegt sein, da 1 und 2 ja voll sind
		assertEquals(3,system.einparkSystem(EinAuto1));
	}
	
	@DisplayName("Einpark(Backward) und Ausparkfunktion Belegungsreihenfolge")
	@Test
	public void testEinAusParkenBackward() {
		system.setParkBehavior(new ParkBackward());
		//Erstes Auto sollte letzten Parkplatz belegen
		assertEquals(10,system.einparkSystem(EinAuto1));
		//Zweites Auto sollte vorletzten Parkplatz belegen
		assertEquals(9,system.einparkSystem(EinAuto2));
		//Zweites Auto ausparken
		AusAuto2[7] = "9";
		system.ausparkSystem(AusAuto2);
		//Drittes Auto sollte dann auf dem vorletzten Parkplatz sein
		assertEquals(9,system.einparkSystem(EinAuto3));
		//Letzter Parkplatz wird frei
		AusAuto1[7] = "10";
		system.ausparkSystem(AusAuto1);
		//Es sollte ganz hinten aufgefüllt werden
		assertEquals(10,system.einparkSystem(EinAuto2));
		//Wenn nun wieder Auto1 einparkt sollte der 8.
		//Parkplatz belegt werden, da 10 und 9 ja voll sind
		assertEquals(8,system.einparkSystem(EinAuto1));
	}

	@DisplayName("Parkhaus Größe verändern")
	@Test
	public void testChangeMax() {
		system.setParkBehavior(new ParkBackward());
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
		system.changeMax(to11);
		//Letzter belegter Parkplatz sollte 11 sein
		assertEquals(11,system.einparkSystem(EinAuto1));
		//Parke aus, weil Parkplatz 11 im Test gleich nochmal belegt wird
		AusAuto1[7] = "11";
		system.ausparkSystem(AusAuto1);
		assertEquals(11, system.getMaxPark());
		//Zu 12
		system.changeMax(to12);
		assertEquals(12,system.einparkSystem(EinAuto1));
		assertEquals(12, system.getMaxPark());
		//zurück zu 11
		system.changeMax(to11);
		assertEquals(11,system.einparkSystem(EinAuto1));
		assertEquals(11, system.getMaxPark());
		//zurück zu 10
		system.changeMax(to10);
		assertEquals(10,system.einparkSystem(EinAuto1));
		assertEquals(10, system.getMaxPark());
		//zu 9
		system.changeMax(to9);
		assertEquals(9,system.einparkSystem(EinAuto1));
		assertEquals(9, system.getMaxPark());
		//zu 8
		system.changeMax(to8);
		assertEquals(8,system.einparkSystem(EinAuto1));
		assertEquals(8, system.getMaxPark());
	}

	@DisplayName("Teste ob Statistiken richtig erfasst werden")
	@Test
	public void testStatistics() {
		//Lasse drei Autos ein- und wieder ausparken
		system.einparkSystem(EinAuto1);
		system.einparkSystem(EinAuto2);
		system.einparkSystem(EinAuto3);
		
		system.ausparkSystem(AusAuto1);
		system.ausparkSystem(AusAuto2);
		system.ausparkSystem(AusAuto3);
		
		//Anzahl Besucher sollte drei sein
		assertEquals(3, system.getStats().getAnzahlBesucher());
		//SummePreis
		float sum = (Float.parseFloat(AusAuto1[4]) +  Float.parseFloat(AusAuto2[4]) + Float.parseFloat(AusAuto3[4]))/100;
		assertEquals(sum, system.getStats().getSum());
		//Gesamt Dauer in Minuten
		float gesamtDauer = Float.parseFloat(AusAuto1[3]) +  Float.parseFloat(AusAuto2[3]) + Float.parseFloat(AusAuto3[3]);
		gesamtDauer /= 1000f;
		assertEquals(gesamtDauer, system.getStats().getGesamtDauer());
		//avgPreis
		assertEquals(sum/3, system.getStats().getAvgPrice());
		//avgDauer
		assertEquals(gesamtDauer/3, system.getStats().getAvgDauer());
		
	}

}
