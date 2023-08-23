<?php
include("ayar.php");
$mailA=$_POST["mailadres"];
$sifre=$_POST["sifre"];

$control=mysqli_query($baglan,"select * from kullanicilar where mailAdres='$mailA' and parola= '$sifre'");
$count=mysqli_num_rows($control);

class UserLogin{
    public $id;
    public $userName;
    public $mailAdres;
    public $parola;
    public $tf;
    public $text;
}

$user=new UserLogin();

if($count>0){
    $parse=mysqli_fetch_assoc($control);
    $durum=$parse["durum"];
    $id=$parse["id"];
    $userName=$parse["kadi"];
    $parola=$parse["parola"];
    $mailAdres=$parse["mailAdres"];

    if($durum==1){
        $user->tf=true;
        $user->text="Sisteme giriş başarılı..";
        $user->id=$id;
        $user->parola=$parola;
        $user->userName=$userName;
        $user->mailAdres=$mailAdres;
        echo(json_encode($user));
    }
    else{
        $user->tf=false;
        $user->text="Sisteme giriş için mail adresinizi onaylamanız gerekmektedir..";
        $user->id=null;
        $user->parola=null;
        $user->userName=null;
        $user->mailAdres=null;
        echo(json_encode($user));
    }
}
else{
    $user->tf=false;
    $user->text="Sistemde girmiş olduğunuz kayıtlara göre kullanıcı bulunmamaktadır..";
    $user->id=null;
    $user->parola=null;
    $user->userName=null;
    $user->mailAdres=null;

    echo(json_encode($user));

}


?>