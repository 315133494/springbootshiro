package com.zhicaili.shiro.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhicaili.shiro.pojo.Resources;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhicaili.shiro.pojo.ResourcesVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhicaili
 * @since 2018-12-03
 */
public interface ResourcesService extends IService<Resources> {
    /**
     * 查询全部资源
     * @return
     */
    public List<Resources> queryAll();

    /**
     * 加载用户可以访问资源
     * @param userid
     * @param type
     * @return
     */
    public List<Resources> loadUserResources(Integer userid,Integer type);

    /**
     * 查询用户选择的资源
     * @param rid
     */
    List<ResourcesVo> queryResourcesListWithSelected(Integer rid);

    /**
     * 查询资源列表
     * @param resources
     * @param start
     * @param length
     * @return
     */
    IPage<Resources> selectByPage(Resources resources, int start, int length);
}
