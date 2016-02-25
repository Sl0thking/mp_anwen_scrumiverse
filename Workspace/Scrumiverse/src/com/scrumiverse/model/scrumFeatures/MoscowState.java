package com.scrumiverse.model.scrumFeatures;


public enum MoscowState {
	Must, Should, Could, Wont;

    private String value;
    
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return (String) this.name();
    }
}

