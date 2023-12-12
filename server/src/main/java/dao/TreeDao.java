package dao;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entity.Tree;
import util.druidUtil;
import util.paramsUtil;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TreeDao {

    public String doTree(JsonObject params) {

        List<Tree> nodeList = new ArrayList<>();
        Connection connection = null;
        CallableStatement statement = null;
        ResultSet result = null;

        try {
            connection = druidUtil.getConnection();
            statement = paramsUtil.createCallableStatement(connection, params);
            result = statement.executeQuery();

            while (result.next()) {
                Tree node = new Tree();
                node.setKey(result.getString("key"));
                node.setTitle(result.getString("title"));
                node.setParentNodeKey(result.getString("parentnodeid"));
                node.setLevel(result.getInt("level"));
                node.setIsParentFlag(result.getInt("isparentflag"));
                node.setAncestor(result.getString("ancestor"));
                node.setChildren(new ArrayList<Tree>());
                nodeList.add(node);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            druidUtil.close(result, statement, connection);
        }

        Gson gson = new Gson();
        String tree=params.get("tree").getAsString();

        if(tree.equals("new")){
            return gson.toJson(newTreeB(nodeList));
        } else {
            return gson.toJson(nodeList);
        }
    }

    private List<Tree> newTree(List<Tree> nodeList) {
        // 使用 Map 存储节点，以节点的 key 作为键，加速查找
        Map<String, Tree> nodeMap = new HashMap<>();

        // 将所有节点放入 Map
        for (Tree node : nodeList) {
            nodeMap.put(node.getKey(), node);
        }

        List<Tree> treeList = new ArrayList<>();

        // 找到根节点
        for (Tree item : nodeList) {
            if (item.getLevel() == 1) {
                // 直接从 Map 中获取子节点
                item.setChildren(findChildNodes(item.getKey(), nodeMap));
                treeList.add(item);
            }
        }
        return treeList;
    }

    private List<Tree> newTreeB(List<Tree> nodeList) {
        // 使用 Map 存储节点，以节点的 key 作为键，加速查找
        Map<String, Tree> nodeMap = new HashMap<>();

        // 将所有节点放入 Map
        for (Tree node : nodeList) {
            nodeMap.put(node.getKey(), node);
        }

        // 使用并行流处理树节点的构建
        List<Tree> treeList = nodeList.parallelStream()  // 使用并行流
                .filter(node -> node.getLevel() == 1)   // 过滤出树的根节点
                .map(node -> {
                    // 设置节点的子节点
                    node.setChildren(findChildNodes(node.getKey(), nodeMap));
                    return node;  // 返回已设置子节点的节点
                })
                .collect(Collectors.toList());  // 将处理后的节点收集成列表

        return treeList;
    }

    private List<Tree> findChildNodes(String parentKey, Map<String, Tree> nodeMap) {
        List<Tree> children = new ArrayList<>();

        for (Tree node : nodeMap.values()) {
            if (parentKey.equals(node.getParentNodeKey())) {
                // 递归查找子节点的子节点
                node.setChildren(findChildNodes(node.getKey(), nodeMap));
                children.add(node);
            }
        }
        return children;
    }

}
