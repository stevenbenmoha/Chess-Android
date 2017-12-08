package cs213.chessapp71;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
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
    }
    public void playGame(View view)
    {
        Intent intent = new Intent(this, PlayActivity.class);
        startActivity(intent);
    }
    public void viewPrevGame(View view)
    {
        String path = Environment.getExternalStorageDirectory().toString();
        File f = new File(path);
        File[] files = f.listFiles();
        if(files != null)
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
