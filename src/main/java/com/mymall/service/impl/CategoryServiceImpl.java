package com.mymall.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mymall.common.ServerResponse;
import com.mymall.dao.CategoryMapper;
import com.mymall.pojo.Category;
import com.mymall.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service("CategoryService")
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 添加品类
     * @param categoryName
     * @param parentId
     * @return
     */
    @Override
    public ServerResponse<String> addCategory(String categoryName, Integer parentId) {
        if (parentId == null && StringUtils.isBlank(categoryName)){
            return ServerResponse.createByErrorMessage("添加品类参数错误");
        }
        Category category = new Category();
        category.setParentId(parentId);
        category.setName(categoryName);
        //新添加的品类状态
        category.setStatus(true);
        int insertCount = categoryMapper.insertSelective(category);
        if (insertCount > 0){
            return ServerResponse.createBySuccess("添加品类成功");
        }
        return ServerResponse.createByErrorMessage("添加品类失败");
    }

    @Override
    public ServerResponse<String> setCategory(String categoryName, Integer categoryId) {
        if (categoryId == null && StringUtils.isBlank(categoryName)){
            return ServerResponse.createByErrorMessage("修改品类名称参数错误");
        }
        Category category = new Category();
        category.setId(categoryId);
        category.setName(categoryName);
        int updateCount = categoryMapper.updateByPrimaryKeySelective(category);
        if (updateCount > 0){
            return ServerResponse.createBySuccessMessage("更新品类名称成功");
        }
        return ServerResponse.createByErrorMessage("更新品类名称失败");
    }

    /**
     * 获取子节点
     * @param categroyId
     * @return
     */
    @Override
    public ServerResponse<List<Category>> getChildrenParallelCategory(Integer categroyId) {
        List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(categroyId);
        if (CollectionUtils.isEmpty(categoryList)){
            log.info("未找到当前分类的子节点");
        }
        return ServerResponse.createBySuccess(categoryList);
    }

    /**
     * 获取本节点及孩子节点的ID
     * @param categoryId
     * @return
     */
    @Override
    public ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId) {
        Set<Category> categorySet = Sets.newHashSet();
        getCategoryById(categorySet, categoryId);
        //只存放子节点的id
        List<Integer> categoryList = Lists.newArrayList();
        for (Category category : categorySet){
            categoryList.add(category.getId());
        }
        return ServerResponse.createBySuccess(categoryList);
    }

    /**
     *
     * @param categorySet 初始set,Category需要重写hashCode 和 equals方法
     * @param categoryId
     * @return
     */
    private Set<Category> getCategoryById(Set categorySet, Integer categoryId){
        //添加当前节点
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category != null){
            categorySet.add(category);
        }
        //查询子节点
        List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        //终止条件：集合遍历完
        for (Category item : categoryList){
            //递归添加
            getCategoryById(categorySet, item.getId());
        }
        return categorySet;
    }
}
