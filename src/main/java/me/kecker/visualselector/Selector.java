package me.kecker.visualselector;

import me.kecker.visualselector.renderer.Renderer;
import org.jline.utils.InfoCmp;

public class Selector<T> {
    private final String prompt;
    private final T[] options;
    private final String pointer;
    private final String activePointer;

    private int selected = 0;

    private final Renderer renderer;
    private final TerminalManager consoleInputManager;

    public Selector(String prompt, T[] options, String pointer, String activePointer, Renderer renderer, TerminalManager consoleInputManager) {
        this.prompt = prompt;
        this.options = options;
        this.pointer = pointer;
        this.activePointer = activePointer;
        this.renderer = renderer;
        this.consoleInputManager = consoleInputManager;
    }

    public void bindKeys() {
        consoleInputManager.registerKey(InfoCmp.Capability.key_up, this::up);
        consoleInputManager.registerKey(InfoCmp.Capability.key_down, this::down);
        consoleInputManager.registerKey("\r", this::select);
    }

    private void select() {
        System.out.printf("Oh, you chose %s? What a bold choice!%n", this.options[this.selected]);
        this.consoleInputManager.stop();
    }

    private void down() {
        this.selected = (this.selected + 1) % this.options.length;
        rerender();
    }

    private void up() {
        this.selected = (this.selected + this.options.length - 1) % this.options.length ;
        rerender();
    }

    public void render() {
        this.renderer.renderLine(this.prompt);
        for (int i = 0; i < this.options.length; i++) {
            T option = this.options[i];
            String pointer = this.selected == i ? this.activePointer : this.pointer;
            this.renderer.renderLine(pointer + " " + option);
        }
    }

    public void rerender() {
        this.renderer.clear();
        this.render();
    }
}
