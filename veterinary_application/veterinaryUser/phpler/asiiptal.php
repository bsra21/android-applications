<?php
include("ayar.php");

$id=$_POST["id"];

$onayla=mysqli_query($baglan,"delete from takipasi where id='$id'");
class Result{
    public $text;
    public $tf;
}
$result=new Result();

$result->text="Aşı yapılmadı.";
$result->tf=true;
echo(json_encode($result));
// {"text":"A\u015f\u0131ım\u015ft\u0131r.","tf":true}


?>