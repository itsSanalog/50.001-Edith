package com.example.edith.fragments;

import android.content.DialogInterface;

/**
 * onDialogCloseListener is an interface that provides a callback method for when a dialog is dismissed.
 * It is used to perform actions when a dialog is closed.
 */
public interface onDialogCloseListener {
    /**
     * This method is called when a dialog is dismissed.
     * @param dialogInterface The dialog that was dismissed.
     */
    void onDialogClose(DialogInterface dialogInterface);
}