package edubentleyd.gvsu.cis.surroundgameandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Spinner;


/**
 * Created by DerrickBentley on 8/17/2015.
 */
public class SettingsDialogFragment extends DialogFragment {


    Spinner totalPlayers;
    Spinner boardSize;
    Spinner startingPlayer;
    int here,we,go;

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog, int arg0, int arg1, int arg2);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    NoticeDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View tempView = inflater.inflate(R.layout.settinglayout, null);
        totalPlayers = (Spinner) tempView.findViewById(R.id.spnrPlayers);
        boardSize = (Spinner) tempView.findViewById(R.id.spnrBdSize);
        startingPlayer = (Spinner) tempView.findViewById(R.id.spnrStartPlayer);
                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                builder.setView(tempView)
                        .setPositiveButton("Set", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                here = Integer.parseInt(totalPlayers.getSelectedItem().toString());
                                we = Integer.parseInt(boardSize.getSelectedItem().toString());
                                go = Integer.parseInt(startingPlayer.getSelectedItem().toString());
                                mListener.onDialogPositiveClick(SettingsDialogFragment.this, here, we, go);
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mListener.onDialogNegativeClick(SettingsDialogFragment.this);
                            }
                        });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
