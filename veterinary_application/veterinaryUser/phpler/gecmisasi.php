<?php
include("ayar.php");

$musid=$_POST["id"];
$pet_id=$_POST["petid"];

$sor=mysqli_query($baglan,"select a.hayvan_isim,a.hayvan_tur,a.hayvan_cins,a.hayvan_resim,b.asi_tarih,b.asi_isim 
from hayvan_listesi a inner join takipasi b on a.id=b.pet_id where a.mus_id='$musid' 
and b.asi_durum='1' and b.pet_id='$pet_id'");

$count=mysqli_num_rows($sor);

class asiTakip{
    public $hayvanisim;
    public $hayvantur;
    public $hayvancins;
    public $hayvanresim;
    public $asitarih;
    public $asiisim;
    public $tf;
}
$asi=new asiTakip();
$sayac=0;

if($count>0) {

    echo("[");
    while ($kayit = mysqli_fetch_assoc($sor)) {
        $sayac = $sayac + 1;
        $asi->hayvanisim = $kayit["hayvan_isim"];
        $asi->hayvantur = $kayit["hayvan_tur"];
        $asi->hayvancins = $kayit["hayvan_cins"];
        $asi->hayvanresim = $kayit["hayvan_resim"];
        $asi->asitarih = $kayit["asi_tarih"];
        $asi->asiisim = $kayit["asi_isim"];
        $asi->tf=true;
        echo(json_encode($asi));
        if($count>$sayac){
            echo(",");
        }

    }
    echo("]");
}
else{
    echo("[");
    $asi->hayvanisim = null;
    $asi->hayvantur = null;
    $asi->hayvancins =null;
    $asi->hayvanresim = null;
    $asi->asitarih =null;
    $asi->asiisim = null;
    $asi->tf=false;
    echo(json_encode($asi));
    echo("]");
}



?>