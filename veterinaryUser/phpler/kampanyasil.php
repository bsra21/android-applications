<?php
include("ayar.php");
$id=$_POST["id"];

$sil=mysqli_query($baglan,"delete from kampanyalar where id='$id'");

class Result
{
    public $text;
    public $tf;
}
$result= new Result();

if($sil)
{
    $result->text="Kampanya başarıyla silindi.";
    $result->tf=true;
    echo(json_encode($result));
}
else
{
    $result->text="Kampanya silinemedi.";
    $result->tf=false;
    echo(json_encode($result));
}
?>