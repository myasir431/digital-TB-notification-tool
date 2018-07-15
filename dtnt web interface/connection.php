<?php

$connect = mysqli_connect($host,$username,$password) or die("Unable to connect. ". mysqli_connect_error());

/* check if server is alive 
if (mysqli_ping($connect)) {
    printf ("Our connection is ok!\n");
} else {
    printf ("Error: %s\n", mysqli_error($connect));
}
*/
/* close connection */

$select = mysqli_select_db($connect,$database) or die("Database not found.");
//mysqli_close($link);
?>