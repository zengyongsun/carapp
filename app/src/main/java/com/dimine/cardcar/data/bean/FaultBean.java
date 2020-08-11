package com.dimine.cardcar.data.bean;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/6/14 15:45
 * desc   :
 * version: 1.0
 */
@Entity
public class FaultBean {

    public FaultBean() {
    }

    public FaultBean(String desc, boolean select, int yesImg, int noImg, String effect, int loadId) {
        this.desc = desc;
        this.select = select;
        this.yesImg = yesImg;
        this.noImg = noImg;
        this.effect = effect;
        this.loadId = loadId;
    }

    @Id(assignable = true)
    public long id;

    public int type;

    public String desc;

    public int loadId;

    public int yesImg;

    public int noImg;

    public boolean select;

    /**
     * A不影响生产  B影响生产
     */
    public String effect;

}
