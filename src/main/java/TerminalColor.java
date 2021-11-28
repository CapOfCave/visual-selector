public enum TerminalColor {
    BLACK("\u001B[30m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    CYAN("\u001B[36m"),
    WHITE("\u001B[37m");

    private static final String ANSI_RESET = "\u001B[0m";
    private String escapeCode;

    TerminalColor(String escapeCode) {
        this.escapeCode = escapeCode;
    }

    public String apply(String input) {
        return this.escapeCode + input + ANSI_RESET;
    }

    public static String color(TerminalColor color, String input) {
        return color.apply(input);
    }
}
