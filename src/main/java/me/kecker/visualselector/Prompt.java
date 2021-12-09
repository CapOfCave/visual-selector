package me.kecker.visualselector;

import me.kecker.visualselector.renderer.Renderer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

public abstract class Prompt<T> {

    private final Collection<Runnable> changeListeners = new ArrayList<>();
    private final Collection<Consumer<T>> completionListeners = new ArrayList<>();

    public Prompt() {
    }

    public abstract void bindKeys(TerminalManager inputManager);

    public abstract void render(Renderer renderer);

    public void addChangeListener(Runnable changeListener) {
        this.changeListeners.add(changeListener);
    }

    public void addCompletionListener(Consumer<T> completionListener) {
        this.completionListeners.add(completionListener);
    }

    protected void resultObtained(T result) {
        this.completionListeners.forEach(c -> c.accept(result));
    }

    protected void fireChanged() {
        this.changeListeners.forEach(Runnable::run);
    }
}
