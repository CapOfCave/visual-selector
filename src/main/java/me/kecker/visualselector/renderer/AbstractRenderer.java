package me.kecker.visualselector.renderer;

public abstract class AbstractRenderer implements Renderer {

    private int lineCount = 0;

    @Override
    public void renderLine(String content) {
        this.lineCount++;
    }

    @Override
    public void clear() {
        this.clear(lineCount);
        this.lineCount = 0;
    }

    @Override
    public void commit() {
        this.lineCount = 0;
    }

    protected abstract void clear(int lines);
}
