package com.craftsman4j.framework.common.pojo;

import java.util.List;

/**
 * @author zhougang
 */
public interface TreeNode<V, T extends TreeNode<V, T>> {
    V getId();

    V getParentId();

    List<T> getChildren();

    void setChildren(List<T> children);
}
