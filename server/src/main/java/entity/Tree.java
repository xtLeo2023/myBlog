package entity;

import java.util.List;

public class Tree {
    private String key;
    private String title;
    private String parentNodeKey;
    private int isParentFlag;
    private int level;
    private String ancestor;
    private List<Tree> children;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParentNodeKey() {
        return parentNodeKey;
    }

    public void setParentNodeKey(String parentNodeId) {
        this.parentNodeKey = parentNodeId;
    }

    public int getIsParentFlag() {
        return isParentFlag;
    }

    public void setIsParentFlag(int isParentFlag) {
        this.isParentFlag = isParentFlag;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getAncestor() {
        return ancestor;
    }

    public void setAncestor(String ancestor) {
        this.ancestor = ancestor;
    }

    public List<Tree> getChildren() {
        return children;
    }

    public void setChildren(List<Tree> children) {  this.children = children; }
    public void addChildren(Tree childNode) {
        this.children.add(childNode);
    }

}

