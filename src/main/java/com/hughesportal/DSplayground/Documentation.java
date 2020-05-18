package com.hughesportal.DSplayground;

public class Documentation {

    private String nav;
    private String docs;


    Documentation(){}

    public Documentation(String nav, String docs) {
        this.nav = nav;
        this.docs = docs;
    }

    public String getNav() {
        return nav;
    }

    public void setNav(String nav) {
        this.nav = nav;
    }

    public String getDocs() {
        return docs;
    }

    public void setDocs(String docs) {
        this.docs = docs;
    }
}
