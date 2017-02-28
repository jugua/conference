package ua.rd.cm.services.exception;

public class LevelNotFoundException extends ResourceNotFoundException {

    public LevelNotFoundException() {
        super("level_not_found");
    }
}
