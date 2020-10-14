package com.em.pojo;

public class User {

    private Integer accountId;          //用户ID
    private String accountName;         //账户名
    private String password;            //密码
    private String phoneNum;            //电话号码
    private String verificationCode;    //验证码
    private String ipAddress;           //ip地址
    private String headImg;             //用户头像
    private String weChat;              //微信号
    private String nickName;            //昵称

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getWeChat() {
        return weChat;
    }

    public void setWeChat(String weChat) {
        this.weChat = weChat;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "User{" +
                "accountId=" + accountId +
                ", accountName='" + accountName + '\'' +
                ", password='" + password + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", verificationCode='" + verificationCode + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", headImg='" + headImg + '\'' +
                ", weChat='" + weChat + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
