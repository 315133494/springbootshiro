package com.zhicaili.shiro.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhicaili.shiro.pojo.Resources;
import com.zhicaili.shiro.mapper.ResourcesMapper;
import com.zhicaili.shiro.pojo.ResourcesVo;
import com.zhicaili.shiro.pojo.Role;
import com.zhicaili.shiro.pojo.User;
import com.zhicaili.shiro.service.ResourcesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhicaili
 * @since 2018-12-03
 */
@Service
public class ResourcesServiceImpl extends ServiceImpl<ResourcesMapper, Resources> implements ResourcesService {
    @Autowired
    private ResourcesMapper resourcesMapper;

    /**
     * 查询全部资源
     *
     * @return
     */
    public List<Resources> queryAll() {
        List<Resources> resources = resourcesMapper.selectList(null);
        return resources;
    }

    /**
     * 加载用户可以访问资源
     *
     * @param userid
     * @param type
     * @return
     */
    @Override
    public List<Resources> loadUserResources(Integer userid, Integer type) {
        List<Resources> resources = resourcesMapper.loadUserResources(userid, type);
        return resources;
    }

    /**
     * 查询用户选择的资源
     *
     * @param rid
     * @return
     */
    @Override
    public List<ResourcesVo> queryResourcesListWithSelected(Integer rid) {
        List<ResourcesVo> resourcesVoList= resourcesMapper.queryResourcesListWithSelected(rid);
        return resourcesVoList;
    }

    @Override
    public IPage<Resources> selectByPage(Resources resources, int start, int length) {

        int current = start / length + 1;//当前页
        Wrapper<Resources> wrapper = new QueryWrapper<>();

        IPage<Resources> page = new Page<>(current, length);

        IPage<Resources> resourcesIPage = resourcesMapper.selectPage(page, null);
        return resourcesIPage;
    }
}
