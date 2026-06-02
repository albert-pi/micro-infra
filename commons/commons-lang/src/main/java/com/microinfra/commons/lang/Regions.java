package com.microinfra.commons.lang;

import static com.microinfra.commons.lang.TreeUtils.CHILDREN_SETTER;
import static com.microinfra.commons.lang.TreeUtils.PARENT_CHECKER;
import static com.microinfra.commons.lang.TreeUtils.ROOT_CHECKER;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONReader;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <p>
 * 行政区划数据
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
public class Regions {

	@Data
	@AllArgsConstructor
	public static class Region implements Serializable {

		private static final long serialVersionUID = 2016149476651451064L;

		/**
		 * 行政区划编号
		 */
		private String code;

		/**
		 * 行政区划名称
		 */
		private String name;

	}

	private static List<TreeNode> regionInTree;

	public static void loadRegions() {
		InputStream ins = Regions.class.getResourceAsStream("/data/regions.json");
		InputStreamReader insReader = new InputStreamReader(ins, StandardCharsets.UTF_8);

		JSONReader jsonReader = JSONReader.of(insReader);
		jsonReader.startArray();
		List<TreeNode> flatRegionNodes = new ArrayList<>();
		while (jsonReader.nextIfObjectStart()) {
			String area = jsonReader.readObject().toString();
			JSONObject regionObj = JSON.parseObject(area);
			TreeNode areaNode = new TreeNode();
			areaNode.setParentId("0");
			areaNode.setId(regionObj.getString("value"));
			areaNode.setName(regionObj.getString("label"));
			flatRegionNodes.add(areaNode);

			parseRegions(regionObj, flatRegionNodes);
		}
		jsonReader.endArray();
		jsonReader.close();

		regionInTree = TreeUtils.buildTree(flatRegionNodes, ROOT_CHECKER, PARENT_CHECKER, CHILDREN_SETTER);
	}

	private static void parseRegions(JSONObject regionObj, List<TreeNode> flatRegionNodes) {
		JSONArray childAreas = regionObj.getJSONArray("children");
		if (childAreas != null) {
			for (int i = 0; i < childAreas.size(); i++) {
				JSONObject childRegionObj = childAreas.getJSONObject(i);
				TreeNode childRegionNode = new TreeNode();
				childRegionNode.setParentId(regionObj.getString("value"));
				childRegionNode.setId(childRegionObj.getString("value"));
				childRegionNode.setName(childRegionObj.getString("label"));
				flatRegionNodes.add(childRegionNode);

				parseRegions(childRegionObj, flatRegionNodes);
			}
		}
	}

	public static List<Region> getSubRegions(String parentCode) {
		List<TreeNode> targetNodes = null;
		if (parentCode.equals("0")) {
			targetNodes = new ArrayList<>();
			for (int i = 0; i < regionInTree.size(); i++) {
				TreeNode childNode = new TreeNode();
				childNode.setParentId(parentCode);
				childNode.setId(regionInTree.get(i).getId());
				childNode.setName(regionInTree.get(i).getName());

				targetNodes.add(childNode);
			}
		} else {
			for (int i = 0; i < regionInTree.size(); i++) {
				targetNodes = searchChildrenInTree(regionInTree.get(i), parentCode);
				if (targetNodes != null) {
					break;
				}
			}
		}

		List<Region> subRegions = Collections.emptyList();
		if (targetNodes != null) {
			subRegions = targetNodes.stream().map(node -> new Region(node.getId(), node.getName())).collect(Collectors.toList());
		}

		return subRegions;
	}

	private static List<TreeNode> searchChildrenInTree(TreeNode root, String parentId) {
		List<TreeNode> targetNodes = null;
		if (root.getId().equals(parentId)) {
			targetNodes = new ArrayList<>();
			for (int i = 0; i < root.getChildren().size(); i++) {
				TreeNode childNode = new TreeNode();
				childNode.setParentId(root.getId());
				childNode.setId(root.getChildren().get(i).getId());
				childNode.setName(root.getChildren().get(i).getName());

				targetNodes.add(childNode);
			}

			return targetNodes;
		}

		for (TreeNode child : root.getChildren()) {
			targetNodes = searchChildrenInTree(child, parentId);
			if (targetNodes != null) {
				return targetNodes;
			}
		}

		return null;
	}

	public static String getRegionName(String regionCode) {
		TreeNode targetNode = null;
		for (int i = 0; i < regionInTree.size(); i++) {
			targetNode = findNodeInTree(regionInTree.get(i), regionCode);
			if (targetNode != null) {
				break;
			}
		}

		return targetNode != null ? targetNode.getName() : null;
	}

	private static TreeNode findNodeInTree(TreeNode root, String nodeId) {
		if (root == null) {
			return null;
		}

		if (root.getId().equals(nodeId)) {
			return root;
		}

		for (TreeNode child : root.getChildren()) {
			TreeNode node = findNodeInTree(child, nodeId);
			if (node != null) {
				return node;
			}
		}

		return null;
	}
}
