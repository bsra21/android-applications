<?php
include("ayar.php");
$musid=$_POST["musid"];
$petisim=$_POST["isim"];
$tur=$_POST["tur"];
$cins=$_POST["cins"];
$resim=$_POST["resim"];

class Result
{
    public $text;
    public $tf;
}

$result=new Result();
$isim=rand(0,100000).rand(0,100000).rand(0,100000).rand(0,100000);
$yol="resimler/$isim.png"; //kesme işaretlerine dikkat.
$resimurl="https://designofengineering.com/uygulama/resimler/$isim.png";

$add=mysqli_query($baglan,"insert into hayvan_listesi(mus_id,hayvan_isim,hayvan_tur,hayvan_cins,hayvan_resim) values ('$musid','$petisim','$tur','$cins','$resimurl')");


if($add)
{
    file_put_contents($yol,base64_decode($resim));

    $result->text="Kullanıcıya pet eklendi";
    $result->tf=true;
    echo(json_encode($result));
}
else
{
    $result->text="Kullanıcıya pet eklenemedi";
    $result->tf=false;
    echo(json_encode($result));    
}

?>