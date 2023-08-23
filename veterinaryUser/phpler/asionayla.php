<?php
include("ayar.php");

$id=$_POST["id"];

$onayla=mysqli_query($baglan,"update takipasi set asi_durum='1' where id='$id'");
class Result{
    public $text;
    public $tf;
}
$result=new Result();

$result->text="Aşı yapılmıştır.";
$result->tf=true;
echo(json_encode($result));
// {"text":"A\u015f\u0131ım\u015ft\u0131r.","tf":true}
// {"text":"A\u015f\u0131 yap\u0131lm\u0131\u015ft\u0131r.","tf":true}

?>