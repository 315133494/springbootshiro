package com.zhicaili.shiro.mapper;

import com.zhicaili.shiro.pojo.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhicaili
 * @since 2018-12-03
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 根据角色id查询用户id
     * @param roleId
     * @return
     */
    @Select("select userId from user_role where roleId =  #{roleId}")
    List<Integer> findUserIdByRoleId(@Param("roleId") Integer roleId);
}
