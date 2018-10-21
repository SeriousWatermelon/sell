<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>成功</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
</head>
<body onload="initSuccess()">

<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="alert alert-dismissable alert-success">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                <h4>
                    操作成功!
                </h4> <strong>${msg!"操作成功"}!</strong> <a href="${url}" class="alert-link"><label id="time">3</label>s后自动跳转</a>
            </div>
        </div>
    </div>
</div>

</body>

<script>
    var restTime=3;
    function initSuccess(){
        setInterval(function(){
            restTime--;
            if(restTime>0){
                document.getElementById("time").innerHTML=restTime; //每次设置的x的值都不一样了。
            }else{
                location.href='${url}';
            }
        }, 1000);
    }
</script>

</html>