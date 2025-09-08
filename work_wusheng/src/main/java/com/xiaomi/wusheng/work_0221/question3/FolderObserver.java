package com.xiaomi.wusheng.work_0221.question3;

import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FolderObserver implements Subject {
    private List<Observer> observers = new ArrayList<>();
    private Path directoryPath;
    private ExecutorService executorService;
    private boolean isRunning;

    public FolderObserver(String directory) {
        this.directoryPath = Paths.get(directory);
        this.executorService = Executors.newSingleThreadExecutor();
        this.isRunning = false;
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String eventType, String fileName) {
        for (Observer observer : observers) {
            observer.update(eventType, fileName);
        }
    }

    public void startObserver() throws IOException {
        if (isRunning) {
            System.out.println("监控已在运行中...");
            return;
        }

        isRunning = true;
        executorService.submit(this::watchDirectory);

        System.out.println("开始监控目录: " + directoryPath);
    }

    private void watchDirectory() {
        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
            directoryPath.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);

            while (isRunning) {
                WatchKey key;
                try {
                    key = watchService.poll(30, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }

                if (key == null) continue;

                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();
                    Path fileName = (Path) event.context();

                    if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                        notifyObservers("CREATE", fileName.toString());
                    } else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
                        notifyObservers("DELETE", fileName.toString());
                    } else if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
                        notifyObservers("MODIFY", fileName.toString());
                    }
                }

                boolean valid = key.reset();
                if (!valid) {
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("监控过程中发生错误: " + e.getMessage());
        } finally {
            stopObserver();
        }
    }

    public void stopObserver() {
        isRunning = false;
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
