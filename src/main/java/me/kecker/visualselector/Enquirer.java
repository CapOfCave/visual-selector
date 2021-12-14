package me.kecker.visualselector;

import me.kecker.visualselector.renderer.Renderer;
import me.kecker.visualselector.renderer.TerminalRenderer;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class Enquirer {

    private static Enquirer INSTANCE;

    public static <T> T prompt(Prompt<T> prompt) throws InterruptedException {
        try {
            return promptAsync(prompt).toCompletableFuture().get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e); // no checked exceptions expected
        }
    }

    public static <T> CompletionStage<T> promptAsync(Prompt<T> prompt) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        CompletionStage<T> completionStage = promptAsync(prompt, executorService);
        executorService.shutdown();
        return completionStage;
    }

    public static <T> CompletionStage<T> promptAsync(Prompt<T> prompt, ExecutorService executorService) {
        CompletableFuture<T> completableFuture = new CompletableFuture<>();
        executorService.submit(() -> {
            Enquirer instance = Enquirer.getInstance();
            instance.setUp();
            instance.doPrompt(prompt, result -> {
                instance.close();
                completableFuture.complete(result);
            });
        });
        return completableFuture;
    }

    public static Enquirer getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Enquirer();
        }
        return INSTANCE;
    }

    private final TerminalManager inputManager;

    private final Renderer renderer;

    public Enquirer() {
        try {
            this.inputManager = new TerminalManager();
            this.renderer = new TerminalRenderer();
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize Enquirer", e);
        }

    }

    public void setUp() {
        // TODO remove if unnecessary
    }

    public void close() {
        // TODO remove if unnecessary
    }

    private <T> void doPrompt(Prompt<T> prompt, Consumer<T> callback) {
        prompt.bindKeys(this.inputManager);
        prompt.addChangeListener(() -> {
            this.renderer.clear();
            prompt.render(this.renderer);
        });
        prompt.addCompletionListener(r -> this.inputManager.stopListening());
        prompt.addCompletionListener(callback);
        this.inputManager.startListening();
        prompt.render(this.renderer);
    }
}
