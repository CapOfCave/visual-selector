package me.kecker.visualselector;

import org.jline.keymap.BindingReader;
import org.jline.keymap.KeyMap;
import org.jline.terminal.Attributes;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp;

import java.io.IOException;

public class TerminalManager {

    private final Terminal terminal;
    private final BindingReader reader;

    private final KeyMap<Runnable> registeredKeys = new KeyMap<>();
    private Thread listeningThread; // TODO perf ops

    private volatile boolean running;
    private volatile boolean shouldStop;

    /** Terminal's Attribute state to use on reset */
    private Attributes defaultTerminalAttributes;

    public TerminalManager() throws IOException {
        this.terminal = TerminalBuilder.terminal();
        this.reader = new BindingReader(this.terminal.reader());
        this.registerDefaultKeys();
    }

    public void startListening() {
        if (this.running) {
            throw new IllegalStateException("Already running!");
        }
        this.running = true;
        this.shouldStop = false;
        this.defaultTerminalAttributes = this.terminal.enterRawMode();

        this.listeningThread = new Thread(this::listenForInput);
        listeningThread.start();
    }

    void registerDefaultKeys() {
        // TODO
        this.registerKey("x", this::stopListening);
    }

    public void registerKey(String key, Runnable onKeyPress) {
        if (this.running) {
            throw new IllegalStateException("Can't register keys when the consoleInputManager is already started");
        }
        this.registeredKeys.bind(onKeyPress, key);
    }

    public void registerKey(InfoCmp.Capability capability, Runnable onKeyPress) {
        this.registerKey(KeyMap.key(this.terminal, capability), onKeyPress);
    }

    public void stopListening() {
        this.shouldStop = true;
    }

    public boolean isTerminalDumb() {
        return "dumb".equals(terminal.getType()) || "dumb-color".equals(terminal.getType());
    }

    public void cleanUp() {
        this.terminal.setAttributes(this.defaultTerminalAttributes);
    }

    private void listenForInput() {
        while (!this.shouldStop) {
            Runnable runnable = this.reader.readBinding(this.registeredKeys, null, true);
            // check if this.shouldStop has changed while waiting for input
            if (runnable != null && !this.shouldStop) {
                runnable.run();
            }

            Thread.onSpinWait();
        }
        this.running = false;
    }
}
