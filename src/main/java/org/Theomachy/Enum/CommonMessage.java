package org.Theomachy.Enum;

public enum CommonMessage {
    BLACKLIST("블랙리스트");

    private final String message;

    CommonMessage(String message) {
        this.message = message;
    }
    public String getMessage(){
        return this.message;
    }
}
