<?php
include("ayar.php");
$tarih=$_POST["tarih"];
$sql=mysqli_query($baglan,"SELECT a.hayvan_isim, a.hayvan_tur, a.hayvan_cins, a.hayvan_resim, b.asi_isim, b.asi_tarih, b.id AS asiId, c.kadi, c.telefon
FROM hayvan_listesi a
INNER JOIN takipasi b ON a.id = b.pet_id
INNER JOIN kullanicilar c ON c.id = b.mus_id
WHERE b.asi_durum =0
AND b.asi_tarih = '$tarih'");
//kullanicilar tablosuna telefon sutununu ekliyor..
echo(mysqli_num_rows($sql));

class Takip
{
    public $petisim;
    public $pettur;
    public $petcins;
    public $petresim;
    public $asiisim;
    public $asitarih;
    public $asiid;
    public $kadi;
    public $telefon;
    public $tf;
}

$tkp=new Takip();

$count=mysqli_num_rows($sql);

$sayac=0;
if($count>0)
{
    echo("[");
    while($kayit=mysqli_fetch_assoc($sql))
    {
        $sayac=$sayac+1; //a.hayvan_isim, a.hayvan_tur, a.hayvan_cins, a.hayvan_resim, b.asi_is

        $tkp->petisim=$kayit["hayvan_isim"];
        $tkp->pettur=$kayit["hayvan_tur"];
        $tkp->petcins=$kayit["hayvan_cins"];
        $tkp->petresim=$kayit["hayvan_resim"];
        $tkp->asiisim=$kayit["asi_isim"];
        $tkp->asitarih=$kayit["asi_tarih"];
        $tkp->asiid=$kayit["asiId"];
        $tkp->kadi=$kayit["kadi"];
        $tkp->telefon=$kayit["telefon"];
        $tkp->tf=true;
        echo(json_encode($tkp));
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
    $tkp->petisim=null;
    $tkp->pettur=null;
    $tkp->petcins=null;
    $tkp->petresim=null;
    $tkp->asiisim=null;
    $tkp->asitarih=null;
    $tkp->asiid=null;
    $tkp->kadi=null;
    $tkp->telefon=null;
    $tkp->tf=false;
    echo(json_encode($tkp));
    echo("]");

    /*
    {"petisim":null,"pettur":null,"petcins":null,"petresim":null,"asiisim":null,"asitarih":null,"asiid":null,"kadi":null,"telefon":null,"tf":false}
    */
}
?>

