import java.util.ArrayList;

public class Statistics {

	private int anzahlBesucher;
	private float avgPrice;
	private float avgDauer;
	private float gesamtDauer;
	private float sum;
	private ArrayList<Integer> spots;
	private ArrayList<String> parktime;
	
	public Statistics(ParkhausSystem system) {
		this.anzahlBesucher = 0;
		this.avgPrice = 0.0f;
		this.avgDauer = 0.0f;
		this.gesamtDauer = 0.0f;
		this.sum = 0.0f;
		this.spots = new ArrayList<Integer>();
		this.parktime = new ArrayList<String>();
		while(spots.size() < system.getMaxPark()) spots.add(0);
	}
		
	public void calculate(String priceString, String minutenString) {
		if(!"_".equals(priceString)) {
			float price = Float.parseFloat(priceString);
			setSum(getSum() + (price/100));
			setAnzahlBesucher(getAnzahlBesucher() + 1);
		}
		if(!"_".equals(minutenString)) {
			float minuten = Float.parseFloat(minutenString);
			setGesamtDauer(getGesamtDauer() + minuten/1000);
		}
		setAvgDauer(getGesamtDauer()/getAnzahlBesucher());
		setAvgPrice(getSum()/getAnzahlBesucher());
	}
	
	public void timeData(String time) {
		parktime.add(time);
	}
	
	public void countSpots(String parkString) {
		if(!"_".equals(parkString)) {
			//Erhöhe Wert für genutzten Parkplatz um 1
			getSpots().set((Integer.parseInt(parkString)-1), getSpots().get(Integer.parseInt(parkString)-1) + 1);
		}
	}
	

	//Getter und Setter

	public ArrayList<String> getParktime() {
		return parktime;
	}

	public int getAnzahlBesucher() {
		return anzahlBesucher;
	}

	public void setAnzahlBesucher(int anzahlBesucher) {
		this.anzahlBesucher = anzahlBesucher;
	}

	public float getAvgDauer() {
		return avgDauer;
	}

	public void setAvgDauer(float avgDauer) {
		this.avgDauer = avgDauer;
	}
	
	public float getGesamtDauer() {
		return gesamtDauer;
	}
	
	public void setGesamtDauer(float gesamtDauer) {
		this.gesamtDauer = gesamtDauer;
	}

	public float getSum() {
		return sum;
	}

	public void setSum(float sum) {
		this.sum = sum;
	}

	public float getAvgPrice() {
		return avgPrice;
	}

	public void setAvgPrice(float avgPrice) {
		this.avgPrice = avgPrice;
	}

	public ArrayList<Integer> getSpots() {
		return spots;
	}

	public void setSpots(ArrayList<Integer> spots) {
		this.spots = spots;
	}
	
}
