package me.kecker.visualselector;

import me.kecker.visualselector.renderer.Renderer;

import java.util.function.Consumer;
import java.util.function.Function;

public class SelectorBuilder<T> {

    private final Renderer renderer;
    private final TerminalManager inputManager;

    private String prompt;
    private T[] options;
    private Function<T, String> toStringFunction = Object::toString;
    private String pointer;
    private String activePointer;
    private Consumer<T> onSelect;
    
    public SelectorBuilder(Renderer renderer, TerminalManager inputManager) {
        this.renderer = renderer;
        this.inputManager = inputManager;
    }

    public Selector<T> build() {
        return new Selector<>(
                this.prompt,
                this.options,
                this.toStringFunction,
                this.activePointer,
                this.pointer,
                this.onSelect,
                this.renderer,
                this.inputManager);
    }

    public SelectorBuilder<T> prompt(String prompt) {
        this.prompt = prompt;
        return this;
    }

    public SelectorBuilder<T> options(T... options) {
        this.options = options;
        return this;
    }

    public SelectorBuilder<T> toStringFunction(Function<T, String> toStringFunction) {
        this.toStringFunction = toStringFunction;
        return this;
    }

    public SelectorBuilder<T> pointer(String pointer) {
        this.pointer = pointer;
        return this;
    }

    public SelectorBuilder<T> activePointer(String activePointer) {
        this.activePointer = activePointer;
        return this;
    }

    public SelectorBuilder<T> onSelect(Consumer<T> onSelect) {
        this.onSelect = onSelect;
        return this;
    }
}
