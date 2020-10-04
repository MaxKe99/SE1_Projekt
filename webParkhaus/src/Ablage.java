

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Ablage
 */
@WebServlet("/Ablage")
public class Ablage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] requestParamString = request.getQueryString().split("=");
		String command = requestParamString[0];
		String param = requestParamString[1];
		
		if("cmd".equals(command) && "sum".equals(param)) {
			Float sum = getPersistentSum();
			sum /= 100;
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println(sum  + " Euros");
			
			System.out.println("sum = " + sum);
		
		} else if("cmd".equals(command)&&"avg".equals(param)) {
			Float avg = (getPersistentSum()/getAnzahlBesucher())/100;
			Float dauer = getAvgDauer()/getAnzahlBesucher()/1000;
			
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("Average Price: " + avg + " Euros,  " + "Duration: " + dauer + " minutes");
		
			System.out.println("avg = " + avg + " " + "Duration:" + dauer);
		} else {
			System.out.println("Invalid Command: " + request.getQueryString());
		}
		if("cmd".equals(command)&&"FavSpot".equals(param)) {
			String spots = getSpots();
			
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println(spots);
			
		}
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String body = getBody(request);
		System.out.println(body);
		String[] params = body.split(",");
		String event = params[0];
		if("leave".equals(event)) {
			Float sum = getPersistentSum();
			Float anzahl = getAnzahlBesucher();
			Float dauer = getAvgDauer();
			String priceString = params[4];
			String minutenString = params[3];
			if(!"_".equals(priceString)) {
				float price = Float.parseFloat(priceString);
				sum += price;
				anzahl += 1;
				getApplication().setAttribute("sum", sum);
				getApplication().setAttribute("anzahl", anzahl);
			}
			if(!"_".equals(minutenString)) {
				float minuten = Float.parseFloat(minutenString);
				dauer += minuten;
				getApplication().setAttribute("dauer", dauer);
			}
			
			String parkString = params[7];
			String spots = getSpots();
			if(!"_".equals(parkString)) {
				//String in Array umwandeln
				String[] spotsArray = spots.split(","); 
				//Parkplatz im Array an der richtigen Stelle erhöhen
				Integer wert = Integer.parseInt(spotsArray[Integer.parseInt(parkString)-1]);
				wert = wert + 1;
				String wertString = wert.toString();
				//Wert in Array einfügen
				spotsArray[Integer.parseInt(parkString)-1] = wertString;
				//Array zurück in String umwandeln.
				spots = String.join(",", spotsArray);
				//String permanent machen
				getApplication().setAttribute("spots", spots);	
			}	
			if("change_Max".equals(event)) {
				String maxParkString = params[2];
				Integer maxPark = getMaxPark();
				if(!"_".equals(maxParkString)) {
					Integer max = Integer.parseInt(maxParkString);
					maxPark = max;
					getApplication().setAttribute("maxPark", maxPark);
				}	
			}	
			
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println(sum);
			out.println(anzahl);
			out.println(dauer);	
		}	
	}
		

	private Float getPersistentSum() {
		Float sum;
		ServletContext application = getApplication();
		sum = (Float)application.getAttribute("sum");
		if (sum==null) sum = 0.0f;
		return sum;
	}
	
	private Float getAnzahlBesucher() {
		Float anzahl;
		ServletContext application = getApplication();
		anzahl = (Float)application.getAttribute("anzahl");
		if(anzahl == null) {
			anzahl = 0.0f;
		}
		return anzahl;
	}
	
	private Float getAvgDauer() {
		Float dauer;
		ServletContext application = getApplication();
		dauer = (Float)application.getAttribute("dauer");
		if(dauer == null) {
			dauer = 0.0f;
		}
		return dauer;
	}
	
	private Integer getMaxPark() {
		Integer maxPark;
		ServletContext application = getApplication();
		maxPark = (Integer)application.getAttribute("maxPark");
		if(maxPark == null) {
			maxPark = 10;
		}
		return maxPark;
	}
	
	private String getSpots() {
		String spots;
		
		ServletContext application = getApplication();
		spots = (String)application.getAttribute("spots");
		if(spots == null) {
			spots = "0,0,0,0,0,0,0,0,0,0";		
		}
		return spots;
	}
	

	private ServletContext getApplication() {
		return getServletConfig().getServletContext();
	}

	public static String getBody(HttpServletRequest request) throws IOException {

	    String body = null;
	    StringBuilder stringBuilder = new StringBuilder();
	    BufferedReader bufferedReader = null;

	    try {
	        InputStream inputStream = request.getInputStream();
	        if (inputStream != null) {
	            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
	            char[] charBuffer = new char[128];
	            int bytesRead = -1;
	            while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
	                stringBuilder.append(charBuffer, 0, bytesRead);
	            }
	        } else {
	            stringBuilder.append("");
	        }
	    } catch (IOException ex) {
	        throw ex;
	    } finally {
	        if (bufferedReader != null) {
	            try {
	                bufferedReader.close();
	            } catch (IOException ex) {
	                throw ex;
	            }
	        }
	    }

	    body = stringBuilder.toString();
	    return body;
	}
	
	
	
}

