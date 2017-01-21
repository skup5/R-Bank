package org.zelenikr.pia.verification;

/**
 * Interface for verification Objects by specific generated code.
 *
 * @author Roman Zelenik
 */
public interface Verifier<T> {

    /**
     * Generates unique code for specific object.
     *
     * @param object the specific T object
     * @return code for the given object
     */
    String generateCode(T object);

    /**
     * Verifies that this T object and code belong together.
     *
     * @param object verified object
     * @param code   verified code
     * @return true only if this code belongs to the T object
     */
    boolean verifyObject(T object, String code);

    /**
     * Deletes inside object-code pair.
     *
     * @param object object to forget
     */
    void forgetObject(T object);
}
