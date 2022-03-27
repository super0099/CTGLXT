package com.yxm.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

public class LayuiTreeVo implements Serializable {
    private static final long serialVersionUID = -5335800146404209396L;
    /*
    title	节点标题	String	未命名
    id	节点唯一索引值，用于对指定节点进行各类操作	String/Number	任意唯一的字符或数字
    field	节点字段名	String	一般对应表字段名
    children	子节点。支持设定选项同父节点	Array	[{title: '子节点1', id: '111'}]
    href	点击节点弹出新窗口对应的 url。需开启 isJump 参数	String	任意 URL
    spread	节点是否初始展开，默认 false	Boolean	true
    checked	节点是否初始为选中状态（如果开启复选框的话），默认 false	Boolean	true
    disabled	节点是否为禁用状态。默认 false	Boolean	false
     */
    private int id;
    private String title;
    private String field;
    private String href;
    private Boolean spread;
    private Boolean checked;
    private Boolean disabled;
    @JsonInclude(JsonInclude.Include.NON_NULL)//注解 属性为NULL   不序列化
    private List<LayuiTreeVo> children;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Boolean getSpread() {
        return spread;
    }

    public void setSpread(Boolean spread) {
        this.spread = spread;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public List<LayuiTreeVo> getChildren() {
        return children;
    }

    public void setChildren(List<LayuiTreeVo> children) {
        this.children = children;
    }
}
