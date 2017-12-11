package cs213.chessapp71;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by colinackerley on 12/10/17.
 */
public class selectPromotionPiece extends DialogFragment
{
    public String desiredPiece="original";

    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        final String[] result = new String[1];

        final String[] pieces = {"queen", "bishop", "knight", "rook"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select desired piece");
        builder.setItems(pieces, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int item)
            {
                result[0] = pieces[item];

                desiredPiece = pieces[item];


            }
        });
        AlertDialog selectPiece = builder.create();
        selectPiece.setCanceledOnTouchOutside(false);
        return selectPiece;
    }


    public String getDesiredPiece() {

        return desiredPiece;
    }


}
