package org.apache.shiro.realm;

import cn.edu.hdu.lab505.innovation.domain.Account;
import cn.edu.hdu.lab505.innovation.domain.Role;
import net.sf.ehcache.Cache;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.web.filter.authc.StatelessToken;


public class StatelessRealm extends AuthorizingRealm {

    Cache tokenCache;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Account a = (Account) principals.getPrimaryPrincipal();
        for (Role r : a.getRoles()) {
            authorizationInfo.addRole(r.getName());
        }
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken token) throws AuthenticationException {
        StatelessToken tk = (StatelessToken) token;
        Account a = (Account) tokenCache.get((String) tk.getPrincipal())
                .getObjectValue();
        return new SimpleAuthenticationInfo(a, tk.getCredentials(), getName());
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof StatelessToken;
    }

    public Cache getTokenCache() {
        return tokenCache;
    }

    public void setTokenCache(Cache tokenCache) {
        this.tokenCache = tokenCache;
    }

}
