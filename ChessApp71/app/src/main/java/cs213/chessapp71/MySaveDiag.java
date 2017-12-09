package cs213.chessapp71;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
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
    public ArrayList<String> movesMade = new ArrayList<String>();


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {



        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Title");
        builder.setCancelable(false);

        final EditText input = new EditText(getActivity());

        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        builder.setView(input);

        if(TextUtils.isEmpty(m_Text)) {
            input.setError("Empty name cannot be saved");
        }

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

    public void writeToFile(ArrayList<String> movesMade, String filename) throws IOException {

        File sdcard = Environment.getExternalStorageDirectory();
        Log.i("i", sdcard.getAbsolutePath());

        File dir = new File(sdcard.getAbsolutePath() + "/Games");
        dir.mkdir();

        File file = new File(dir, filename +".txt");
        FileWriter write = new FileWriter(file);

        for (String curMove : movesMade) {


            write.append(curMove);
            write.append("\n");
        }
        write.flush();
        write.close();
    }

    public void populateArray(ArrayList<String> arr) {

        for (String s: arr) {

            movesMade.add(s);

        }


    }






}
