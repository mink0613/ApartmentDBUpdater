<?php
$connect = mysql_connect("127.0.0.1", "modoocoupon", "fjtnldywjd1");
mysql_selectdb("modoocoupon");
mysql_query("set names utf8"); 

$brandId = $_GET['brandId'];
$qry = "select * from CouponList where BrandId='$brandId'";
$result = mysql_query($qry);
 
$data = array(); 

while($obj = mysql_fetch_object($result))
{
    $number = $obj->Number;
    $smallImage = $obj->ImageSmall;
    $largeImage = $obj->ImageLarge;
    $title = $obj->Title;
    $detailText = $obj->DetailText;
    $detailLink = $obj->DetailLink;
    $brand = $obj->BrandId;
    $dueDate = $obj->DueDate;
 
 
	array_push($data, 
            array('number'=>$number,
            'smallImage'=>$smallImage,
            'largeImage'=>$largeImage,
            'title'=>$title,
			'detailText'=>$detailText,
			'detailLink'=>$detailLink,
			'brand'=>$brand,
			'dueDate'=>$dueDate
        ));
}
 
header('Content-Type: application/json; charset=utf8');
$json = json_encode(array("coupons"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
echo $json;

mysql_close($connect);

?>