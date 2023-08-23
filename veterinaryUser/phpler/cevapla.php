<?php
include("ayar.php");
$musid=$_POST["musid"];
$soruid=$_POST["soruid"];
$text=$_POST["text"];
$ekle=mysqli_query($baglan,"insert into cevaplar(mus_id,soru_id,cevap) values ('$musid','$soruid','$text')");

class Result
{
    public $text;
    public $tf;
}

$res=new Result();

if($ekle)
{
    $guncelle=mysqli_query($baglan,"update sorular set durum='1' where id='$soruid'");
    $res->text="Soru Cevaplandı";
    $res->tf=true;
    echo(json_encode($res));
}
else
{
    $res->text="Soru cevaplanamadı.";
    $res->tf=false;
    echo(json_encode($res));    
}
// {"text":"Soru Cevapland\u0131","tf":true}
?>