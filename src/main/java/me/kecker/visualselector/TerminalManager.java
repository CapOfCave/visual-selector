package me.kecker.visualselector;

import org.jline.keymap.BindingReader;
import org.jline.keymap.KeyMap;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp;

import java.io.IOException;

public class TerminalManager {

    private final Terminal terminal;
    private final BindingReader reader;

    private final KeyMap<Runnable> registeredKeys = new KeyMap<>();
    private final Thread listeningThread;

    private volatile boolean running;
    private volatile boolean shouldStop;

    public TerminalManager() throws IOException {
        this.terminal = TerminalBuilder.terminal();
        this.reader = new BindingReader(this.terminal.reader());
        this.listeningThread = new Thread(this::listenForInput);
        this.registerDefaultKeys();
    }

    public void startListening() {
        if (this.running) {
            throw new IllegalStateException("Already running!");
        }
        this.running = true;
        this.shouldStop = false;
        this.terminal.enterRawMode();

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
