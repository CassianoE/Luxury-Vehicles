package app.messages;

public final class ErrorMessages {

    // Construtor privado para evitar instanciação
    private ErrorMessages() {
        throw new AssertionError("Utility class cannot be instantiated");
    }

    public static final String CAR_NOT_FOUND = "Car not found with ID: ";
    public static final String MOTORCYCLE_NOT_FOUND = "Motorcycle not found with ID: ";
    public static final String PLANE_NOT_FOUND = "Plane not found with ID: ";
    public static final String BOAT_NOT_FOUND = "Boat not found with ID: ";
    public static final String OWNER_NOT_FOUND = "Owner not found with ID: ";
}