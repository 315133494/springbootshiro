package com.zhicaili.shiro.mapper;

import com.zhicaili.shiro.pojo.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhicaili.shiro.pojo.RoleVo;
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
public interface RoleMapper extends BaseMapper<Role> {

    @Select("<script>"+
            "SELECT r.id,r.roleDesc," +
            "      (CASE WHEN " +
            "          (SELECT ur.roleId FROM user_role ur WHERE ur.userId= #{id} AND ur.roleId = r.id) " +
            "      THEN 1 ELSE 0 END) " +
            "        AS selected " +
            "            FROM role r"
    +"</script>")
    List<RoleVo> queryRoleListWithSelected(@Param("id") Integer uid);
}
