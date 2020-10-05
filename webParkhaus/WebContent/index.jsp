<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Parkhaus</title>
<script src='https://ccmjs.github.io/mkaul-components/parkhaus/versions/ccm.parkhaus-9.1.7.js'></script>
</head>
<body>

<ccm-parkhaus-9-1-7
	server_url="http://localhost:8080/webParkhaus/Parkhaus"
	client_categories='["red","blue","green"]'
	extra_buttons='["sum","avg","Alarm","ChangeParkSystem"]'
	extra_charts='["Auslastung","Parkdauer"]'
></ccm-parkhaus-9-1-7>


</body>
</html>