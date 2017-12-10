package cs213.chessapp71;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;

import java.util.ArrayList;
/**
 * Created by Steven on 12/9/2017.
 */
public class MyDrawDialogFragment extends DialogFragment
{
    public ArrayList<String> movesMade = new ArrayList<String>();
    private boolean doResign;
    private String curColor;
    @Override
    //Creates a confirmation dialog for if the user wants to resign.
    //Based on their selection, a doResign boolean is set to true or false.
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(curColor + " wants to draw, do you accept?");
        builder.setCancelable(false);
        builder.setPositiveButton("Draw", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int whichButton)
            {
                Snackbar mySnackbar = Snackbar.make(getActivity().findViewById(android.R.id.content), "Draw", 3000);
                mySnackbar.show();
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
            }
        });
        AlertDialog confirmDraw = builder.create();
        confirmDraw.setCanceledOnTouchOutside(false);
        return confirmDraw;
    }
    public void populateArray(ArrayList<String> arr)
    {
        for(String s : arr)
        {
            movesMade.add(s);
        }
    }
    public void winner(String winner)
    {
        curColor = winner;
    }
}
