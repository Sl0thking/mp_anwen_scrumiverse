package com.scrumiverse.model.scrumFeatures;

/**
 * Enum of Moscow (Must, Should, Could, Wont) States
 * @author Lasse Jacobs
 * @version 19.04.2016
 */
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

