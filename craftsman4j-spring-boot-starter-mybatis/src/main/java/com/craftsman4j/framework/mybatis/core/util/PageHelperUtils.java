package com.craftsman4j.framework.mybatis.core.util;

import cn.hutool.core.util.ArrayUtil;
import com.craftsman4j.framework.common.pojo.PageParam;
import com.craftsman4j.framework.common.pojo.PageResult;
import com.craftsman4j.framework.common.pojo.SortingField;
import com.craftsman4j.framework.common.util.servlet.ServletUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PageHelperUtils {

    public static final String PAGE_NUM = "pageNum";

    public static final String PAGE_SIZE = "pageSize";

    public static final String ORDERS = "orders";

    public static <T> Page<T> doPage() {
        return doPage(null);
    }

    /**
     * PageHelper分页
     *
     * @param columnsMap key:前端传过来的字段名，value:表别名.表字段名
     * @param <T>        <T>
     * @return Page<T>
     */
    public static <T> Page<T> doPage(Map<String, String> columnsMap) {
        PageParam pageParam = getPageDTO();
        Page<T> page;
        if (ArrayUtil.isEmpty(pageParam.getOrders())) {
            page = PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        } else {
            page = PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize(), orderBy(pageParam.getOrders(), columnsMap));
        }
        return page;
    }

    public static PageParam getPageDTO() {
        HttpServletRequest request = ServletUtils.getRequest();
        PageParam pageParam = new PageParam();
        assert request != null;
        if (StringUtils.isNotBlank(request.getParameter(PAGE_NUM))) {
            pageParam.setPageNo(Integer.parseInt(request.getParameter(PAGE_NUM)));
        }
        if (StringUtils.isNotBlank(request.getParameter(PAGE_SIZE))) {
            pageParam.setPageSize(Integer.parseInt(request.getParameter(PAGE_SIZE)));
        }
        if (request.getParameterValues(ORDERS) != null) {
            pageParam.setOrders(request.getParameterValues(ORDERS));
        }
        return pageParam;
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

    private static String orderBy(String[] orders, Map<String, String> columnsMap) {
        if (ArrayUtil.isEmpty(orders)) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int x = 0; x < orders.length; x++) {
            String order = orders[x];
            if (StringUtils.isBlank(order)) {
                continue;
            }
            String[] split = order.split(" ");
            String column = split[0].trim();
            String orderType = split[1].trim().toLowerCase();
            if (!(orderType.equals(SortingField.ORDER_ASC) || orderType.equals(SortingField.ORDER_DESC))) {
                throw new IllegalArgumentException("非法的排序策略：" + orderType);
            }
            // 判断列名称的合法性，防止SQL注入。只能是【字母，数字，下划线】
            if (!Pattern.matches(FIELD_REGEXP, column)) {
                throw new IllegalArgumentException("非法的排序字段名称：" + column);
            }
            if (x != 0) {
                stringBuilder.append(", ");
            }
            if (columnsMap != null && columnsMap.get(column) != null) {
                stringBuilder.append(columnsMap.get(column));
            } else {
                // 驼峰转换为下划线
                column = humpConversionUnderscore(column);
                stringBuilder.append("`").append(column).append("`");
            }
            stringBuilder.append(" ").append(order);
        }
        return stringBuilder.toString();
    }

    private static String humpConversionUnderscore(String value) {
        StringBuilder stringBuilder = new StringBuilder();
        char[] chars = value.toCharArray();
        for (char character : chars) {
            if (Character.isUpperCase(character)) {
                stringBuilder.append("_");
                character = Character.toLowerCase(character);
            }
            stringBuilder.append(character);
        }
        return stringBuilder.toString();
    }
}