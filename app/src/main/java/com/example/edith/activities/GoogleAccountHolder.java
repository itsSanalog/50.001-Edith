package com.example.edith.activities;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class GoogleAccountHolder {

        private static GoogleAccountHolder instance;
        private GoogleSignInAccount account;

        private GoogleAccountHolder() {}

        public static GoogleAccountHolder getInstance() {
            if (instance == null) {
                instance = new GoogleAccountHolder();
            }
            return instance;
        }

        public GoogleSignInAccount getAccount() {
            return account;
        }

        public void setAccount(GoogleSignInAccount account) {
            this.account = account;
        }
}
