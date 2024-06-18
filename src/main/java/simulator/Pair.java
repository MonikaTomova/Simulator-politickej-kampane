package simulator;

import java.util.Objects;

public class Pair<Type1, Type2> {
    private Type1 object1;
    private Type2 object2;

    /**
     * Constructs a Pair object with the specified objects
     * @param object1 the first object of the pair
     * @param object2 the second object of the pair
     */
    public Pair(Type1 object1, Type2 object2) {
        this.object1 = object1;
        this.object2 = object2;
    }

    /**
     * Checks if this Pair object is equal to another object
     * @param object the object to compare with
     * @return true if the objects are equal, otherwise false
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Pair<?, ?> pair = (Pair<?, ?>) object;
        return Objects.equals(object1, pair.object1) && Objects.equals(object2, pair.object2);
    }

    /**
     * Generates a hash code for this Pair object
     * @return the hash code value for this Pair object
     */
    @Override
    public int hashCode() {
        return Objects.hash(object1, object2);
    }
}
