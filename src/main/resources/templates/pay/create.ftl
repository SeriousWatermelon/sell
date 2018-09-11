<script>
    /**
     * h5页面支付_freemaker模板
     * 之所以写在后端没写在前端，是因为本端代码是支付页面调用的。
     * 假如有两个前端项目需要支付环节，则需要在项目中写两次本代码。
     *  （时间戳、随机字符串、package都是动态修改的，因此项目运行时需要将值动态注入。
     *   这里使用freemaker技术：
     *       1.引入freemaker依赖；
     *       2.在PayController.java中配置ModelAndView页面跳转方法（即创建订单完成后，跳转到本页面，带上传递的参数）
     *       3.在templates下建立ftl模板；使用格式${}获取controller传递过来的参数。${object.property}获取对象的属性值
     *
     *  ）
     */

    function onBridgeReady(){
        WeixinJSBridge.invoke(
                'getBrandWCPayRequest', {
                    "appId":"${payResponse.appId}",     //公众号名称，由商户传入
                    "timeStamp":"${payResponse.timeStamp}",         //时间戳，自1970年以来的秒数
                    "nonceStr":"${payResponse.nonceStr}", //随机串
                    "package":"${payResponse.package}",
                    "signType":"MD5",         //微信签名方式：MD5
                    "paySign":"${payResponse.paySign}" //微信签名
                },
                function(res){
                    //if(res.err_msg == "get_brand_wcpay_request:ok" ){
                        // 使用以上方式判断前端返回,微信团队郑重提示：
                        //res.err_msg将在用户支付成功后返回ok，但并不保证它绝对可靠。
                    //}
                    location.href="${returnUrl}";
                });
    }
    if (typeof WeixinJSBridge == "undefined"){
        if( document.addEventListener ){
            document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
        }else if (document.attachEvent){
            document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
            document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
        }
    }else{
        onBridgeReady();
    }

</script>