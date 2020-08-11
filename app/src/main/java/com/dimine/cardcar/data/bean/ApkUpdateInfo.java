package com.dimine.cardcar.data.bean;

import java.io.File;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class ApkUpdateInfo {

    @Id(assignable = true)
    public long id;

    public String apkFile;

    public String version;


}
