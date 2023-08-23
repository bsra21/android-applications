<?php
include("ayar.php");

$sql=mysqli_query($baglan,"select a.mus_id as musid,a.soru,b.kadi,b.telefon,a.id as soruid from sorular a inner join kullanicilar b on a.mus_id=b.id where a.durum=0");
$count=mysqli_num_rows($sql);

class Soru
{
    public $musid;
    public $soru;
    public $kadi;
    public $telefon;
    public $soruid;
    public $tf;
}

$sor=new Soru();
$sayac=0;

if($count>0)
{
    echo("[");
    while($bilgi=mysqli_fetch_assoc($sql))
    {
        $sayac=$sayac+1;

        $sor->musid=$bilgi["musid"];
        $sor->soru=$bilgi["soru"];
        $sor->kadi=$bilgi["kadi"];
        $sor->telefon=$bilgi["telefon"];
        $sor->soruid=$bilgi["soruid"];
        $sor->tf=true;
        echo(json_encode($sor));
        if($count>$sayac)
        {
            echo(",");
        }
    }
    echo("]");
}
else
{
    echo("[");
    $sor->musid=null;
    $sor->soru=null;
    $sor->kadi=null;
    $sor->telefon=null;
    $sor->soruid=null;
    $sor->tf=false;
    echo(json_encode($sor));
    echo("]");

}
//{"musid":null,"soru":null,"kadi":null,"telefon":null,"soruid":null,"tf":false}
?>