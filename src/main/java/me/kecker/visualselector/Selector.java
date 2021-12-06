package me.kecker.visualselector;

import me.kecker.visualselector.renderer.Renderer;
import org.jline.utils.InfoCmp;

import java.util.function.Consumer;
import java.util.function.Function;

public class Selector<T> {
    private final String prompt;
    private final T[] options;
    private final Function<T, String> toStringFunction;
    private final String pointer;
    private final String activePointer;
    private final Consumer<T> onSelect;
    private final Renderer renderer;
    private final TerminalManager inputManager;

    private int selected = 0;

    public Selector(
            String prompt,
            T[] options,
            Function<T, String> toStringFunction,
            String pointer,
            String activePointer,
            Consumer<T> onSelect,
            Renderer renderer,
            TerminalManager inputManager) {

        this.prompt = prompt;
        this.options = options;
        this.toStringFunction = toStringFunction;
        this.pointer = pointer;
        this.activePointer = activePointer;
        this.onSelect = onSelect;
        this.renderer = renderer;
        this.inputManager = inputManager;
        this.bindKeys();
    }

    private void bindKeys() {
        inputManager.registerKey(InfoCmp.Capability.key_up, this::up);
        inputManager.registerKey(InfoCmp.Capability.key_down, this::down);
        inputManager.registerKey("\r", this::select);
    }

    public void render() {
        this.renderer.renderLine(this.prompt);
        for (int i = 0; i < this.options.length; i++) {
            T option = this.options[i];
            String pointer = this.selected == i ? this.activePointer : this.pointer;
            this.renderer.renderLine(pointer + " " + this.toStringFunction.apply(option));
        }
    }

    private void select() {
        this.onSelect.accept(this.selectedOption());
        this.inputManager.stop();
    }

    private void down() {
        this.selected = (this.selected + 1) % this.options.length;
        rerender();
    }

    private void up() {
        this.selected = (this.selected + this.options.length - 1) % this.options.length;
        rerender();
    }

    private void rerender() {
        this.renderer.clear();
        this.render();
    }

    private T selectedOption() {
        return this.options[selected];
    }
}
