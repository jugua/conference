package service.businesslogic.exception;

public class LevelNotFoundException extends ResourceNotFoundException {

    public LevelNotFoundException() {
        super(LEVEL_NOT_FOUND);
    }
}
