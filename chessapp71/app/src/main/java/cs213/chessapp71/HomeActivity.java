package cs213.chessapp71;
import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.File;
public class HomeActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);
    }
    public void playGame(View view)
    {
        Intent intent = new Intent(this, PlayActivity.class);
        startActivity(intent);
    }
    public void viewPrevGame(View view)
    {

        File dir = new File(Environment.getExternalStorageDirectory().toString() + "/games");
        if(!dir.exists())
        {
            dir.mkdirs();
        }
        File[] files = dir.listFiles();
        if(files.length != 0)
        {
            Intent intent = new Intent(this, PreviousGameActivity.class);
            startActivity(intent);
        }
        else
        {
            Snackbar error = Snackbar.make(findViewById(android.R.id.content), "No Saved Games", 3000);
            error.show();
        }
    }
}