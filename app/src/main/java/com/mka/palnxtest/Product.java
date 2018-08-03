package com.mka.palnxtest;

/**
 * Created by mostafa kamel on 18/07/2018.
 */

public class Product {
    private String name, desc, key, datasnapShot;
    private Float price, limitUnits, units;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getLimitUnits() {
        return limitUnits;
    }

    public void setLimitUnits(Float limitUnits) {
        this.limitUnits = limitUnits;
    }

    public Float getUnits() {
        return units;
    }

    public void setUnits(Float units) {
        this.units = units;
    }

    public String getDatasnapShot() {
        return datasnapShot;
    }

    public void setDatasnapShot(String datasnapShot) {
        this.datasnapShot = datasnapShot;
    }
}
