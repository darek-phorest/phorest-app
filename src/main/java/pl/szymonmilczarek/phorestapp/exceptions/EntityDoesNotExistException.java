package pl.szymonmilczarek.phorestapp.exceptions;

public class EntityDoesNotExistException extends RuntimeException {

    public EntityDoesNotExistException(String cause) {
        super(String.format("%s does not exist", cause));
    }
}
