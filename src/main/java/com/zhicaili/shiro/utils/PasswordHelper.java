package com.zhicaili.shiro.utils;

import com.zhicaili.shiro.pojo.User;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class PasswordHelper {
    private String hashAlgorithmName="MD5";//使用md5加密
    private int hashIterations=1024;//加密次数

    /**
     * md5加密方法
     * @param user
     */
    public void encryptPassword(User user ){
        String newPassword = new SimpleHash(hashAlgorithmName, user.getPassword(), ByteSource.Util.bytes(user.getUsername()), hashIterations).toHex();
        user.setPassword(newPassword);
    }
    /**
     * 返回md5加密方法
     * @param user
     */
    public String md5Password(User user ){
        String newPassword = new SimpleHash(hashAlgorithmName, user.getPassword(), ByteSource.Util.bytes(user.getUsername()), hashIterations).toHex();
        return newPassword;
    }
    public static void main(String[] args) {
        PasswordHelper passwordHelper = new PasswordHelper();
        User user = new User();
        user.setUsername("test04");
        user.setPassword("test04");
        passwordHelper.encryptPassword(user);
        System.out.println(user);
    }
}
