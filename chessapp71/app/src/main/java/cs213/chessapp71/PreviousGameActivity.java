package cs213.chessapp71;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
        Log.i("I", "Filename is " + filename);
        try
        {
            readFile(filename);
            setContentView(R.layout.activity_prev_game);
        }
        catch(IOException e)
        {
        }
    }
    private String getUserSelection()
    {
        final String[] result = new String[1];
        try
        {
            ArrayList<String> textFiles = getAllTextFiles();
            setContentView(R.layout.layout_game_list);
            setTitle("Choose Saved Game");
            final ListView curView = (ListView) findViewById(R.id.gameList);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, textFiles);
            curView.setAdapter(arrayAdapter);
            curView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                public void onItemClick(AdapterView<?> adapter, View v, int position, long id)
                {
                    String selected = curView.getItemAtPosition(position).toString();
                    result[0] = selected;
                    setContentView(R.layout.activity_prev_game);
                }
            });
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return result[0];
    }
    private ArrayList<String> getAllTextFiles() throws IOException
    {
        ArrayList<String> allTextFiles = new ArrayList<String>();
        String path = Environment.getExternalStorageDirectory().toString() + "/games";
        File f = new File(path);
        File[] files = f.listFiles();
        for(int i = 0; i < files.length; i++)
        {
            File file = files[i];
            String filePath = file.getPath();
            if(filePath.endsWith(".txt"))
                allTextFiles.add(file.getName());
        }
        return allTextFiles;
    }
    private void readFile(String filename) throws IOException
    {
        try
        {
            File sdcard = Environment.getExternalStorageDirectory();
            File dir = new File(sdcard.getAbsolutePath() + "/games");
            Log.i("I", "Filename is" + filename);
            File file = new File(dir, filename);
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