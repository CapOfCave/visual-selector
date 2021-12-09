package me.kecker.visualselector;

import me.kecker.visualselector.renderer.Renderer;
import me.kecker.visualselector.renderer.TerminalRenderer;

import java.io.IOException;
import java.util.function.Consumer;

public class Enquirer {

    private static Enquirer INSTANCE;

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

    public <T> void doPrompt(Prompt<T> prompt, Consumer<T> callback) {
        prompt.bindKeys(this.inputManager);
        prompt.addChangeListener(() -> {
            this.renderer.clear();
            prompt.render(this.renderer);
        });
        prompt.addCompletionListener(r -> this.inputManager.stop());
        prompt.addCompletionListener(callback);
        this.inputManager.startListening();
        prompt.render(this.renderer);
    }
}
