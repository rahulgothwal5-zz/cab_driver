package com.oxycab.provider.data.network.model;

import com.google.gson.annotations.Expose;

public class Panic {
    @Expose
    private String panic;

    public String getPanic() {
        return panic;
    }

    public void setPanic(String panic) {
        this.panic = panic;
    }
}
