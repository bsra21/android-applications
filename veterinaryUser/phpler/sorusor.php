<?php
include("ayar.php");

$mus_id=$_POST["id"];              //"34";
$soru= $_POST["soru"];                           //"Aşı fiyatı öğrenebilir miyim?";
$ekle=mysqli_query($baglan,"insert into sorular(mus_id,soru,durum) values ('$mus_id','$soru','0')");

class ekleme{
    public $text;
    public $tf;
}

$ekle=new ekleme();

if($ekle){
    $ekle->text="Sorunuz ilgili veterinere ulaşmıştır.";
    $ekle->tf=true;
    echo(json_encode($ekle));
}
else{
    $ekle->text="Soru iletilemedi.";
    $ekle->tf=false;
    echo(json_encode($ekle));
}

?>