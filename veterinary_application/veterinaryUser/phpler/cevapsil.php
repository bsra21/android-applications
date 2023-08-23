<?php
include("ayar.php");

$cevapid = $_POST["cevap"];
$soruid = $_POST["soru"];

$sil = mysqli_query($baglan, "delete from cevaplar where id='$cevapid' and soru_id='$soruid'");
$sil2 = mysqli_query($baglan, "delete from sorular where id='$soruid'");

class deleteRecord
{
    public $text;
    public $tf;

}

$del = new deleteRecord();

if ($sil & $sil2) {
    $del->text = "Silme işlemi başarılı";
    $del->tf = true;
    echo(json_encode($del));
} else {
    $del->text = "Silme işlemi başarısız";
    $del->tf = false;

    echo(json_encode($del));
}


?>