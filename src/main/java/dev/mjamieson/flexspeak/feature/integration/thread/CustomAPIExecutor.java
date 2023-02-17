package dev.mjamieson.flexspeak.feature.integration.thread;

import java.util.concurrent.ExecutorService;

public interface CustomAPIExecutor {
    void init(ExecutorService executorService);

    ExecutorService getExecutorService();
}
