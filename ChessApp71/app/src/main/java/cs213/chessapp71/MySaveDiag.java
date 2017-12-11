package cs213.chessapp71;
import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
/**
 * Created by Steven on 12/8/2017.
 */
public class MySaveDiag extends DialogFragment
{
    public String m_Text;
    public ArrayList<String> movesMade = new ArrayList<String>();
    private boolean doSave;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Title");
        builder.setCancelable(false);
        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        builder.setView(input);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                m_Text = input.getText().toString();
                doSave = true;
                if(checkForDuplicates(m_Text))
                {
                    MySaveDiag mySave = new MySaveDiag();
                    mySave.populateArray(movesMade);
                    mySave.show(getFragmentManager(), "Diag");
                    Toast.makeText(getContext(), "Duplicate name, try again", Toast.LENGTH_SHORT).show();
                }
                if(input.getText().toString().trim().length() == 0)
                {
                    MySaveDiag mySave = new MySaveDiag();
                    mySave.populateArray(movesMade);
                    mySave.show(getFragmentManager(), "Diag");
                    Toast.makeText(getContext(), "Can't have empty name, try again", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    try
                    {

                        writeToFile(movesMade, m_Text);
                    }
                    catch(Exception c)
                    {
                    }
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    startActivity(intent);
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
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
    public String getName()
    {
        return m_Text;
    }
    public void writeToFile(ArrayList<String> movesMade, String filename) throws IOException
    {
        File sdcard = Environment.getExternalStorageDirectory();
        Log.i("i", sdcard.getAbsolutePath());
        File dir = new File(sdcard.getAbsolutePath() + "/games");
        dir.mkdir();
        File file = new File(dir, filename + ".txt");
        FileWriter write = new FileWriter(file);
        for(String curMove : movesMade)
        {
            write.append(curMove);
            write.append("\n");
        }
        write.flush();
        write.close();
    }
    public void populateArray(ArrayList<String> arr)
    {
        for(String s : arr)
        {
            movesMade.add(s);
        }
    }
    public boolean checkForDuplicates(String filename)
    {
        File sdcard = Environment.getExternalStorageDirectory();
        Log.i("i", sdcard.getAbsolutePath());
        File dir = new File(sdcard.getAbsolutePath() + "/games");
        File[] files = dir.listFiles();
        filename = filename + ".txt";
        for(int i = 0; i < files.length; i++)
        {
            Log.i("i", files[i].getName());
            if(filename.equals(files[i].getName()))
            {
                return true;
            }
        }
        return false;
    }

}
