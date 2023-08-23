<?php
include("ayar.php");

class asiTakip{
    public $id;
    public $baslik;
    public $aciklama;
    public $thumbnail;
    public $sesdosyasi;
    public $kategori;
    public $tarih;
    public $tf;
}
$asi=new asiTakip();
$sayac=0;

$sorgu=mysqli_query($baglan,"select * from kitaplar");
$count=mysqli_num_rows($sorgu);


if($count>0) {

    echo("[");
    while ($kayit = mysqli_fetch_assoc($sorgu)) {
        $sayac = $sayac + 1;
        $asi->id= $kayit["id"];
        $asi->baslik= $kayit["baslik"];
        $asi->aciklama= $kayit["aciklama"];
        $asi->thumbnail= $kayit["thumbnail"];
        $asi->sesdosyasi= $kayit["sesdosyasi"];
        $asi->kategori= $kayit["kategori"];
        $asi->tarih= $kayit["tarih"];
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
    $asi->id= null;
    $asi->baslik= null;
    $asi->aciklama=null;
    $asi->thumbnail= null;
    $asi->sesdosyasi=null;
    $asi->kategori= null;
    $asi->tarih= null;
    $asi->tf=false;
    echo(json_encode($asi));
    echo("]");
}




?>

