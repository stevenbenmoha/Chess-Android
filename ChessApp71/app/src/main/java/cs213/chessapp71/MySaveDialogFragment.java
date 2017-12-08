package cs213.chessapp71;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Steven on 12/8/2017.
 */

public class MySaveDialogFragment extends DialogFragment{

    private boolean doSave;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Would you like to save your game?");
        builder.setCancelable(false);
        builder.setPositiveButton("No", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int whichButton)
            {
                doSave = false;

                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);

            }
        });
        builder.setNegativeButton("Save", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int whichButton)
            {
                doSave = true;
                MySaveDiag mySave = new MySaveDiag();
                mySave.show(getFragmentManager(), "Diag");
            }
        });
        AlertDialog confirmSave = builder.create();
        confirmSave.setCanceledOnTouchOutside(false);

        return confirmSave;
    }
    public boolean getResign()
    {
        return doSave;
    }






}

