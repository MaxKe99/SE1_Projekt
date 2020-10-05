import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import java.lang.Math;


public class Graphs {
	
	public static String create(ArrayList<? extends Object> list, String chart) {
		String output = "";
		
		chart = chart.toLowerCase();
		if(chart.equals("auslastung")) {
			ArrayList<Integer> spots = (ArrayList<Integer>) list;
			output = spots(spots.stream());
			
		}else if(chart.equals("parkdauer")) {
			ArrayList<Car> cars = (ArrayList<Car>) list;
			output = parkdauer(cars.stream());
		}
		return output;
	}
	
	
	private static String parkdauer(Stream<Car> stream) {
		String output = "";
		
//		Ablage des CarStreams
		ArrayList<Car> streamContainer = new ArrayList<Car>(stream.collect(Collectors.toList()));
//		Rundet Parkdauer zur nächsten Minute auf/ab
		List<String> dauerList = streamContainer.stream()
				.map(x -> Integer.toString(Math.round(Float.parseFloat(x.getParkdauer()))) + " Sekunden").collect(Collectors.toList());
//		Zählt Aufkommen der einzelnen Parkzeiten
		Map<String, Long> counter = dauerList.stream()
				.collect(Collectors.groupingBy(x -> x, Collectors.counting()));
//		Extrahiert die einzelnen Längen
		ArrayList<String> descriptors = new ArrayList<String>();
		descriptors.addAll(counter.keySet());
//		Extrahiert Aufkommen
		ArrayList<Long> counts = new ArrayList<Long>();
		for(int i = 0; i < descriptors.size(); i++) {
			counts.add(counter.get(descriptors.get(i)));
		}
		
		JsonArray xWerte = Json.createArrayBuilder(descriptors).build();
		JsonArray yWerte = Json.createArrayBuilder(counts).build();	

		JsonObject root = Json.createObjectBuilder()
				.add("data", Json.createArrayBuilder()
						.add(Json.createObjectBuilder()
							.add("values",yWerte)
							.add("labels", xWerte)
							.add("type", "pie")
						)	
					)	
				.build();
		output = root.toString();
		System.out.println(output);
		return output;
		
	}	
	
	private static String spots(Stream<Integer> stream) {
		String output = "";
		
//		Ablage des Integerstreams
		ArrayList<Integer> streamContainer = new ArrayList<Integer>(stream.collect(Collectors.toList()));
//		Belegungsarray zu ArrayList
		ArrayList<String> belegung = new ArrayList<String>(streamContainer.stream().map(x -> Integer.toString(x)).collect(Collectors.toList()));
//		Parkplatznummer als Graphbeschreibung
		ArrayList<String> descriptors = new ArrayList<String>();
		for(int i = 0; i < streamContainer.size(); i++) {
			descriptors.add("Parkplatz Nr. " + (i+1));
		}
		
		JsonArray xWerte = Json.createArrayBuilder(descriptors).build();
		JsonArray yWerte = Json.createArrayBuilder(belegung).build();
		
		JsonObject root = Json.createObjectBuilder()
				.add("data", Json.createArrayBuilder()
						.add(Json.createObjectBuilder()
							.add("x",xWerte)
							.add("y", yWerte)
							.add("type", "bar")
							.add("name", "Duration")
						)	
					)	
				.build();
		output = root.toString();
		System.out.println(output);
		return output;
		
	}

	
}
