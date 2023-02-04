package dev.mjamieson.flexspeak.integration.thread;

import java.util.concurrent.ExecutorService;

public class CustomAPIExecutorConcrete implements CustomAPIExecutor {

    private ExecutorService executorService;
    private final String apiIntegrationName;

    public CustomAPIExecutorConcrete(String apiIntegrationName) {
        this.apiIntegrationName = apiIntegrationName;
    }

    @Override
    public void init(ExecutorService executorService) {
        this.executorService = executorService;
        setNameOfThread(apiIntegrationName);
    }

    private void setNameOfThread(String threadName) {
        this.executorService.submit(() -> {
            final Thread currentThread = Thread.currentThread();
            final String oldName = currentThread.getName();
            currentThread.setName(threadName + " " + oldName);

        });
    }
    @Override
    public ExecutorService getExecutorService() {
        return this.executorService;
    }


}
