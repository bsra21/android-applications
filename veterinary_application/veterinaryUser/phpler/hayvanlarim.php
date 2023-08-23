<?php
include("ayar.php");

$mus_id= $_POST["musid"];  
$sorgula=mysqli_query($baglan,"select * from hayvan_listesi where mus_id='$mus_id'");
$count=mysqli_num_rows($sorgula);

class petClass{
    public $petid;
    public $petresim;
    public $petisim;
    public $petcins;
    public $pettur;
}
$pet=new petClass();
$sayac=0;

if($count>0){
    echo("[");
    while($bilgi=mysqli_fetch_assoc($sorgula))
    {      /*bu..mysqli_fetch_assoc($sorgula)..tarzı 
        girisyap.phpde daha öncden kullanmıştık
        Orada sadece bir kere sorgulama yapıyordu.
        Burada liste içini sorguladığı için while ekledik. */                
        $sayac=$sayac+1;
        $pet->petid=$bilgi["id"];
        $pet->petresim=$bilgi["hayvan_resim"];
        $pet->petisim=$bilgi["hayvan_isim"];
        $pet->petcins=$bilgi["hayvan_cins"];
        $pet->pettur=$bilgi["hayvan_tur"];
        $pet->tf=true;
        echo(json_encode($pet));
        if($count>$sayac){
            echo(",");
        }
    }
    echo("]");
}

else{
    echo("[");

    $pet->petid=null;
    $pet->petresim=null;
    $pet->petisim=null;
    $pet->petcins=null;
    $pet->pettur=null;
    $pet->tf=false;
    echo(json_encode($pet));
    echo("]");
}

?>