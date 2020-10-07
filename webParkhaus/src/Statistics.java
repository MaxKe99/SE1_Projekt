import java.util.ArrayList;

public class Statistics {

	private int anzahlBesucher;
	private float avgPrice;
	private float avgDauer;
	private float gesamtDauer;
	private float sum;
	private ArrayList<Integer> spots;
	private ArrayList<String> parktime;
	private ArrayList<String> carType;
	private double creation;
	private float incomeDay;
	private float incomeWeek;
	private int dayCounter;
	
	public Statistics(ParkhausSystem system) {
		this.dayCounter = 1;
		this.incomeDay = 0f;
		this.incomeWeek = 0f;
		this.creation = 0;
		this.anzahlBesucher = 0;
		this.avgPrice = 0.0f;
		this.avgDauer = 0.0f;
		this.gesamtDauer = 0.0f;
		this.sum = 0.0f;
		this.spots = new ArrayList<Integer>();
		this.parktime = new ArrayList<String>();
		this.setCarType(new ArrayList<String>());
		while(spots.size() < system.getMaxPark()) spots.add(0);
	}
		
	public void calculate(String priceString, String minutenString, String currentTime) {
		if(!"_".equals(priceString)) {
			float price = Float.parseFloat(priceString);
			setSum(getSum() + (price/100));
			setAnzahlBesucher(getAnzahlBesucher() + 1);
		}
		if(!"_".equals(minutenString)) {
			float minuten = Float.parseFloat(minutenString);
			setGesamtDauer(getGesamtDauer() + minuten/1000);
		}
//		Berechnung der Tageseinnahmen
//		Tag wird hier relativ zur Erstellung der Parkhaussession gesehen.
		if(!"_".equals(currentTime)) {
			double time = Double.parseDouble(currentTime);
			if(time - creation <= 86400000) {
				setIncomeDay(getIncomeDay() + Float.parseFloat(priceString) / 100);
				setIncomeWeek(getIncomeWeek() + Float.parseFloat(priceString) / 100);
			}else if(time - creation > 86400000) {
//			Tag hat 1440 Minuten, Tageseinnahmen werden zurückgesetzt
				creation += 1440;
				dayCounter +=1;
				incomeDay = 0;
//				Nach 7 Tagen werden Wocheneinnahmen zurückgesetzt
				if(dayCounter > 7) {
					incomeWeek = 0;
					dayCounter = 1;
				}
				setIncomeDay(getIncomeDay() + Float.parseFloat(priceString) / 100);
				setIncomeWeek(getIncomeWeek() + Float.parseFloat(priceString) / 100);
			}
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
	
	public double getCreation() {
		return creation;
	}

	public void setCreation(long creation) {
		this.creation = creation;
	}

	public ArrayList<String> getCarType() {
		return carType;
	}

	public void setCarType(ArrayList<String> carType) {
		this.carType = carType;
	}

	public float getIncomeDay() {
		return incomeDay;
	}

	public void setIncomeDay(float incomeDay) {
		this.incomeDay = incomeDay;
	}

	public float getIncomeWeek() {
		return incomeWeek;
	}

	public void setIncomeWeek(float incomeWeek) {
		this.incomeWeek = incomeWeek;
	}
	
}
