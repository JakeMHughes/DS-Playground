package com.hughesportal.DSplayground;

public class Keyword {
    private String name;
    private String value;
    private String packageValue;

    public Keyword(String name, String value, String packageValue) {
        this.name = name;
        this.value = value;
        this.packageValue = packageValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPackageValue() {
        return packageValue;
    }

    public void setPackageValue(String packageValue) {
        this.packageValue = packageValue;
    }
}
