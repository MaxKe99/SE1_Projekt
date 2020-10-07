<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Parkhaus</title>
<script src='https://ccmjs.github.io/mkaul-components/parkhaus/versions/ccm.parkhaus-9.1.9.js'></script>
</head>
<body>

<ccm-parkhaus-9-1-9
	price_factor='{"Pickup":1.25,"SUV":1.5,"Zweirad":0.5,"Trike":0.75,"Quad":0.75}'
	vehicle_types='["PKW","SUV","Pickup","Zweirad","Trike","Quad"]'
	server_url="http://localhost:8080/webParkhaus/Parkhaus"
	extra_buttons='["Bisheriger_Preis"]'
></ccm-parkhaus-9-1-9>

<form method="post" target="_blank">
  	<button type="submit" formaction="AdminView.jsp">Admin</button>
</form>

</body>
</html>