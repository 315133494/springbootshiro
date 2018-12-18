package com.zhicaili.shiro.mapper;

import com.zhicaili.shiro.pojo.Resources;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhicaili.shiro.pojo.ResourcesVo;
import jdk.internal.dynalink.linker.LinkerServices;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zhicaili
 * @since 2018-12-03
 */
public interface ResourcesMapper extends BaseMapper<Resources> {

    /**
     * 加载用户拥有的权限
     * @param userid
     * @param type
     * @return
     */
    @Select(
           "<script>" +
                    "SELECT re.id,re.name,re.parentId,re.resUrl from resources re " +
                    "left join " +
                    "role_resources rr " +
                    "on re.id=rr.resourcesId " +
                    "LEFT JOIN user_role ur " +
                    "on rr.roleId = ur.roleId " +
                    "WHERE ur.userId=#{userid} " +
                    "<if test='type != null'>" +
                    " AND re.type= #{type} " +
                    "</if>" +
                    "GROUP BY re.id " +
                    "ORDER BY re.sort asc"
            +"</script>"
    )
    List<Resources> loadUserResources(@Param("userid")Integer userid,@Param("type")Integer type);

    /**
     *
     * 查询用户选择的资源
     * @param rid
     * @return
     */
    @Select("        SELECT re.id,re.name,re.parentId,re.resUrl,re.type, " +
            "(CASE WHEN EXISTS(SELECT 1 " +
            "FROM role_resources rr WHERE rr.resourcesId=re.id AND rr.roleId=#{rid}) " +
            "THEN 'true' ELSE 'false' END) AS checked " +
            "FROM resources re " +
            "WHERE re.parentId !=0 " +
            "ORDER BY re.sort ASC ")
    List<ResourcesVo> queryResourcesListWithSelected(@Param("rid")Integer rid);
}
