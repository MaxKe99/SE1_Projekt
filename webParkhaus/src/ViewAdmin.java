import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;

public class ViewAdmin implements IObserver{

	private ParkhausSystem model;
	private String output;

	public ViewAdmin(ParkhausSystem model) {
		this.model = model;
		createComponents();
		model.add(this);
	}
	
	
	private void createComponents() {
		ArrayList<Integer> heights = new ArrayList<Integer>();
		heights.add(Math.round(model.getStats().getIncomeDay()));
		heights.add(Math.round(model.getStats().getIncomeWeek()));
		createJson(heights);
		
	}

	private void createJson(ArrayList<Integer> heights) {
		
		ArrayList<String> descriptors = new ArrayList<String>();
		descriptors.add("Heutige Einnahmen");
		descriptors.add("Woechentliche Einnahmen");
		JsonArray xWerte = Json.createArrayBuilder(descriptors).build();
		JsonArray yWerte = Json.createArrayBuilder(heights).build();
		
		JsonObject root = Json.createObjectBuilder()
				.add("data", Json.createArrayBuilder()
						.add(Json.createObjectBuilder()
							.add("x",xWerte)
							.add("y", yWerte)
							.add("type", "bar")
							.add("name", "Distribution")
						)	
					)	
				.build();
		output = root.toString();
	}

	@Override
	public void update() {
		ArrayList<Integer> heights = new ArrayList<Integer>();
		heights.add(Math.round(model.getStats().getIncomeDay()));
		heights.add(Math.round(model.getStats().getIncomeWeek()));
		createJson(heights);
	}


	public String getOutput() {
		return output;
	}


	public void setOutput(String output) {
		this.output = output;
	}

}
