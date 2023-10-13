package com.habtech.ETLHabtech.models;

public enum ResultType {
    DEFAULT, DHIS_HEADER;

    @Override
    public String toString() {
        switch (this){
            case DEFAULT -> {
                return "default";
            }
            case DHIS_HEADER -> {
                return "dhis_header";
            }
        }
        return "default";
    }
}
