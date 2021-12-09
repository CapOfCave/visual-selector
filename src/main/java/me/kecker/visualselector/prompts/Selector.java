package me.kecker.visualselector.prompts;

import me.kecker.visualselector.Prompt;
import me.kecker.visualselector.TerminalManager;
import me.kecker.visualselector.renderer.Renderer;
import org.jline.utils.InfoCmp;

import java.util.function.Function;

public class Selector<T> extends Prompt<T> {
    private final String prompt;
    private final T[] options;
    private final Function<T, String> toStringFunction;
    private final String pointer;
    private final String activePointer;

    private int selected = 0;

    public Selector(
            String prompt,
            T[] options,
            Function<T, String> toStringFunction,
            String pointer,
            String activePointer
    ) {
        super();
        this.prompt = prompt;
        this.options = options;
        this.toStringFunction = toStringFunction;
        this.pointer = pointer;
        this.activePointer = activePointer;
    }

    @Override
    public void bindKeys(TerminalManager inputManager) {
        inputManager.registerKey(InfoCmp.Capability.key_up, this::up);
        inputManager.registerKey(InfoCmp.Capability.key_down, this::down);
        inputManager.registerKey("\r", this::select);
    }

    @Override
    public void render(Renderer renderer) {
        renderer.renderLine(this.prompt);
        for (int i = 0; i < this.options.length; i++) {
            T option = this.options[i];
            String pointer = this.selected == i ? this.activePointer : this.pointer;
            renderer.renderLine(pointer + " " + this.toStringFunction.apply(option));
        }
    }

    private void select() {
        this.resultObtained(this.selectedOption());
    }

    private void down() {
        this.selected = (this.selected + 1) % this.options.length;
        this.fireChanged();
    }

    private void up() {
        this.selected = (this.selected + this.options.length - 1) % this.options.length;
        this.fireChanged();
    }

    private T selectedOption() {
        return this.options[selected];
    }

}
