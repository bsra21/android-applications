<?php
include("ayar.php");
$id=$_POST["id"];

$sil=mysqli_query($baglan,"delete from hayvan_listesi where id='$id'");

class Result
{
    public $text;
    public $tf;
}
$res= new Result();

if($sil)
{
    $res->text="Silme başarıyla gerçekleşti.";
    $res->tf=true;
    echo(json_encode($res));
}
else
{
    $res->text="Silme işlemi başarısız.";
    $res->tf=false;
    echo(json_encode($res));
}
?>