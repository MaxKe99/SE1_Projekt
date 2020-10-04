import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;



public class Graphs {
	
	public static String create(ArrayList<? extends Object> list, String chart, ChartSetting setting) {
		String output = "";
		
		chart = chart.toLowerCase();
		String ChartArt = setting.getArt();
		Integer nummer = setting.getNummer();
		if(chart.equals("spots")) {
			ArrayList<Integer> spots = (ArrayList<Integer>) list;
			output = spots(spots.stream().filter(p -> p >= nummer), ChartArt);
			
		}else if(chart.equals("parkdauer")) {
			ArrayList<Car> cars = (ArrayList<Car>) list;
			output = parkdauer(cars.stream().filter(p -> Float.parseFloat(p.getParkdauer()) >= nummer), ChartArt);
		}
		return output;
	}
	
	
	private static String parkdauer(Stream<Car> s, String ChartArt) {
		String output = "";
		ArrayList<Car> cars = (ArrayList<Car>) getArrayListFromStream(s);
		ArrayList<String> nr = new ArrayList<String>();
		ArrayList<String> parkdauer = new ArrayList<String>();
		
		cars.stream().forEach((z)->nr.add("Car_" + z.getNr()));
		cars.stream().forEach((z)->parkdauer.add(z.getParkdauer()));
		
		JsonArray xWerte = Json.createArrayBuilder(nr).build();
		JsonArray yWerte = Json.createArrayBuilder(parkdauer).build();	
		switch(ChartArt) {
			case "bar":
				output = barChart(xWerte, yWerte);
				break;
			case "pie":
				output = pieChart(xWerte, yWerte);
				break;
		}		
		System.out.println(output);
		return output;
		
	}	
	
	private static String spots(Stream<Integer> stream, String ChartArt) {
		String output = "";
		ArrayList<Integer> spots = getArrayListFromStream(stream);
		ArrayList<String> nr = new ArrayList<String>();
		ArrayList<String> anzahl = new ArrayList<String>();
		
		for(int i = 0; i < spots.size(); i++) {
			nr.add("Parkplatz" + Integer.toString(i+1));
		}
		spots.stream().forEach((z)->anzahl.add(Integer.toString(z)));
		
		JsonArray xWerte = Json.createArrayBuilder(nr).build();
		JsonArray yWerte = Json.createArrayBuilder(anzahl).build();	
	
		switch(ChartArt) {
			case "bar":
				output = barChart(xWerte, yWerte);
				break;
			case "pie":
				output = pieChart(xWerte, yWerte);
				break;
		}		
		System.out.println(output);
		return output;
		
	}
	
	private static String barChart(JsonArray xWerte, JsonArray yWerte) {
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
		return root.toString();
	}
	
	private static String pieChart(JsonArray xWerte, JsonArray yWerte) {
		JsonObject root = Json.createObjectBuilder()
				.add("data", Json.createArrayBuilder()
						.add(Json.createObjectBuilder()
							.add("values",yWerte)
							.add("labels", xWerte)
							.add("type", "pie")
						)	
					)	
				.build();
		return root.toString();
	}
	
	
	//Function to get ArrayList from Stream 
    public static <T> ArrayList<T> getArrayListFromStream(Stream<T> stream) { 
        //Convert the Stream to List 
        List<T> list = stream.collect(Collectors.toList()); 
 
        //Create an ArrayList of the List 
        ArrayList<T> arrayList = new ArrayList<T>(list); 
  
        //Return the ArrayList 
        return arrayList; 
    } 
  
	
}
