
<?php

include("config.php");
include("connection.php");
include_once("security.php");

$json = file_get_contents('php://input');
$obj = json_decode($json);

//$_POST = json_decode(file_get_contents('php://input'), true)


//include("session.php");

error_reporting(E_ALL & E_NOTICE & E_DEPRECATED & E_STRICT & E_WARNING );

$enc = new security();


$keyValue = "DTNT_1.01@ITU";
/*
$password = "05Ju91@LyC$16%IT53";

$mcrypt = mcrypt_module_open('rijndael-256', '', 'cbc', '');//Opens the module
$iv = mcrypt_create_iv(mcrypt_enc_get_iv_size($mcrypt), MCRYPT_DEV_RANDOM);//Define initialization vector
$pass = md5($password);
mcrypt_generic_init($mcrypt, $pass, $iv);//Open buffers
//$decryptedData = mdecrypt_generic($mcrypt, $encryptedData);
//echo PHP_EOL."Decrypted value: ".$decryptedData;
*/
	
$gender_list  = array(" ","Male", "Female");

$disease_list = array(" ", "Bac-confirmed Pulmonary","Clinically diagnosed pulmonary","Extra Pulmonary");

$district_list = array( " ","Attock", "Bahawalnagar", "Bahawalpur",  "Bhakkar", "Chakwal",  "Chiniot",
    					"D.G.Khan", "Faisalabad", "Gujranwala", "Gujrat", "Hafizabad", "Jhang",
    					"Jhelum", "Kasur", "Khanewal", "Khushab", "Lahore", "Layyah", "Lodhran",
    					"Mandi Baha ud din", "Mianwali", "Multan", "Muzaffargarh", "Nankana Sahib",
    					"Narowal", "Okara", "Pakpattan", "Rahim Yar Khan", "Rajanpur", "Rawalpindi",
    					"Sahiwal", "Sargodha", "Sheikhupura", "Sialkot", "Toba Tek Singh", "Vehari");

	$count=0;

	
	$key = mysqli_real_escape_string($connect,$_POST['A']);
	$sender = mysqli_real_escape_string($connect,$_POST["C"]);	
	$sloc = mysqli_real_escape_string($connect,$_POST["E"]);	
	$location = mysqli_real_escape_string($connect,$_POST["J"]);
	$age = mysqli_real_escape_string($connect,$_POST["H"]);
	$lname = mysqli_real_escape_string($connect,$_POST["G"]);
	$disease = mysqli_real_escape_string($connect,$_POST["K"]);
	$sname = mysqli_real_escape_string($connect,$_POST["D"]);
	$date = mysqli_real_escape_string($connect,$_POST["B"]);
	$fname = mysqli_real_escape_string($connect,$_POST["F"]);	
	$gender = mysqli_real_escape_string($connect,$_POST["I"]);
	try{
		
		//$tst = $enc-> encrypt();
		$KEY = $enc -> decrypt($key);
		$SENDER = $enc -> decrypt($sender);
		$SLOC = $enc -> decrypt($sloc);
		$LOCATION = $enc -> decrypt($location);
		$AGE = $enc -> decrypt($age);
		$LNAME = $enc -> decrypt($lname);
		$DISEASE = $enc -> decrypt($disease);
		$SNAME = $enc -> decrypt($sname);
		$DATE = $enc -> decrypt($date);
		$FNAME = $enc -> decrypt($fname);
		$GENDER = $enc -> decrypt($gender);
	//echo $sNAME;
		
		
	}
	catch(Exception $e){
		echo "Message:".$e->getMessage();
	}
	
	$a = (integer) $DISEASE;
	$dis = $disease_list[$a];
	$loc = $district_list[(integer) $LOCATION];
	$sex = $gender_list[(integer) $GENDER];

	// checking if data is sent from DTNT App.
	if($KEY != $keyValue)	
	{
		// Do Nothing.
	}		
	else{
			$query = "INSERT INTO notifications(date,sender,sName,sLocation,fName,lName,age,gender,location,disease,status) 
					VALUES('$DATE','$SENDER','$SNAME','$SLOC','$FNAME','$LNAME','$AGE','$sex','$loc','$dis','0')";				
			$result = mysqli_query($connect,$query);			
			if($result)
			{
				echo "Thank You.";
			}
		else
		{
			echo "Failed. Try again.";
		}
	}
	$sql2="SELECT * FROM notifications WHERE status = 0";
	$result_1=mysqli_query($connect, $sql2);
	$count=mysqli_num_rows($result_1);
	
	$query3 = "SELECT location, count(date) as number FROM notifications GROUP BY location";  
	$result = mysqli_query($connect, $query3);  

	$query4 = "SELECT date, count(date) as Number FROM notifications GROUP BY date";  
	$result2 = mysqli_query($connect, $query4); 

	$q5 = "SELECT sName, count(sName) as Number from notifications GROUP BY sName";
	$r2 = mysqli_query($connect,$q5);

