<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Einstellungen</title>
</head>
<body>


<form action="Parkhaus" method="post" target="_parent">
  <table border=0 cellspacing=3 cellpadding=3>
    <tr bgcolor='#EBEEEE'><th colspan='2'>
        <big>Einstellungen</big><br>
       (Einstellungen um die Charts zu verändern)</th></tr>
    <tr bgcolor="#EBEEEE"><td>Select Chart</td>
      <td>
        <select name="SelectChart" size=1>
          <option value="1" selected>Spots</option>
          <option value="2">Parkdauer</option>
        </select>
      </td></tr>
    <tr bgcolor="#EBEEEE"><td>Select Art</td>
      <td>
        <select name="SelectArt" size=1>
          <option value="1">Bar Chart</option>
          <option value="2">Pie Chart</option>
        </select>
      </td></tr>
    <tr bgcolor="#EBEEEE"><td>Spots: min. Auslastung?<br>Parkdauer: min. Parkdauer in Minuten?</td>
      <td>
        <input type="number" name="number" value="default">
      </td></tr>
    <tr bgcolor="#EBEEEE"><td>Submit</td>
      <td>
        <input type="submit" name="Submit" value="Submit">
      </td></tr>
  </table>
</form>



</body>
</html>