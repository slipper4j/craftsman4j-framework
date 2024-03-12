package com.craftsman4j.framework.mybatis.core.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.craftsman4j.framework.common.pojo.PageParam;
import com.craftsman4j.framework.common.pojo.PageResult;
import com.craftsman4j.framework.common.pojo.SortablePageParam;
import com.craftsman4j.framework.common.pojo.SortingField;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PageHelperUtils {

    public static <T> Page<T> startPage(PageParam pageParam) {
        return startPage(pageParam, null);
    }

    /**
     * PageHelper分页
     *
     * @param pageParam     pageParam
     * @param orderFieldMap key:前端传过来的字段名，value:表别名.表字段名
     * @param <T>           <T>
     * @return Page<T>
     */
    public static <T> Page<T> startPage(PageParam pageParam, Map<String, String> orderFieldMap) {
        Page<T> page;
        SortablePageParam sortablePageParam;
        if (pageParam instanceof SortablePageParam &&
                CollUtil.isNotEmpty((sortablePageParam = (SortablePageParam) pageParam).getSortingFields())) {
            page = PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize(), filterOrderBy(sortablePageParam.getSortingFields(), orderFieldMap));
        } else {
            page = PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        }
        return page;
    }

    public static <T> PageResult<T> toResult(List<T> pageList) {
        PageInfo<T> pageInfo = new PageInfo<>(pageList);
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setTotal(pageInfo.getTotal());
        pageResult.setList(pageInfo.getList());
        return pageResult;
    }

    public static <T, R> PageResult<R> toResult(List<T> pageList, Function<? super T, ? extends R> mapper) {
        return toResult(pageList.stream().map(mapper).collect(Collectors.toList()));
    }

    /**
     * 字段名，数字字母下划线
     */
    public static final String FIELD_REGEXP = "([a-zA-Z0-9_]+)";

    /**
     * 过滤排序字段，防止SQL注入
     *
     * @param sortingFields sortingFields
     * @param columnsMap    columnsMap
     * @return
     */
    public static String filterOrderBy(List<SortingField> sortingFields, Map<String, String> columnsMap) {
        if (CollUtil.isEmpty(sortingFields)) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < sortingFields.size(); i++) {
            SortingField order = sortingFields.get(i);
            String field = order.getField();
            String orderType = order.getOrder().toLowerCase();
            if (!(orderType.equals(SortingField.ORDER_ASC) || orderType.equals(SortingField.ORDER_DESC))) {
                throw new IllegalArgumentException("非法的排序策略：" + orderType);
            }
            // 判断列名称的合法性，防止SQL注入。只能是【字母，数字，下划线】
            if (!Pattern.matches(FIELD_REGEXP, field)) {
                throw new IllegalArgumentException("非法的排序字段名称：" + field);
            }
            if (i != 0) {
                stringBuilder.append(", ");
            }
            if (columnsMap != null && columnsMap.get(field) != null) {
                stringBuilder.append(columnsMap.get(field));
            } else {
                // 驼峰转换为下划线
                stringBuilder.append("`").append(StrUtil.toUnderlineCase(field)).append("`");
            }
            stringBuilder.append(" ").append(order);
        }
        return stringBuilder.toString();
    }
}
