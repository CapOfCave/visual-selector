package me.kecker.visualselector;

import me.kecker.visualselector.prompts.Selector;

import java.util.function.Function;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        Selector<String> selector = new Selector<>(
                "What color do you like best?",
                new String[]{"blue", "green", "red"},
                Function.identity(),
                " ",
                "*"
        );

        String option = Enquirer.prompt(selector);
        System.out.printf("Oh, you chose %s? I don't like that choice! Please choose another one.%n", option);
        String newOption = Enquirer.prompt( new Selector<>(
                "What color do you like best?",
                new String[]{"blue", "green", "red"},
                Function.identity(),
                " ",
                "*"
        ));
        while (newOption.equals(option)) {
            System.out.printf("I told you not to choose %s! Try again, please.", option);
            newOption = Enquirer.prompt( new Selector<>(
                    "What color do you like best?",
                    new String[]{"blue", "green", "red"},
                    Function.identity(),
                    " ",
                    "*"
            ));
        }
        System.out.printf("%s is a much nicer color, glad you reconsidered your choice", newOption);




    }

}
