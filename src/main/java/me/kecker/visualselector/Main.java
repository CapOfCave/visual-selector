package me.kecker.visualselector;

import org.jline.terminal.Terminal;
import org.jline.utils.InfoCmp;

import java.io.IOException;
import java.util.EnumSet;

public class Main {

    public static void main(String[] args) throws IOException {

        ConsoleInputManager consoleInputManager = new ConsoleInputManager();
        System.out.println(consoleInputManager.isTerminalDumb());
//        EnumSet.of(InfoCmp.Capability.key_up, InfoCmp.Capability.key_down, InfoCmp.Capability.key_left, InfoCmp.Capability.key_right)
//                .forEach(c -> consoleInputManager.registerKey(c, () -> System.out.println(c.name())));

        consoleInputManager.registerKey("x", consoleInputManager::stop);

        Selector selector = new Selector("What color do you like best?", new String[]{"blue", "green", "red"}, ">", "*");

        selector.bindKeys(consoleInputManager);
        selector.render();
        consoleInputManager.startListening();


    }

}
