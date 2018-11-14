<?php
$connect = mysql_connect("127.0.0.1", "modoocoupon", "fjtnldywjd1");
mysql_selectdb("modoocoupon");
mysql_query("set names euc-kr"); 

$apt_name = $_GET['apt_name'];
$built_year = $_GET['built_year'];
$year = $_GET['year'];
$month = $_GET['month'];
$day = $_GET['day'];
$legal_dong = $_GET['legal_dong'];
$jibeon = $_GET['jibeon'];
$apt_size = $_GET['apt_size'];
$j_code = $_GET['j_code'];
$floor = $_GET['floor'];
$trade_price = $_GET['trade_price'];
$rent_price = $_GET['rent_price'];
$rent_month_price = $_GET['rent_month_price'];

$qry = "INSERT INTO ApartmentInfo (APT_NAME, BUILT_YEAR, YEAR, MONTH, DAY, LEGAL_DONG, JIBEON, APT_SIZE, J_CODE, FLOOR, TRADE_PRICE, RENT_PRICE, RENT_MONTH_PRICE) VALUES('$apt_name', '$built_year', '$year', '$month', '$day', '$legal_dong', '$jibeon', '$apt_size', '$j_code', '$floor', '$trade_price', '$rent_price', '$rent_month_price')";

$result = mysql_query($qry);

echo $result;

mysql_close($connect);

?>