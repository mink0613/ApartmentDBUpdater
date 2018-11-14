<?php
$connect = mysql_connect("127.0.0.1", "modoocoupon", "fjtnldywjd1");
mysql_selectdb("modoocoupon");
mysql_query("set names utf8"); 

$qry = "SELECT * FROM J_CODE";

$result = mysql_query($qry);

$data = array(); 

while($obj = mysql_fetch_object($result))
{
    $ID = $obj->ID;
    $code = $obj->CODE;
    $name1 = $obj->NAME_1;
    $name2 = $obj->NAME_2;
    $name3 = $obj->NAME_3;
 
	array_push($data, 
            array('ID'=>$ID,
            'code'=>$code,
            'name1'=>$name1,
            'name2'=>$name2,
			'name3'=>$name3
        ));
}

header('Content-Type: application/json; charset=utf8');
$json = json_encode(array("JCODEList"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
echo $json;
mysql_close($connect);
?>