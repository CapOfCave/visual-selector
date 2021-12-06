package me.kecker.visualselector;

import org.jline.utils.InfoCmp;

public class Selector {
    private final String prompt;
    private final String[] options;
    private final String pointer;
    private final String activePointer;


    private int selected = 0;

    private final TerminalManager consoleInputManager;

    public Selector(String prompt, String[] options, String pointer, String activePointer, TerminalManager consoleInputManager) {
        this.prompt = prompt;
        this.options = options;
        this.pointer = pointer;
        this.activePointer = activePointer;
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
        System.out.println(this.prompt);
        for (int i = 0; i < this.options.length; i++) {
            String option = this.options[i];
            String pointer = this.selected == i ? this.activePointer : this.pointer;
            System.out.println(pointer + " " + option);
        }
    }

    public void rerender() {
        this.consoleInputManager.clearScreen();
        this.render();
    }
}
