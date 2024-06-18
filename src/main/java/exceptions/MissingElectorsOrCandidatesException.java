package exceptions;

public class MissingElectorsOrCandidatesException extends Exception {
    public MissingElectorsOrCandidatesException(String message) {
        super(message);
    }
}