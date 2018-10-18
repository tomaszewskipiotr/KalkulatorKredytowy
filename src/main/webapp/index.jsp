<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Kalkulator Kredytowy</title>
</head>
<body>
<form action ="hello" method="post">
<label> Kwota kredytu <br><input type="number" name="kwotaKredytu" required="required"/><br></label>
<label> Ilosc rat <br><input type="number" name="iloscRat" required="required"/><br></label>
<label> Oprocentowanie <br><input type="number" name="oprocentowanie" required="required"/><br></label>
<label> Opłata stała<br><input type="number" name="oplataStala" required="required"/><br></label>
<label> Rodzaj Rat: <select name="rodzaj">
				<option value="1">Stałe</option>
				<option value="2">Malejące</option>
			</select>
<input type="submit" value="wyslij">
</label>
</form>
</body>
</html>