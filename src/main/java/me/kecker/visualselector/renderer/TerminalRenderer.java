package me.kecker.visualselector.renderer;

public class TerminalRenderer extends AbstractRenderer {

    @Override
    public void renderLine(String content) {
        super.renderLine(content);
        System.out.println(content);
    }

    @Override
    protected void clear(int lines) {
        System.out.printf("\033[%dA", lines); // Move up
        System.out.print("\033[2K"); // Erase renderLine content
    }
}
