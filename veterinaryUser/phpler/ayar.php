<?php
$serverName="https://designofengineering.com";
$userName="busra";
$sifre="Busra1827";
$dbName="uygulama";

$baglan=mysqli_connect($serverName,$userName,$sifre,$dbName);
mysqli_set_charset($baglan,"UTF-8");
mysqli_query($baglan,"SET NAMES UTF8");

?>


