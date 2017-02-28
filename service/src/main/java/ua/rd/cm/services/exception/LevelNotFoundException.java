package ua.rd.cm.services.exception;

public class LevelNotFoundException extends EntityNotFoundException {

    public LevelNotFoundException() {
        super("level_not_found");
    }
}
