package com.dimine.cardcar.utils;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/6/13 10:53
 * desc   :
 * version: 1.0
 */
public class CallerRunsPolicy implements RejectedExecutionHandler {

    /**
     * Creates a {@code CallerRunsPolicy}.
     */
    public CallerRunsPolicy() {
    }

    /**
     * Executes task r in the caller's thread, unless the executor
     * has been shut down, in which case the task is discarded.
     *
     * @param r the runnable task requested to be executed
     * @param e the executor attempting to execute this task
     */
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
        if (!e.isShutdown()) {
            r.run();
        }
    }
}
