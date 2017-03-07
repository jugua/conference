package ua.rd.cm.services.exception;

public class LevelNotFoundException extends ResourceNotFoundException {

    public LevelNotFoundException() {
        super(LEVEL_NOT_FOUND);
    }
}
