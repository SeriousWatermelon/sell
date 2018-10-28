<html>
<#include "../common/header.ftl" />
<body>

<div id="wrapper" class="toggled">
        <#--左侧导航栏-->
        <#include "../common/nav.ftl" />

        <#--卖家商品列表数据-->
        <div id="page-content-wrapper">
            <div class="container-fluid">
                <div class="row clearfix">
                    <div class="col-md-12 column">
                        <table class="table table-bordered table-hover table-condensed">
                            <thead>
                            <tr>
                                <th>订单id</th>
                                <th>姓名</th>
                                <th>手机号</th>
                                <th>地址</th>
                                <th>金额</th>
                                <th>订单状态</th>
                                <th>支付状态</th>
                                <th>创建时间</th>
                                <th colspan="2">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#list orderDTOPage.content as orderDTO>
                            <tr>
                                <td>${orderDTO.orderId}</td>
                                <td>${orderDTO.buyerName}</td>
                                <td>${orderDTO.buyerPhone}</td>
                                <td>${orderDTO.buyerAddress}</td>
                                <td>${orderDTO.orderAmount}</td>
                                <td>${orderDTO.getOrderStatusEnum().message}</td>
                                <td>${orderDTO.getPayStatusEnum().message}</td>
                                <td>${orderDTO.createTime}</td>
                                <td><a href="/sell/seller/order/detail?orderId=${orderDTO.orderId}">详情</a> </td>
                                <td>
                                    <#if orderDTO.getOrderStatusEnum().message == "新订单">
                                        <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}">取消订单</a>
                                    </#if>
                                </td>
                            </tr>
                            </#list>
                            </tbody>
                        </table>
                        <ul class="pagination pull-right">

                        <#--上一页-->
                        <#if currentPage lte 1>
                            <li class="disabled"><a href="#">上一页</a></li>
                        <#else>
                            <li><a href="/sell/seller/order/list?page=${currentPage-1}&size=${size}">上一页</a></li>
                        </#if>

                        <#--页码-->
                        <#list 1..orderDTOPage.getTotalPages() as index>
                            <#if currentPage == index>
                                <li class="disabled"><a href="#">${index}</a></li>
                            <#else>
                                <li><a href="/sell/seller/order/list?page=${index}&size=${size}">${index}</a></li>
                            </#if>
                        </#list>

                        <#--下一页-->
                        <#if currentPage gte orderDTOPage.getTotalPages()>
                            <li class="disabled"><a href="#">下一页</a></li>
                        <#else>
                            <li><a href="/sell/seller/order/list?page=${currentPage+1}&size=${size}">下一页</a></li>
                        </#if>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
</div>

<#--客户下单消息弹窗begin-->
<div class="modal fade" id="mymodel" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="myModalLabel">
                    提示
                </h4>
            </div>
            <div class="modal-body">
                您有新的订单
            </div>
            <div class="modal-footer">
                <button onclick="javascript:document.getElementById('notice').pause();" type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button onclick="location.reload()" type="button" class="btn btn-primary">查看订单</button>
            </div>
        </div>
    </div>
</div>
<#--客户下单消息弹窗end-->

<#--播放音乐-->
<audio id="notice" loop>
    <source src="/sell/mp3/song.mp3" type="audio/mpeg" />
</audio>

<script src="https://cdn.bootcss.com/jquery/3.0.0/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script>

    // 卖家端后台，订单消息通知
    var websocket = null;
    if('WebSocket' in window){
        websocket = new WebSocket('ws://127.0.0.1/sell/websocket');
    }else{
        alert("浏览器不支持websocket！");
    }
    //websocket建立连接
    websocket.open = function(event){
        console.log("建立连接");

    }
    //websocket关闭连接
    websocket.onclose = function(event){
        console.log("连接关闭");

    }
    //websocket接收到消息
    websocket.onmessage = function(event){
        //收到消息后弹窗提醒，并播放音乐
        $('#mymodel').modal('show');
        document.getElementById('notice').play();
    }

    //websocket发生错误
    websocket.onerror = function(event){
        alert("websocket通信发生错误");
    }

    //卸载页面前关闭websocket
    window.onbeforeunload = function (){
        websocket.close();
    }

</script>


</body>
</html>

