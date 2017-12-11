package cs213.chessapp71;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.widget.Toast;

import java.util.ArrayList;
/**
 * Created by colinackerley on 12/5/17.
 */
public class MyDialogFragment extends DialogFragment
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
        builder.setTitle("Confirm Resign");
        builder.setCancelable(false);


        builder.setPositiveButton("Resign", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int whichButton)
            {
                doResign = true;


                Toast toast=Toast.makeText(getActivity(),(curColor) + " wins!",Toast.LENGTH_SHORT);
                toast.show();
                MySaveDialogFragment mySave = new MySaveDialogFragment();
                movesMade.add("Draw"+"-"+(curColor));
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

    public void saveFinishedGame()
    {
        MySaveDialogFragment mySave = new MySaveDialogFragment();
        mySave.populateArray(movesMade);
        mySave.show(getFragmentManager(), "Diag");
        if(mySave.getResign())
        {
        }
        else
        {
        }
    }

    private static String flipColor(String s)
    {
        if(s.equals("White"))
            return "Black";
        return "White";
    }



}
