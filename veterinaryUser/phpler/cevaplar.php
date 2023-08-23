<?php
include("ayar.php");

$mus_id=$_POST["mus_id"];

$sor=mysqli_query($baglan,"select a.id as soruid,a.soru,b.cevap,b.id as cevapid,b.soru_id from sorular a inner join cevaplar b on a.id=b.soru_id where a.durum='1' and a.mus_id='$mus_id' and b.mus_id='$mus_id'");
$count=mysqli_num_rows($sor);

class soruClass{
    public $soru;
    public $cevap;
    public $cevapid;
    public $soruid;
    public $tf;
}

$sorusor=new soruClass();
$sayac=0;

if($count>0){
    echo("[");
    while($bilgi=mysqli_fetch_assoc($sor))
    {                       //bu..mysqli_fetch_assoc($sorgula)..tarzı girisyap.phpde daha öncden kullanmıştık
        //Orada sadece bir kere sorgulama yapıyordu.Burada liste içini sorguladığı için while ekledik.
        $sayac=$sayac+1;
        $sorusor->soru=$bilgi["soru"];
        $sorusor->cevap=$bilgi["cevap"];
        $sorusor->cevapid=$bilgi["cevapid"];
        $sorusor->soruid=$bilgi["soru_id"];
        $sorusor->tf=true;
        echo(json_encode($sorusor));
        if($count>$sayac){
            echo(",");
        }
    }
    echo("]");
}

else{
    echo("[");

    $sorusor->soru=null;
    $sorusor->cevap=null;
    $sorusor->cevapid=null;
    $sorusor->soruid=null;
    $sorusor->tf=false;
    echo(json_encode($sorusor));
    echo("]");

}


?>