<?php
include("ayar.php");
$musid=$_POST["musid"];
$petid=$_POST["petid"];
$asi=$_POST["name"];
$tarih=$_POST["tarih"];

$ekle=mysqli_query($baglan,"insert into takipasi(mus_id,pet_id,asi_isim,asi_durum,asi_tarih)values('$musid','$petid','$asi','0','$tarih')");
class Result
{
    public $text;
    public $tf;
}

$res=new Result();
if($ekle)
{
    $res->text="Pete aşı girilmiştir.";
    $res->tf=true;
    echo(json_encode($res));
}
else
{
    $res->text="Pete aşı eklenememiştir.";
    $res->tf=false;
    echo(json_encode($res));    
}

?>