?>
<html>
		<head>
			<meta charset="UTF-8" />
			<meta name="viewport" content="width=device-width, initial-scale=1">
			<title>Notifications</title>
			<link rel="stylesheet" href="notification-demo-style.css" type="text/css">
			<script src="https://code.jquery.com/jquery-2.1.1.min.js" type="text/javascript"></script>
			<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>  
			<script type="text/javascript">  
		           google.charts.load('current', {'packages':['corechart']});  
		           google.charts.setOnLoadCallback(drawChart);  
		           function drawChart()  
		           {  
		                var data = google.visualization.arrayToDataTable([  
		                          ['location', 'Number'],  
		                          <?php  
		                          while($row = mysqli_fetch_array($result))  
		                          {  
		                               echo "['".$row["location"]."', ".$row["number"]."],";  

		                          }  
		                          ?>  
		                     ]);  
		                var options = {  
		                      title: 'Percentage of Notifications by District',  
		                      //is3D:true,  
		                      pieHole: 0.4  
		                     };  
		                var chart = new google.visualization.PieChart(document.getElementById('piechart'));  
		                chart.draw(data, options);  
		           }  
		           </script>  
			<script type="text/javascript">  
		           google.charts.load('current', {'packages':['corechart']});  
		           google.charts.setOnLoadCallback(drawChart);  
		           function drawChart()  
		           {  
		                var data = google.visualization.arrayToDataTable([  
		                          ['sName', 'Number'],  
		                          <?php  
		                          while($row = mysqli_fetch_array($r2))  
		                          {  
		                               echo "['".$row["sName"]."', ".$row["Number"]."],";  

		                          }  
		                          ?>  
		                     ]);  
		                var options = {  
		                      title: '#Notifications from Doctors.',  
		                      //is3D:true,  
		                      pieHole: 0.4  
		                     };  
		                var chart = new google.visualization.PieChart(document.getElementById('piechart1'));  
		                chart.draw(data, options);  
		           }  
		           </script>     
			<script type="text/javascript">

			function myFunction() {
				$.ajax({
					url: "view_notification.php",
					type: "POST",
					processData:false,
					success: function(data){
						$("#notification-count").remove();					
						$("#notification-latest").show();$("#notification-latest").html(data);
					},
					error: function(){}           
				});
			 }
			 
			 $(document).ready(function() {
				$('body').click(function(e){
					if ( e.target.id != 'notification-icon'){
						$("#notification-latest").hide();
					}
				});
			});
				 
			</script>


				<style>
				* {
				    box-sizing: border-box;
				}

				.column {
				    float: left;
				    width: 33.33%;
				    padding: 5px;
				}

				/* Clearfix (clear floats) */
				.row::after {
				    content: "";
				    clear: both;
				    display: table;
				}

				/* Responsive layout - makes the three columns stack on top of each other instead of next to each other */
				@media screen and (max-width: 500px) {
				    .column {
				        width: 100%;
				    }
				}
				</style>
		</head>
	<body>
		<div class="demo-content">
			<div id="notification-header">
				   <div style="position:relative">
					   <button id="notification-icon" name="button" onclick="myFunction()" class="dropbtn"><span id="notification-count"><?php if($count>0) { echo $count; } ?></span><img src="notification-icon.png" /></button>
						 <div id="notification-latest"></div>
					</div>			
			</div>
					<?php if(isset($message)) { ?> <div class="error"><?php echo $message; ?></div> <?php } ?>


					<?php if(isset($success)) { ?> <div class="success"><?php echo $success;?></div> <?php } ?>

				<form name="frmNotification" id="frmNotification" action="index.php" method="post" >
					<div id="form-header" class="form-row">Notifications Status</div>

					<table>
					    <tr>
					        <td><div id="piechart" style="width: 450px; height: 600px;"></div></td>
					        <td><div id="piechart1" style="width: 450px; height: 600px;"></div></td>
					    </tr>
					 <table>

					
					</div>			
					
				</form>
			</div>      
	</body>
