package com.sonu.diary.domain.enums;

/**
 * Created by sonu on 11/07/16.
 */
public enum SecurityClearance {
    LOW (0),
    MEDIUM (1),
    HIGH (2);

    private int securityClearanceLevelCode;

    SecurityClearance(int levelCode){

    }

    public int getSecurityClearanceLevelCode(){
        return this.securityClearanceLevelCode;
    }

}
