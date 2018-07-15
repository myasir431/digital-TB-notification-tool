<!DOCTYPE html>
<html>
	<head>
		<title>Digital TB Notification Tool</title>
		<script src="https://ajax.ggogleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
		<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" />
		<script scr="https://maxcdn.bootstrap.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	</head>
	<body>
		<br /><br />
		<div class="container">
			<br />
			<h2 align="center"> Digital TB Notification Tool</h2>
			<br />
			 <nav class="navbar navbar-inverse">
			  <div class="container-fluid">
			    <div class="navbar-header">
			      <a class="navbar-brand" href="#">WebSiteName</a>
			    </div>
			    <ul class="nav navbar-nav navbar-right">
			    	<li class="dropdown">
			      		<a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="label label-pill label-danger count"></span> Notification</a>
			      		<ul class="dropdown-menu"></ul>
			      	</li>
			      
			    </ul>
			  </div>
			</nav> 
			<br />
			<form method="">
		</div>
	</body>
</html>

<script >
	$(document).ready(function(){
		function load_unseen_notification(view='')
		{
			$.ajax({
				url:"fetch.php",
				method:"POST",
				data:{view:view},
				dataType:"json",
				success:function(data)
				{
					$('.dropdown-menu').html(data.notification);
					if(data.unseen_notification >0)
					{
						$('.count').html(data.unseen_notification;)
					}
				}
			});
		}
		load_unseen_notification();
	});
</script>	