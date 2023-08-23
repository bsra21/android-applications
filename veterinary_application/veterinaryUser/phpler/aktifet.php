<?php
echo("Hosgeldiniz....");
include("ayar.php");
$mail=$_GET["mail"];
$kod=$_GET["dogrulamaKodu"];

$guncelle=mysqli_query($baglan,"update kullanicilar set durum='1' where mailAdres='$mail' and dogrulamaKodu='$kod'");

?>