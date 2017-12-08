package cs213.chessapp71;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Steven on 12/8/2017.
 */

public class MySaveDiag extends DialogFragment {

    public String m_Text;
    private boolean doSave;
    public ArrayList<String> movesMade = new ArrayList<>();


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Title");
        builder.setCancelable(false);

        final EditText input = new EditText(getActivity());

        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        builder.setView(input);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();
                doSave = true;


                try {
                    writeToFile(movesMade,m_Text);
                } catch (Exception c) {
                }

                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                doSave = false;
                dialog.cancel();

                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);


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


    public String getName() {

        return m_Text;
    }

    public void writeToFile(ArrayList<String> moves, String filename) throws IOException {
        File moveFile = new File(Environment.getExternalStorageDirectory(), filename + ".txt");
        FileWriter write = new FileWriter(moveFile);
        write.flush();
        write.close();
        for (String curMove : moves) {
            write.append(curMove);
            write.append("\n");
        }
    }

    public void setArray(ArrayList arr) {

        movesMade = arr;

    }






}
