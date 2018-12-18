package com.zhicaili.shiro.realms;

import com.zhicaili.shiro.pojo.Resources;
import com.zhicaili.shiro.pojo.User;
import com.zhicaili.shiro.service.ResourcesService;
import com.zhicaili.shiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.util.ByteSource;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.*;

public class ShiroRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Autowired
    private ResourcesService resourcesService;
    @Autowired
    private RedisSessionDAO redisSessionDAO;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("授权===============================");
        User user = (User) SecurityUtils.getSubject().getPrincipal();//获得用户对象
        Integer userid = user.getId();//用户id
        Integer type = null;//资源类型
        List<Resources> resources = resourcesService.loadUserResources(userid, type);
        // 权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        for (Resources resource : resources) {
            info.addStringPermission(resource.getResUrl());
            System.out.println(resource.getResUrl());
        }
        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("doGetAuthorizationInfo........");

        //1、从PrincipalCollection中获取用户登录用户的信息
        String username = (String) token.getPrincipal();
        //2、调用数据库方法，从数据库中查询usernaem 对应的用户记录
        User user = userService.selectByUsername(username);
        //3、若用户不存在，则可以抛出UnknowAccountException异常
        if (user == null) {
            throw new UnknownAccountException("用户不存在");//用户不存在
        }
        //4、根据用户信息的情况，决定是否需要抛出其它的AuthenticationException异常
        if (0 == user.getEnable()) {
            throw new LockedAccountException("用户被锁定");//用户被锁定
        }

        //5、返回SimpleAuthorizationInfo
        //（1)、principal:认证的实体信息，可以是username，也可以是数据表对应的服务的实体类对象
        Object principal = user;
        //（2）、cerdentials：密码
        Object cerdentials = user.getPassword();
        //（3）、盐值
        ByteSource cerdentialsSalt = ByteSource.Util.bytes(username);
        //（4）、realmName: 当前realm对象的name，调用父类的getName（）方法即可
        String realmName = getName();
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(principal, cerdentials, cerdentialsSalt, realmName);

        //6\当验证通过后，把用户信息放在session里
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("userSession", user);
        session.setAttribute("userSessionId", user.getId());

        return simpleAuthenticationInfo;
    }


    /**
     * 根据userId 清除当前session存在的用户的权限缓存
     * @param userIds  已经修改了权限的userId
     */
    public void clearUserAuthByUserId(List<Integer> userIds) {
        if (null == userIds || userIds.size() == 0) return;
        //获取所有session
        Collection<Session> sessions = redisSessionDAO.getActiveSessions();
        //定义返回
        List<SimplePrincipalCollection> list = new ArrayList<SimplePrincipalCollection>();

        for (Session session : sessions) {
            //获取session登录信息
            Object obj = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if (null != obj && obj instanceof SimplePrincipalCollection) {
                //强转
                SimplePrincipalCollection spc = (SimplePrincipalCollection) obj;
                //判断 用户，匹配用户id
                obj = spc.getPrimaryPrincipal();
                if (null != obj && obj instanceof User) {
                    User user = (User) obj;
                    System.out.println("user   " + user);
                    //比较用户id，符合即加入集合
                    if (null != user && userIds.contains(user.getId())) {
                        list.add(spc);
                    }
                }
            }
        }


        RealmSecurityManager securityManager = (RealmSecurityManager) SecurityUtils.getSecurityManager();
        ShiroRealm shiroRealm = (ShiroRealm) securityManager.getRealms().iterator().next();
        for (SimplePrincipalCollection simplePrincipalCollection : list) {
            shiroRealm.clearCachedAuthorizationInfo(simplePrincipalCollection);
        }
    }
}
