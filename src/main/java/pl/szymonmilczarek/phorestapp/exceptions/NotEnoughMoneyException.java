package pl.szymonmilczarek.phorestapp.exceptions;

public class NotEnoughMoneyException extends RuntimeException {

    public NotEnoughMoneyException(String id) {
        super(String.format("Player %s has not enough money to play.", id));
    }
}
