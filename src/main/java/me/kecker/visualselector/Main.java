package me.kecker.visualselector;

import me.kecker.visualselector.prompts.Selector;

import java.io.IOException;
import java.util.function.Function;

public class Main {

    public static void main(String[] args) throws IOException {




        Selector<String> selector = new Selector<>(
                "What color do you like best?",
                new String[]{"blue", "green", "red"},
                Function.identity(),
                " ",
                "*"
                );

        Enquirer enquirer = new Enquirer();
        enquirer.doPrompt(selector, option -> System.out.printf("Oh, you chose %s? What a bold choice!%n", option));


    }

}
