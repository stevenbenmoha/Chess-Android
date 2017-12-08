package cs213.chessapp71;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
/**
 * Created by colinackerley on 12/7/17.
 */
public class PreviousGameActivity extends AppCompatActivity
{
    ArrayList<String> moves = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        String filename = getUserSelection();
        if(filename != null)
        {
            try
            {
                readFile(filename);
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            Intent intent = new Intent(this, PreviousGameActivity.class);
            startActivity(intent);
            Snackbar error = Snackbar.make(findViewById(android.R.id.content), "No Saved Games", 3000);
            error.show();
        }
    }
    private String getUserSelection()
    {
        try
        {
            ArrayList<String> textFiles = getAllTextFiles();
            textFiles.add("Hello");
            textFiles.add("Goodbye");
            setContentView(R.layout.layout_game_list);
            setTitle("Choose Saved Game");
            ListView curView = (ListView) findViewById(R.id.gameList);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, textFiles);
            curView.setAdapter(arrayAdapter);
            return "";
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }
    private ArrayList<String> getAllTextFiles() throws IOException
    {
        ArrayList<String> allTextFiles = new ArrayList<String>();
        String path = Environment.getExternalStorageDirectory().toString();
        File f = new File(path);
        File[] files = f.listFiles();
        if(files == null)
        {
            throw new IOException();
        }
        else
        {
            for(int i = 0; i < files.length; i++)
            {
                File file = files[i];
                String filePath = file.getPath();
                if(filePath.endsWith(".txt"))
                    allTextFiles.add(filePath);
            }
        }
        return allTextFiles;
    }
    private void readFile(String filename) throws IOException
    {
        try
        {
            File sdcard = Environment.getExternalStorageDirectory();
            File file = new File(sdcard, "filename");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String curMove;
            while((curMove = reader.readLine()) != null)
            {
                moves.add(curMove);
            }
            reader.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}