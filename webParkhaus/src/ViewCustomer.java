import java.util.ArrayList;

public class ViewCustomer implements IObserver{

	private ParkhausSystem model;
	private ArrayList<String> currentCustomer;
	private long time;
	private double output;
	
	public ViewCustomer(ParkhausSystem model) {
		this.model = model;
		createComponents();
		model.add(this);
	}
	
	private void createComponents() {
		currentCustomer = model.getLastParked();
		time = System.currentTimeMillis();
		String difference = Double.toString((time - Double.parseDouble(currentCustomer.get(1))) / 1000.);
		setOutput(calcPrice(currentCustomer.get(0), difference));
		
	}
	
	public double calcPrice(String type, String time) {
		switch(type) {
		case "SUV":
			return Double.parseDouble(time) * 1.5;
		case "Pickup":
			return Double.parseDouble(time) * 1.25;
		case "Zweirad":
			return Double.parseDouble(time) * 0.5;
		case "Trike": 
			return Double.parseDouble(time) * 0.75;
		case "Quad":
			return Double.parseDouble(time) * 0.75;
		default:
			return Double.parseDouble(time);
		}
		
	}

	@Override
	public void update() {
		
		currentCustomer = model.getLastParked();
		time = System.currentTimeMillis();
		String difference = Double.toString((time - Double.parseDouble(currentCustomer.get(1))) / 1000.);
		setOutput(calcPrice(currentCustomer.get(0), difference));
		
	}

	public double getOutput() {
		return output;
	}

	public void setOutput(double output) {
		this.output = output;
	}
}
