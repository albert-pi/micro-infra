package com.microinfra.commons.lang;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * <p>
 * 树状结构节点信息
 * </p>
 *
 * @author Albert
 * @since 1.0.0
 */
@Data
public class TreeNode implements Serializable {

	private static final long serialVersionUID = 2016149476651451064L;

	/**
	 * 节点编号
	 */
	private String id;

	/**
	 * 节点名称
	 */
	private String name;

	/**
	 * 父节点编号，0表示第一级
	 */
	private String parentId;

	/**
	 * 排序
	 */
	private Integer sort;

	/**
	 * 子节点
	 */
	private List<TreeNode> children;

}
