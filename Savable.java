/***
 * An interface for classes that can be saved and loaded from disk and have a unique identifier.
 */
public interface Savable {

    /***
     * Saves an instance of the class to disk.
     */
    void Save();

    /***
     * Loads based on the unique id.
     */
    void Load(String unique_id);

    /***
     * Gets the unique identifier for the current instance.
     * @return the uid.
     */
    String getUniqueIdentifier();
}