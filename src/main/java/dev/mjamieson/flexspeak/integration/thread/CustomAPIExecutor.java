package dev.mjamieson.flexspeak.integration.thread;

import java.util.concurrent.ExecutorService;

public interface CustomAPIExecutor {
    void init(ExecutorService executorService);

    ExecutorService getExecutorService();
}
