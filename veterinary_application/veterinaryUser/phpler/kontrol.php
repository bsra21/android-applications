<?php /** @noinspection SqlNoDataSourceInspection */
include("ayar.php");
//echo($serverName);

$mailAdres=$_POST["mailAdres"];
$kadi=$_POST["kadi"];
$parola=$_POST["pass"];
$dogrulamaKodu=rand(0,10000).rand(0,10000);
$durum=0;
$kontrol=mysqli_query($baglan,"select * from kullanicilar where mailAdres='$mailAdres' or kadi='$kadi'");
$count=mysqli_num_rows($kontrol);

class User{
    public $text;
    public  $tf;
}
$user=new User();

if($count>0){
    // echo ("başarısız");
    $user->text="Girmiş olduğunuz bilgilere ait kullanıcı bulunmaktadır. Lütfen bilgileriniz değiştirin.";
    $user->tf=false;
    echo(json_encode($user));
}
else{

    echo ("başarılı");
    $addUser=mysqli_query($baglan,"insert into kullanicilar(kadi,mailAdres,parola,dogrulamaKodu,durum) values ('$kadi','$mailAdres','$parola','$dogrulamaKodu','$durum')" );

    $git="https://designofengineering.com/uygulama/aktifet.php?mail=".$mailAdres."&dogrulamaKodu=".$dogrulamaKodu."";
    $to=$mailAdres;
    $subject="Kullanıcı Hesabı Aktifleştirme";
    $text="Merhaba Sayin ".$kadi."\n Sisteme giris için onay gereklidir. <a href='".$git."'> Onayla </a>";
    $from="From:info@osmantalhadincer.com";
//$from.="MIME-Version: 1.0\r\n";
//$from.="Content-Type: text/html; charset=UTF-8\r\n";
    mail($to,$subject,$text,$from);

    $user->text="Hesabınız kaydedildi, ancak adresinizi doğrulamanız gerekiyor...";
    $user->tf=true;
    echo(json_encode($user));

}

?>