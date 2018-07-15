<?php
include("config.php");
include("connection.php");
error_reporting(E_ALL ^ E_NOTICE);


$sql="UPDATE notifications SET status=1 WHERE status=0";	
$result=mysqli_query($connect, $sql);

$sql="select * from notifications ORDER BY sName DESC limit 5";
$result=mysqli_query($connect, $sql);

$response='';
while($row=mysqli_fetch_array($result)) {
	$response = $response . "<div class='notification-item'>" .
	"<div class='notification-subject'>". $row["date"] . "</div>" . 
	"<div class='notification-comment'>" . $row["location"]  . "</div>" .
	"</div>";
}
if(!empty($response)) {
	print $response;
}


?>