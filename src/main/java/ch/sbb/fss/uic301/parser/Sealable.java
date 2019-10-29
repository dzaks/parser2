package ch.sbb.fss.uic301.parser;

/**
 * The instance is immutable after it has been sealed. Calling mutating methods
 * after {@link #seal()} will lead to an {@link IllegalStateException}.
 */
public interface Sealable {

    /**
     * Seals the class. Changing content will lead to an exception afterwards.
     * It is only allowed to call this method once. A second call will lead to
     * an {@link IllegalStateException}.
     */
    public void seal();

    /**
     * Determines if the instance is sealed.
     * 
     * @return <code>true</code> if the instance has been sealed.
     */
    public boolean isSealed();

}
