<?php
	require "dbcon.php";

	$latitude=filter_input(INPUT_POST, "latitude");
	$longitude=filter_input(INPUT_POST, "longitude");

	//$username="yasas";
	//$password="123";

	$query="insert into locations(latitude,longitude)values('$latitude','$longitude')";

	$row = mysqli_query($con, $query);
	if($row==1){
		echo "inserted location";
	}
	else{
		echo " Error ";
	} 

	mysqli_close($con);

?>