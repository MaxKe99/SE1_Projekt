
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Parkhaus")
public class ParkhausServlet extends HttpServlet {
       
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void init() {
		//Initialisiere Alarmsystem
		ParkhausState state = getState();
		//Initialisiere ParkhausSystem
		ParkhausSystem system = getSystem();
		
		state.add(system);
		
		//Setze Alarm auf false
		state.notify(false);
		
		getApplication().setAttribute("state", state);
		getApplication().setAttribute("system", system);
	}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] requestParamString = request.getQueryString().split("=");
		String command = requestParamString[0];
		String param = requestParamString[1];
		
		response.setContentType("text/html"); 
		PrintWriter out = response.getWriter(); 
//		Erlaubt zwei Nachkommastellen bei Zahlen
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		
		ParkhausState state = getState();
		ParkhausSystem system = getSystem();
		
		//SummenButton
		if("cmd".equals(command)&&"sum".equals(param)) {		
			out.println(df.format(system.getStats().getSum()) + " Euro");
			System.out.println("sum = " + df.format(system.getStats().getSum()) + " Euro");
		}
		
		//AverageButton
		if("cmd".equals(command)&&"avg".equals(param)) {
			out.println("Durchschnittlicher Ticketpreis: " + df.format(system.getStats().getAvgPrice()) + " Euro;" + " " + "Durchschnittliche Parkdauer: " + df.format(system.getStats().getAvgDauer()) + " Minuten");
			System.out.println("avgPrice = " + df.format(system.getStats().getAvgPrice()) + " Euro;" + " " + "AvgDur: " + df.format(system.getStats().getAvgDauer()) + " Minuten");
		}
			
		//AlarmButton
		if("cmd".equals(command)&&"Alarm".equals(param)) {
			if(state.getAlarm() == false) {
				state.notify(true);
				out.println("Alarm wurde aktiviert. Kein Einparken mehr.");	
			}else {
				state.notify(false);
				out.println("Alarm wurde deaktiviert. Einparken wieder erlaubt");	
			}					
		}
		
		//ChangeParkSystem Button
		if("cmd".equals(command)&&"ChangeParkSystem".equals(param)) {
			if(system.getParkBehavior() instanceof ParkDefault) {
				out.println("Parkverhalten: Backward");
				system.setParkBehavior(new ParkBackward());
			}else {
				out.println("Parkverhalten: Forward");
				system.setParkBehavior(new ParkDefault());
			}
			System.out.println("Parkverhalten wurde geändert");
		}
		
		//Auslastungschart
		if("cmd".equals(command)&&"Auslastung".equals(param)) {
			ArrayList<Integer> spots = system.getStats().getSpots();
			out.println(Graphs.create(spots, "Auslastung"));
		}
				
		//Parkdauerchart
		if("cmd".equals(command)&&"Parkdauer".equals(param)) {
			ArrayList<String> parktime = system.getStats().getParktime();
			out.println(Graphs.create(parktime, "Parkdauer"));
		}
		
		getApplication().setAttribute("state", state);
		getApplication().setAttribute("system", system);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ParkhausSystem system = getSystem();
		String body = getBody(request);
		System.out.println(body);
		
		//String der Parkhaus Api einlesen
		String[] params = body.split(","); 
		String event = params[0];	
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
				
		if("leave".equals(event)) {		
			//Automatische Ausparkfunktion
			system.ausparkSystem(params);
		}
		
		if("change_max".equals(event)) {
			//Parkhaus Größe verändern
			system.changeMax(params);
		}
		
		if("enter".equals(event)) {
			//Automatische Einparkfunktion
			out.println(system.einparkSystem(params));
		}
		getApplication().setAttribute("system", system);
	}
	
	//Methode um Request in einen String umzuwandeln
	private static String getBody(HttpServletRequest request) throws IOException {
		
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;
		
		try {
			InputStream inputStream =  request.getInputStream();
			if(inputStream != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				char[] charBuffer = new char[128];
				int bytesRead = -1;
				while((bytesRead = bufferedReader.read(charBuffer)) > 0) {
					stringBuilder.append(charBuffer,0, bytesRead);
					
				}
			}else {
					stringBuilder.append("");
				}
		}finally {
			if(bufferedReader != null) {
				bufferedReader.close();
			}
		}	
		return stringBuilder.toString();
	}
	
	
	//ServletContext Variablen
	private ServletContext getApplication() {
		return getServletConfig().getServletContext();
	}
			
	private ParkhausState getState() {
		ParkhausState state;
		ServletContext application = getApplication();
		state = (ParkhausState)application.getAttribute("state");
		if(state == null) {
			state = new ParkhausState();
		}
		return state;
	}
	
	private ParkhausSystem getSystem() {
		ParkhausSystem system;
		ServletContext application = getApplication();
		system = (ParkhausSystem)application.getAttribute("system");
		if(system == null) {
			system = new ParkhausSystem(new ParkDefault());
		}
		return system;
	}
	
}
