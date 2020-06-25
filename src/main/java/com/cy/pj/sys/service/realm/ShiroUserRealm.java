package com.cy.pj.sys.service.realm;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.cy.pj.sys.dao.SysMenuDao;
import com.cy.pj.sys.dao.SysRoleMenuDao;
import com.cy.pj.sys.dao.SysUserDao;
import com.cy.pj.sys.dao.SysUserRoleDao;
import com.cy.pj.sys.entity.SysUser;
/**基于此对象获取用户认证和授权信息并进行封装*/

public class ShiroUserRealm extends AuthorizingRealm {
	
	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	
	@Autowired
	private SysMenuDao sysMenuDao;
    /**负责授权信息的获取和封装:
     * 为了提高授权性能，还可以将用户权限信息进行缓存，具体缓存对象
     * 底层使用的是SoftHashMap，这个容器的key为当前用户身份，doGetAuthorizationInfo方法
     * 的返回值作为value存储到SoftHashMap
     * */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("==doGetAuthorizationInfo==");
		//1.获取登陆用户信息
		SysUser user=(SysUser)principals.getPrimaryPrincipal();
		//2.基于登陆用户id获取用户角色id并进行判定。
		List<Integer> roleIds=sysUserRoleDao.findRoleIdsByUserId(user.getId());
		if(roleIds==null||roleIds.size()==0)
			throw new AuthorizationException();
		//3.基于角色id获取角色对应的菜单id并进行校验.
		List<Integer> menuIds=sysRoleMenuDao.findMenuIdsByRoleIds(roleIds);
		if(menuIds==null||menuIds.size()==0)
			throw new AuthorizationException();
		//4.基于菜单id获取菜单授权标识(permisssion)
		List<String> permissions=sysMenuDao.findPermissions(menuIds);
		if(permissions==null||permissions.size()==0)
			throw new AuthorizationException();
		//5.对权限标识信息进行封装
		Set<String> stringPermissions=new HashSet<>();
		for(String per:permissions) {
			if(!StringUtils.isEmpty(per)) {
				stringPermissions.add(per);
			}
		}
		System.out.println("stringPermissions:"+stringPermissions);
		SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
		info.setStringPermissions(stringPermissions);
		return info;
	}
    /**负责认证信息的获取和封装*/
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		//1.获取用户输入的用户名
		UsernamePasswordToken upToken=(UsernamePasswordToken)token;
		String username=upToken.getUsername();
		System.out.println("doGetAuthenticationInfo.username="+username);
		//2.基于用户名获取用户对象
		SysUser user=sysUserDao.findUserByUserName(username);
		//3.判定用户对象是否存在
		if(user==null)throw new UnknownAccountException();
		//4.判定用户是否有效(是否被锁定)
		if(user.getValid()==0)throw new LockedAccountException();
		//5.封装用户认证信息
		ByteSource credentialsSalt=ByteSource.Util.bytes(user.getSalt());
		SimpleAuthenticationInfo info=
		new SimpleAuthenticationInfo(
				user,//principal 用户身份
				user.getPassword(), //hashedCredentials 已加密用户凭证
				credentialsSalt,//credentialsSalt 凭证盐
				getName());//real名称
		return info;//此对象会返回给SecurityManager对象
	}
	/**负责获取加密凭证匹配器对象*/
	@Override
	public CredentialsMatcher getCredentialsMatcher() {
		HashedCredentialsMatcher hMatcher=new HashedCredentialsMatcher();
		hMatcher.setHashAlgorithmName("MD5");
		hMatcher.setHashIterations(1);
		return hMatcher;
	}
	

	
}





