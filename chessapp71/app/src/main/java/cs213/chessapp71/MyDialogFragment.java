package cs213.chessapp71;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * Created by colinackerley on 12/5/17.
 */
public class MyDialogFragment extends DialogFragment
{
    private boolean doResign;
    public ArrayList<String> movesMade = new ArrayList<String>();
    @Override
    //Creates a confirmation dialog for if the user wants to resign.
    //Based on their selection, a doResign boolean is set to true or false.
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Confirm Resign");
        builder.setCancelable(false);
        builder.setPositiveButton("Resign", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int whichButton)
            {
                doResign = true;

                MySaveDialogFragment mySave = new MySaveDialogFragment();
                mySave.populateArray(movesMade);
                mySave.show(getFragmentManager(), "Diag");

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int whichButton)
            {
                doResign = false;
            }
        });
        AlertDialog confirmResign = builder.create();
        confirmResign.setCanceledOnTouchOutside(false);

        return confirmResign;
    }
    public boolean getResign()
    {
        return doResign;
    }

    public void populateArray(ArrayList<String> arr) {

        for (String s : arr) {

            movesMade.add(s);

        }
    }
}
