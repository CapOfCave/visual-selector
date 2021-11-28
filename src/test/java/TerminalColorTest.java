import org.junit.jupiter.api.Test;

class TerminalColorTest {

    @Test
    void test() {
        // TODO automatize?
        System.out.println(TerminalColor.GREEN.apply("this is some text") + " with some " + TerminalColor.RED.apply("RED") + " text afterwards");
        System.out.println(TerminalColor.GREEN.apply("this is some text"));
    }
}