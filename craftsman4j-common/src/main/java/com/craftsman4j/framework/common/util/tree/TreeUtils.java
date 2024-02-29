package com.craftsman4j.framework.common.util.tree;

import cn.hutool.core.collection.CollectionUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author zhougang
 */
public class TreeUtils {

    /**
     * list转tree，指定根节点
     *
     * @param nodes         nodes
     * @param rootPredicate rootPredicate
     * @param <V>           ID类型
     * @param <T>           节点类型
     * @return
     */
    public static <V, T extends TreeNode<V, T>> List<T> buildTree(List<T> nodes, Predicate<T> rootPredicate) {
        // 第 1 步：将节点存储在映射中
        Map<V, T> nodeMap = nodes.stream().collect(Collectors.toMap(T::getId, Function.identity()));

        // 第 2 步：设置父子关系
        for (T node : nodes) {
            if (node.getParentId() != null && nodeMap.containsKey(node.getParentId())) {
                if (nodeMap.get(node.getParentId()).getChildren() == null) {
                    nodeMap.get(node.getParentId()).setChildren(new ArrayList<>());
                }
                nodeMap.get(node.getParentId()).getChildren().add(node);
            }
        }

        // 第 3 步: 收集根节点
        return nodeMap.values().stream()
                .filter(rootPredicate)
                .collect(Collectors.toList());
    }

    /**
     * list转tree，只要parentId找不到对应节点，就认为是根节点
     *
     * @param nodes nodes
     * @param <V>   ID类型
     * @param <T>   节点类型
     * @return
     */
    public static <V, T extends TreeNode<V, T>> List<T> buildTree(List<T> nodes) {
        // 第 1 步：将节点存储在映射中
        Map<V, T> nodeMap = nodes.stream().collect(Collectors.toMap(T::getId, Function.identity()));

        List<T> treeNodeList = new ArrayList<>();
        // 第 2 步：设置父子关系
        for (T node : nodes) {
            if (node.getParentId() != null && nodeMap.containsKey(node.getParentId())) {
                if (nodeMap.get(node.getParentId()).getChildren() == null) {
                    nodeMap.get(node.getParentId()).setChildren(new ArrayList<>());
                }
                nodeMap.get(node.getParentId()).getChildren().add(node);
            } else {
                treeNodeList.add(node);
            }
        }
        return treeNodeList;
    }

    /**
     * 有序获取树的所有子节点
     *
     * @param treeList 树
     * @return list所有子节点
     */
    public static <V, T extends TreeNode<V, T>> List<T> getChildrenList(List<T> treeList) {
        List<T> result = new ArrayList<>();
        getChildrenList(result, treeList);
        return result;
    }

    private static <V, T extends TreeNode<V, T>> void getChildrenList(List<T> result, List<T> treeList) {
        Set<V> memorandum = new HashSet<>();
        if (CollectionUtil.isNotEmpty(treeList)) {
            for (T node : treeList) {
                V id = node.getId();
                if (!memorandum.contains(id)) {
                    result.add(node);
                    memorandum.add(id);
                }
                getChildrenList(result, node.getChildren());
            }
        }
    }

    /**
     * 根据ID获取树上的某个节点
     *
     * @param id       id
     * @param treeList 树
     * @return T节点
     */
    public static <V, T extends TreeNode<V, T>> T getNodeById(V id, List<T> treeList) {
        if (treeList != null && !treeList.isEmpty()) {
            for (T node : treeList) {
                V nodeId = node.getId();
                if (nodeId.equals(id)) {
                    return node;
                }
                T nodeById = getNodeById(id, node.getChildren());
                if (nodeById != null) {
                    return nodeById;
                }
            }
        }
        return null;
    }

    public interface TreeNode<V, T extends TreeNode<V, T>> {
        V getId();

        V getParentId();

        List<T> getChildren();

        void setChildren(List<T> children);
    }
}
