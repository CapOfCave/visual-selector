package me.kecker.visualselector;

import me.kecker.visualselector.renderer.Renderer;
import me.kecker.visualselector.renderer.TerminalRenderer;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        TerminalManager consoleInputManager = new TerminalManager();
        System.out.println(consoleInputManager.isTerminalDumb());
//        EnumSet.of(InfoCmp.Capability.key_up, InfoCmp.Capability.key_down, InfoCmp.Capability.key_left, InfoCmp.Capability.key_right)
//                .forEach(c -> consoleInputManager.registerKey(c, () -> System.out.println(c.name())));

        consoleInputManager.registerKey("x", consoleInputManager::stop);

        Renderer renderer = new TerminalRenderer();

        Selector<String> selector = new Selector<>(
                "What color do you like best?",
                new String[]{"blue", "green", "red"},
                ">",
                "*",
                renderer,
                consoleInputManager);

        selector.bindKeys();
        selector.render();
        consoleInputManager.startListening();


    }

}
