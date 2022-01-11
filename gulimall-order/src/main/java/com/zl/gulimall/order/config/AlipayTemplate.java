package com.zl.gulimall.order.config;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;

import com.zl.gulimall.order.vo.PayVo;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "alipay")
@Component
@Data
public class AlipayTemplate {

    //在支付宝创建的应用的id
    private   String app_id = "2021000118659841";

    // 商户私钥，您的PKCS8格式RSA2私钥
    private  String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCupfNUSSodwpM8st30h0zAoYYydbVNZJbEb9sHSrSluyV1GOSAgs1jDLNu8vN7xIMWlsLozVm6Kfyd+tG845gQIgpuDYuwYJ9XaodcF/0o78AJkSLXoWxpQo1WHVjiH4CbOSJdb4HLhIra0xvRKOTjELcw5wcAIJKvM4X0XbAeQlgDHpX0TAJYtG+uelJSU1U+TdwSVK6Vh1WfttQAjRGsAWWHuk1dJCJFX6Z3KBX5/4IBZdiHRc8fF4iesDZ0nvn18Dhk/kqBI7vInDeht+TxRiAeOaLDdNRvNq40rNuAfr0rzRjz/nMk8uQGXeVaN+vso7U86dLTbj/oZETkHvrbAgMBAAECggEAaN2eMLLJxhrJ+71Oxn/jFxslj/Dbjv/iA0tCkatKsuSfRiNPONbRajL7HphkmKFTMOIPYGfFA6IoW9d5zlg+Oxmld5VMlfg+VOfTJPoGeWkTvurVp/H2PXHHmbh/YS8XMpzoxH+qyL/p/CCcaEeMv8aOqJ9ftbTZPrfKLAOBN3kQd2drzKkA8bueI7/C5LXVeUmlLrr796r1vfWuLn+JkNgHrsu0kRKHlwd7/6wliDr0pAjiayNDxPfWqqRBrarVJ8L0lW3AUOqCQ+R+A1M0yir/+VN28NvflgDHszGuyAcHZmmtmw1IqwfqDLBepS3Dul9M9qK40pT52JOtdJIomQKBgQDw466Jskz8avF5BIe6xH2G6rlq9rkgTnJh7l+jx5zsx8Jl1vFJg1dp7LzvZT7HSDqJZJPnPtQJ+nuiDqeKINtKn5GHtLXAgimDsSyqkMZT4m47fzcusa7fmG2mWGngoJ3CyxEhy47i48X4PG3YG9+Kd33OJRlNTHZCX7tjxzJRfQKBgQC5molXE9+DSTTbaOLgfcFWmqBPuAoApBrvMqsQRBck/Jet1r4aVd3P+IP9POLW7LuJ91kSqa9aLVyLoaNugWQZmF/fOGdCiorMHcRo+ye0gRoKY+deebNfp4vLviUbTNJxHUVqJuAL26i29bX5eDdJnSaIFHCxGx74BsrdOJatNwKBgQDcGKwY0/jTiUMot6LsWyv2YfEYcmWjxwlrwUlRc8lF7x8HveSoroDoH45hjbhWjQAvGN6qoxVKKLtdztX1tx+mhzWJMOwAAO29jdyp263aZ0Egy5O37lFBqxxEqPPUceyzVqdIoZzkYgk4Tr5ufsk40wT3nZ4IqpxC+QVWnWK0YQKBgHPeyDL2kylRAJChkFH58ENy0PYzvPrdeHswNJ85AwHyN3ctgwzJ4mo9iZkE89Cdhg8BvjfqezBe9VGunWsVmDpqaB/hBIYEG9aGFuI7OXR0zJ8D86OFcrgazylpEZ1TfmVjkrxMfL6qaU+fYfXWWR254B5ANII8nj7Vbrxg4aBNAoGBAIdHVvM9DbV/ZQoXfwsYmp3FhThrhhMj0mafTnn7OkrfTJiP9dhXkKSiehzbiq5ARvh7taAwZtwXFpSj6YhSXhEuMfiqMDLnUTwqUk4An+kjZ2F+dUREyXEpSYxgtfBLZnjTiRky4MuAZaNr/t4iMRZLtGSUK+2Ws/3FT3idXfUt";
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    private  String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqrNGKMC2ZI5MVO3oVysVdpjNRX+Qm0yY3XayaLdnGaekqnJCZZJ86bniPshPMYeI6lI3fSLsvygRyGVVKUU4CgJ8EbOzO9T8wuTKQ7a0wF3dsRCYsqSB926Aa5UyJm4URKuaWastoDjUMd52en4kjgxxDDm+W6Oi2r6S/H4cljtF+4T7ZExHoytGBygtYnc8S2ChT48DLYKVXNbaDQ6o975w/L4WyioS+85sUVQDVlw2XngPQ4KkEqyK9V672TbdoFTkaF/IpWvHXG74AanjIJz6fWuX9mahQpvFmhN2LKN586j6kUIRsr0KDWG5LxGRo3eQo8EeplCo4VdKqgQ6oQIDAQAB";
    // 服务器[异步通知]页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    // 支付宝会悄悄的给我们发送一个请求，告诉我们支付成功的信息
    private  String notify_url="http://f0f2-240e-3b7-2e90-44d0-788e-922-205d-66cb.ngrok.io/payed/notify";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    //同步通知，支付成功，一般跳转到成功页
    private  String return_url="http://order.gulimall.com/memberOrder.html";

    // 签名方式
    private  String sign_type = "RSA2";

    // 字符编码格式
    private  String charset = "utf-8";
    //订单超时时间
    private String timeout = "1m";
    // 支付宝网关； https://openapi.alipaydev.com/gateway.do
    private  String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    public  String pay(PayVo vo) throws AlipayApiException {

        //AlipayClient alipayClient = new DefaultAlipayClient(AlipayTemplate.gatewayUrl, AlipayTemplate.app_id, AlipayTemplate.merchant_private_key, "json", AlipayTemplate.charset, AlipayTemplate.alipay_public_key, AlipayTemplate.sign_type);
        //1、根据支付宝的配置生成一个支付客户端
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl,
                app_id, merchant_private_key, "json",
                charset, alipay_public_key, sign_type);

        //2、创建一个支付请求 //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(return_url);
        alipayRequest.setNotifyUrl(notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = vo.getOut_trade_no();
        //付款金额，必填
        String total_amount = vo.getTotal_amount();
        //订单名称，必填
        String subject = vo.getSubject();
        //商品描述，可空
        String body = vo.getBody();

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"timeout_express\":\""+timeout+"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        String result = alipayClient.pageExecute(alipayRequest).getBody();

        //会收到支付宝的响应，响应的是一个页面，只要浏览器显示这个页面，就会自动来到支付宝的收银台页面
        System.out.println("支付宝的响应："+result);

        return result;

    }
}
