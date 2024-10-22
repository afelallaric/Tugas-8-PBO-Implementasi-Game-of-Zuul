public class CommandWords {
    public static final String GO = "go";
    public static final String QUIT = "quit";
    public static final String HELP = "help";

    private static final String[] validCommands = {GO, QUIT, HELP};

    public boolean isCommand(String commandWord) {
        for (String validCommand : validCommands) {
            if (validCommand.equals(commandWord)) {
                return true;
            }
        }
        return false;
    }

    public void showAll() {
        for (String command : validCommands) {
            System.out.print(command + " ");
        }
        System.out.println();
    }
}
