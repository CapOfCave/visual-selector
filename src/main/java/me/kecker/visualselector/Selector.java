package me.kecker.visualselector;

import org.jline.utils.InfoCmp;

public class Selector {
    private final String prompt;
    private final String[] options;
    private final String pointer;
    private final String activePointer;


    private int selected = 0;

    public Selector(String prompt, String[] options, String pointer, String activePointer) {
        this.prompt = prompt;
        this.options = options;
        this.pointer = pointer;
        this.activePointer = activePointer;
    }

    public void start() {

    }

    public void bindKeys(ConsoleInputManager consoleInputManager) {
        consoleInputManager.registerKey(InfoCmp.Capability.key_up, this::up);
        consoleInputManager.registerKey(InfoCmp.Capability.key_down, this::down);

    }

    private void down() {
        this.selected = (this.selected + 1) % this.options.length;
        render();
    }

    private void up() {
        this.selected = (this.selected + this.options.length - 1) % this.options.length ;
        render();
    }

    public void render() {
        System.out.println(this.prompt);
        for (int i = 0; i < this.options.length; i++) {
            String option = this.options[i];
            String pointer = this.selected == i ? this.activePointer : this.pointer;
            System.out.println(pointer + " " + option);
        }
    }
}
