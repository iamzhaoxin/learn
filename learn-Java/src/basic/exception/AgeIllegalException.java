package basic.exception;

public class AgeIllegalException extends RuntimeException{
    public AgeIllegalException(){    }

    public AgeIllegalException(String message) {
        super(message);
    }
}
