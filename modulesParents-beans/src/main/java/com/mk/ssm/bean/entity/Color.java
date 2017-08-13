package com.mk.ssm.bean.entity;

/**
 * Created by yaming on 17-4-18.
 */
public class Color {
    private Long id;
    private String title;
    private Integer red;
    private Integer green;
    private Integer blue;
    private String createAt;
    private String updateAt;
    private Integer able;
    //分页查询时用
    private Integer page;
    private Integer pagesize;
    private Long pages;
    private Long totalNum;


    public Color() {
    }

    public Color(Long id, String title, Integer red, Integer green, Integer blue,
                 String createAt, String updateAt, Integer able) {
        this.id = id;
        this.title = title;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.able = able;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getRed() {
        return red;
    }

    public void setRed(Integer red) {
        this.red = red;
    }

    public Integer getGreen() {
        return green;
    }

    public void setGreen(Integer green) {
        this.green = green;
    }

    public Integer getBlue() {
        return blue;
    }

    public void setBlue(Integer blue) {
        this.blue = blue;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public Integer getAble() {
        return able;
    }

    public void setAble(Integer able) {
        this.able = able;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPagesize() {
        return pagesize;
    }

    public void setPagesize(Integer pagesize) {
        this.pagesize = pagesize;
    }

    public Long getPages() {
        return pages;
    }

    public void setPages(Long pages) {
        this.pages = pages;
    }

    public Long getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Long totalNum) {
        this.totalNum = totalNum;
    }

    @Override
    public String toString() {
        return "Color{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", red=" + red +
                ", green=" + green +
                ", blue=" + blue +
                ", createAt='" + createAt + '\'' +
                ", updateAt='" + updateAt + '\'' +
                ", able=" + able +
                '}';
    }
}
