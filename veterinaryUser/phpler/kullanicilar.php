<?php
include("ayar.php");
$sql=mysqli_query($baglan,"select * from kullanicilar where durum='1'");
$count=mysqli_num_rows($sql);

class Kullanici
{
    public $id;
    public $kadi;
    public $telefon;
    public $tf;
}
$kl=new Kullanici();
$sayac=0;
if($count>0)
{
    echo("[");
    while($bilgi=mysqli_fetch_assoc($sql))
    {
        $sayac=$sayac+1;
        $kl->id=$bilgi["id"];
        $kl->kadi=$bilgi["kadi"];
        $kl->telefon=$bilgi["telefon"];
        $kl->tf=true;
        echo(json_encode($kl));
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
    $kl->id=null;
    $kl->kadi=null;
    $kl->telefon=null;
    $kl->tf=false;
    echo(json_encode($kl));
    echo(json_encode($pet));
    echo("]");

}