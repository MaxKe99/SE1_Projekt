
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
//		init Controller
		Controller controller = getController();
		//Initialisiere ParkhausSystem
		getApplication().setAttribute("controller", controller);
		getApplication().setAttribute("system", controller.getSystem());
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
		
		getApplication().setAttribute("system", system);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ParkhausSystem system = getSystem();
		String body = getBody(request);
		System.out.println(body);
		
		Controller controller = getController();
		
		//String der Parkhaus Api einlesen
		String[] params = body.split(","); 
		String event = params[0];	
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
				
		if("leave".equals(event)) {		
			//Automatische Ausparkfunktion
			controller.ausparkSystem(params);
		}
		
		if("change_max".equals(event)) {
			//Parkhaus Größe verändern
			system.changeMax(params);
		}
		
		if("enter".equals(event)) {
			//Automatische Einparkfunktion
			out.println(controller.einparkSystem(params));
		}
		getApplication().setAttribute("controller", controller);
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
			
	private Controller getController() {
		Controller controller;
		
		ServletContext application = getApplication();
		controller = (Controller)application.getAttribute("controller");
		if(controller == null) {
			controller = new Controller();
		}
		
		return controller;
	}
	
	private ParkhausSystem getSystem() {
		Controller controller;
		ServletContext application = getApplication();
		controller = (Controller)application.getAttribute("controller");
		return controller.getSystem();
	}
	
}
