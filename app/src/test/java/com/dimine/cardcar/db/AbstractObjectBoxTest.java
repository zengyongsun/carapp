package com.dimine.cardcar.db;

import com.dimine.cardcar.data.bean.MyObjectBox;

import org.junit.After;
import org.junit.Before;

import java.io.File;

import io.objectbox.BoxStore;
import io.objectbox.DebugFlags;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/8/23 15:46
 * desc   : 配置 https://docs.objectbox.io/android/android-local-unit-tests
 * version: 1.0
 */
public class AbstractObjectBoxTest {

    protected static final File TEST_DIRECTORY = new File("objectbox-example/test-db");
    protected BoxStore store;

    @Before
    public void setUp() throws Exception {
        // delete database files before each test to start with a clean database
        BoxStore.deleteAllFiles(TEST_DIRECTORY);
        store = MyObjectBox.builder()
                // add directory flag to change where ObjectBox puts its database files
                .directory(TEST_DIRECTORY)
                // optional: add debug flags for more detailed ObjectBox log output
                .debugFlags(DebugFlags.LOG_QUERIES | DebugFlags.LOG_QUERY_PARAMETERS)
                .build();
    }

    @After
    public void tearDown() throws Exception {
        if (store != null) {
            store.close();
            store = null;
        }
        BoxStore.deleteAllFiles(TEST_DIRECTORY);
    }
}
