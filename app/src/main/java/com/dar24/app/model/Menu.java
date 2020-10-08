package com.dar24.app.model;

/**
 * @author Simon Mawole
 * @version 0.1.0
 * @email simonmawole2011@gmail.com
 * @since 0.1.0
 */
public class Menu {

    String title;
    String iconPath;
    Integer iconResource;

    public Menu(String title, String iconPath, Integer iconResource) {
        this.title = title;
        this.iconPath = iconPath;
        this.iconResource = iconResource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public Integer getIconResource() {
        return iconResource;
    }

    public void setIconResource(Integer iconResource) {
        this.iconResource = iconResource;
    }
}
