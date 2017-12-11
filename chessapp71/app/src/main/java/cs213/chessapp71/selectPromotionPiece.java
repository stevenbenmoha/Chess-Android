package cs213.chessapp71;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
/**
 * Created by colinackerley on 12/10/17.
 */
public class selectPromotionPiece extends DialogFragment
{
    public String desiredPiece="";

    public Dialog makeDialog()
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

                desiredPiece = result[0];

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
