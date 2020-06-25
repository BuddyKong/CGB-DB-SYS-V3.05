package com.cy.pj.sys.dao;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SysUserRoleDao {
	@Select("select role_id from sys_user_roles where user_id=#{userId}")
	List<Integer> findRoleIdsByUserId(Integer userId);
	/**
	 * 保存用户和角色关系数据
	 * @param userId
	 * @param roleIds
	 * @return
	 */
	 int insertObjects(Integer userId,Integer[]roleIds);
	/**
	 * 基于用户id删除用户和角色关系数据
	 * @param id
	 * @return
	 */
	 @Delete("delete from sys_user_roles where user_id=#{id}")
	  int deleteObjectsByUserId(Integer id);
     /**
      * 基于角色id删除用户和角色关系数据
      * @param id
      * @return
      */
	 @Delete("delete from sys_user_roles where role_id=#{id}")
	 int deleteObjectsByRoleId(Integer id);
}
