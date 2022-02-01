package com.cavanosa.crudretrofitapp.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.DialogFragment;

import com.cavanosa.crudretrofitapp.interfaces.DeleteInteface;

public class DeleteFragment extends DialogFragment {

    private String message;
    private int id;
    private DeleteInteface deleteInteface;

    public DeleteFragment(String message, int id, DeleteInteface deleteInteface) {
        this.message = message;
        this.id = id;
        this.deleteInteface = deleteInteface;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message + id + "?")
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteInteface.delete(id);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.i("Action: ", "cancel");
                    }
                });
        return builder.create();
    }
}
