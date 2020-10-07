<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Adminpage</title>
<script src='https://ccmjs.github.io/mkaul-components/parkhaus/versions/ccm.parkhaus-9.1.9.js'></script>
</head>
<body>
	<ccm-parkhaus-9-1-9
	price_factor='{"Pickup":1.25,"SUV":1.5,"Zweirad":0.5,"Trike":0.75,"Quad":0.75}'
	vehicle_types='["PKW","SUV","Pickup","Zweirad","Trike","Quad"]'
	server_url="http://localhost:8080/webParkhaus/Parkhaus"
	extra_buttons='["sum","avg","ChangeParkSystem"]'
	extra_charts='["Auslastung","Parkdauer","Fahrzeugtypen","Einnahmenverteilung"]'
></ccm-parkhaus-9-1-9>
</body>
</html>