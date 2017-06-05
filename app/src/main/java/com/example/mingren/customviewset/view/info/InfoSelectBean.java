package com.example.mingren.customviewset.view.info;

/**
 * Created by vincent on 2017/5/31.
 */

public class InfoSelectBean {
    private boolean isSelected;
    private String itemName;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public InfoSelectBean(boolean isSelected, String itemName) {
        this.isSelected = isSelected;
        this.itemName = itemName;
    }
}
