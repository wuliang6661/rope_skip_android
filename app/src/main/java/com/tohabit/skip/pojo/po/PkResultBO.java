package com.tohabit.skip.pojo.po;

import java.io.Serializable;

public class PkResultBO implements Serializable {


    /**
     * pkChallengeValue : 10
     * sex : 0
     */

    private int pkChallengeValue;
    private int sex;

    public int getPkChallengeValue() {
        return pkChallengeValue;
    }

    public void setPkChallengeValue(int pkChallengeValue) {
        this.pkChallengeValue = pkChallengeValue;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
