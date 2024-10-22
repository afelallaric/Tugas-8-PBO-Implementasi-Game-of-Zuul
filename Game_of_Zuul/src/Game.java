public class Game {
    private Parser parser;
    private boolean finished;
    private Room currentRoom;
    private Room exitRoom;

    public Game() {
        createRooms();
        parser = new Parser();
        finished = false;
    }

    private void createRooms() {
        Room outside = new Room("outside the main entrance of the university");
        Room mainHall = new Room("in the main hall");
        Room hallway = new Room("in the hallway");
        Room cafeteria = new Room("in the cafeteria");
        Room library = new Room("in the library");
        exitRoom = new Room("at the university's exit");

        outside.setExit("east", mainHall);

        mainHall.setExit("west", outside);
        mainHall.setExit("north", hallway);
        mainHall.setExit("east", cafeteria);
        mainHall.setExit("south", exitRoom);

        hallway.setExit("south", mainHall);
        hallway.setExit("east", cafeteria);
        hallway.setExit("west", library);

        cafeteria.setExit("west", hallway);
        cafeteria.setExit("south", exitRoom);
        cafeteria.setExit("north", mainHall);

        library.setExit("east", hallway);

        exitRoom.setExit("north", mainHall);

        // Starting point: outside
        currentRoom = outside;
    }

    public void play() {
        printWelcome();

        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing. Goodbye!");
    }

    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getDescription());
        printCurrentExits();
    }

    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        if (command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals(CommandWords.HELP)) {
            printHelp();
        } else if (commandWord.equals(CommandWords.GO)) {
            goRoom(command);
        } else if (commandWord.equals(CommandWords.QUIT)) {
            wantToQuit = true;
        }

        return wantToQuit;
    }

    private void printHelp() {
        System.out.println("You are " + currentRoom.getDescription());
        System.out.println("To find the exit, try using directions like 'go east', 'go west', 'go north', or 'go south'.");
        printCurrentExits();
    }

    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no exit in that direction!");
        } else {
            currentRoom = nextRoom;
            System.out.println("You are now " + currentRoom.getDescription());
            printCurrentExits();

            if (currentRoom == exitRoom) {
                System.out.println("Congrats, you win the game!");
                finished = true;
            }
        }
    }

    private void printCurrentExits() {
        System.out.println("Exits: " + currentRoom.getExits());
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
}
