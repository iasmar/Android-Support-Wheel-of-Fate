package com.iasmar.rome.ui.views.custom.recyclerview;

//TODO  add comments

public class AnimationItem {
    private final String name;
    private final int resourceId;

    public AnimationItem(String name, int resourceId) {
        this.name = name;
        this.resourceId = resourceId;
    }

    public String getName() {
        return name;
    }

    public int getResourceId() {
        return resourceId;
    }
}
