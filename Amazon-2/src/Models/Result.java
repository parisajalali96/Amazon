package Models;

public record Result(boolean success, String message) {
    public Result(boolean success, String message) {
        this.success = success;
        this.message = message;
        printMessage(message);
    }
    public static void printMessage(String message) {
        if(!message.isEmpty()) {
            System.out.println(message);
        }
    }
}
