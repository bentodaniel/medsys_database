package gui.java.client;

public class Main {

    /**
     * An utility class should not have public constructors
     */
    private Main() {
    }

    /**
     * A simple interaction with the application services
     * @param args Command line parameters
     */
    public static void main(String[] args) {
        gui.java.presentation.Startup.startGUI();
    }
}
