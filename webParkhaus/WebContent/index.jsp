<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Parkhaus</title>
<script src='https://ccmjs.github.io/mkaul-components/parkhaus/versions/ccm.parkhaus-9.1.7.js'></script>
</head>
<body>

<ccm-parkhaus-9-1-7
	price_factor='{"Pickup":1.25,"SUV":1.5,"Zweirad":0.5,"Trike":0.75,"Quad":0.75}'
	vehicle_types='["PKW","SUV","Pickup","Zweirad","Trike","Quad"]'
	server_url="http://localhost:8080/webParkhaus/Parkhaus"
	client_categories='["red","blue","green"]'
	extra_buttons='["sum","avg","ChangeParkSystem"]'
	extra_charts='["Auslastung","Parkdauer"]'
></ccm-parkhaus-9-1-7>


</body>
</html>