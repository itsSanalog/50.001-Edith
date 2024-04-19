package com.example.edith.activities;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

/**
 * This class is a singleton that holds the GoogleSignInAccount instance for the application.
 * It provides a global point of access to the GoogleSignInAccount instance.
 */
public class GoogleAccountHolder {

    // The single instance of this class
    private static GoogleAccountHolder instance;
    // The GoogleSignInAccount instance
    private GoogleSignInAccount account;

    // Private constructor to prevent instantiation
    private GoogleAccountHolder() {}

    /**
     * Returns the single instance of this class, creating it if it doesn't exist.
     * @return the single instance of this class
     */
    public static GoogleAccountHolder getInstance() {
        if (instance == null) {
            instance = new GoogleAccountHolder();
        }
        return instance;
    }

    /**
     * Returns the GoogleSignInAccount instance.
     * @return the GoogleSignInAccount instance
     */
    public GoogleSignInAccount getAccount() {
        return account;
    }

    /**
     * Sets the GoogleSignInAccount instance.
     * @param account the GoogleSignInAccount instance
     */
    public void setAccount(GoogleSignInAccount account) {
        this.account = account;
    }
}