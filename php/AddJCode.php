<?php
$connect = mysql_connect("127.0.0.1", "modoocoupon", "fjtnldywjd1");
mysql_selectdb("modoocoupon");
mysql_query("set names euc-kr"); 

$code = $_GET['code'];
$name1 = $_GET['name1'];
$name2 = $_GET['name2'];
$name3 = $_GET['name3'];

$qry = "INSERT INTO J_CODE (CODE, NAME_1, NAME_2, NAME_3) VALUES($code, '$name1', '$name2', '$name3')";

$result = mysql_query($qry);

echo $result;

mysql_close($connect);

?>