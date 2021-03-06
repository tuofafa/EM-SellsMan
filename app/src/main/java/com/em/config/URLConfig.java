package com.em.config;
/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/10 0010 16:24
 * discrption URL配置类
 */
public class URLConfig {
    //密码登录访问的接口URL
    public static final String PasswordLoginURL = "http://47.111.231.22:8003/app-dologin.html";

    //验证码登录访问的接口URL127.0.0.1:8003/app-dologinsms.html?userName=15810867923&phone=15810867923&ip=127.0.0.1&smsCode=654829&uid=222
    public static final String VerificationCodeLogin = "http://47.111.231.22:8003/app-dologinsms.html";

    //短信发送接口
    public static String sendMSG = "http://47.111.231.22:8003/app-sendVerifySMS.html";

    //注册访问的接口URL
    public static final String RegisterURL = "http://47.111.231.22:8003/app-doregister.html";

    //分类查询商品URL http://47.111.231.22:8003/scene/96.html
    public static final String SPURL = "http://47.111.231.22:8003/scene/";

    //所有商品URL
    public static final String ALLSPURL = "http://47.111.231.22:8003/app-allproduct.html";

    //图片服务器的地址
    public static final String TPURL = "http://solr.em616.cn/ejsimage/";

    //更新个人资料
    public static final String UPDATE_PERSON = "http://47.111.231.22:8003/member/app-saveinfo.html";

    //查询个人信息
    public static final String SELECT_PERSON = "http://47.111.231.22:8003/member/app-info.html";

    //密码重置URL127.0.0.1:8003/app-doforgetpassword.html?name=15810867923&mobile=15810867923&smsCode=160289&newPwd=111111&uid=222
    public static final String RESET_PWD = "http://47.111.231.22:8003/app-doforgetpassword.html";

    //每一个商品的URL
    public static final String PREDUCT_URL = "http://m.emaimed.com/#/product/productdetail?id=";

    public static final String TYPE_PREDUCT_URL = "http://m.emaimed.com/#/seller/sellerindexspecial?id=";

    //累计订单URL
    public static final String LJ_ORDER_URL = "http://47.111.231.22:8003/member/app-sale-orders.html";

    //主页面URL
    public static final String HOME_URL = "http://47.111.231.22:8003/member/app-sale-apply-money.html";

    //添加银行卡URL
    public static final String ADD_BACK_CRAD = "http://47.111.231.22:8003/member/app-sale-finance.html";

    //查看银行卡的添加状态URL
    public static final String SELECT_BANK_STATUS = "http://47.111.231.22:8003/member/app-sale-finance-info.html";

    //提现URL
    public static final String  TX_URL = "http://47.111.231.22:8003/member/app-sale-apply-money-save.html";

    //上传头像URL
    public static final String UPLOAD_TOUXIANG = "http://47.111.231.22:8003/member/app-upload.html";

    //查询个人邀请码URL
    public static final String GRYQ_CODE = "http://47.111.231.22:8003/member/app-sale-finance-info.html";

    //版本检测URL
    public static final String INSPECTION = "http://47.111.231.22:8003/app-version.html";

    //系统更新资源路径 GET
    public static final String SYSTEM_APK = "http://47.111.184.21/app-release.apk";

    //提现记录URL GET
    public static final String TX_RECORD = "http://47.111.231.22:8003/member/app-sale-apply-money.html";

    //商品详情 (GET http://47.111.231.22:8003/product/json/545.html)
    public static final String GOODS_DETAILS = "http://47.111.231.22:8003/product/json/";

    //累计客户URL
    public static final String LJ_CUSTOMER = "http://47.111.231.22:8003/member/app-sale-member1.html";

    //Get http://47.111.231.22:8003/searchJson.html?keyword=独家总代
    public static final String SEARCH_KEYWORD = "http://47.111.231.22:8003/searchJson.html?";
}

