<?php

include("config.php");
include("connection.php");
error_reporting(E_ALL ^ E_NOTICE);

if(isset($_POST["view"]))
{
	$query = "SELECT * FROM patientdb ORDER bY pname DESC LIMIT 5";
	$result = mssql_query ($connect,$query);
	$output = '';
	if (mysqli_num_row($result) > 0) {
		while ($row = mssql_fetch_array($result)) {
			 $output .= '
			 <li>
			 	<a href="#">
			 		<strong>'.$row["date"].'</strong><br />
			 		<small<em>'.$row["pname"].'</em></small>
			 	</a>
			 </li>
			 ';
			 
		}
	}

	else{
		$output .= '
			<li><a href="#" class="text-bold text-italic">No Notification Found.</a> </li>
		';
	}

	$query_1 = "SELECT * FROM patinetdb WHERE status=0";
	$result_1 = mssql_query($connect, $query_1);
	$count = mysqli_num_row($result_1);
	$data = array(
		'notification' => $output,
		'unseen_notification' => $count ); 
	echo json_encode($data);
}


?>