</html>

<?php
/*
	$originalDate1 = $_POST["from"];
	$d1 = date("dd-mm-yyyy", strtotime($originalDate1));

	$originalDate12 = $_POST["to"];
	$d2 = date("dd-mm-yyyy", strtotime($originalDate12));

	$d3 = $_POST["dept"];

	$sql2="SELECT date, sender, sName, fName, lName, age, gender, location, disease FROM notifications
	 		WHERE date BETWEEN '".$d1."' AND '".$d2."' OR location = '".$d3."' order by date";

		$result_1=mysqli_query($connect, $sql2);
		$count=mysqli_num_rows($result_1);
		mysqli_close($connect);
		$response='';

		if($count > 0){
						echo "<table style=\"width:200%;\"> <tr>
						<th width=\"10%\"> Date </th> <th width=\"7%\"> Contact# </th> <th width=\"7%\"> Sender Name </th> <th width=\"7%\"> First Name </th> 
						<th width=\"7%\"> Last Name </th> <th width=\"4%\"> Age </th> <th width=\"5%\"> Gender </th>  <th width=\"10%\"> Location </th> 
						<th width=\"10%\"> Disease </th> 
						</tr> </table> ";
						$row=mysqli_fetch_array($result_1);
					while(mysqli_fetch_array($result_1)){
					//$response = $response . //"<div class='notification-item'>" .
						
					//	"<div class='notification-subject'>" .
						echo	" <table style=\"background-color: #f1f1c1;width:200%;\"> <tr>".
						"<th width=\"10%\">". $row["date"] . "</th> <th width=\"7%\">" . $row["sender"] . "</th> <th width=\"7%\">". $row["sName"] .
						"</th> <th width=\"7%\">" . $row["fName"] . "</th> <th width=\"7%\">". $row["lName"] . "</th> <th width=\"4%\">". $row["age"] . "</th>" .
						"<th width=\"5%\">". $row["gender"] . "</th> <th width=\"10%\">". $row["location"] . "</th> <th width=\"10%\">". $row["disease"] . "</th>" . "</div>" . 
						"</tr>";
						
	/*	"<div class='notification-comment'>" .   . "</div>" .	


	<script type="text/javascript">
	      google.charts.load('current', {'packages':['corechart']});
	      google.charts.setOnLoadCallback(drawVisualization);

	      function drawVisualization() {


	        var data = google.visualization.arrayToDataTable([
	        	['date','location']
	        
	         <?php  
	                          while($row = mysqli_fetch_array($result3))  
	                          {  
	                               echo "['".$row["date"]."', ".$row["location"]."],";  
	                          }  
	                          ?>
	                          
	                           
	 
	      ]);

	    var options = {
	      title : 'Monthly Coffee Production by Country',
	      vAxis: {title: 'Cups'},
	      hAxis: {title: 'Month'},
	      seriesType: 'bars',
	      series: {5: {type: 'line'}}
	    };

	    var chart = new google.visualization.ComboChart(document.getElementById('chart_div'));
	    chart.draw(data, options);
	  
	  }
	</script>

		"</div>";
			}
	}
	else
	{
			echo " No Record Found.";
	}
			echo "</table>";*/
	if(!empty($response)) {
		print $response;
	}
?>




