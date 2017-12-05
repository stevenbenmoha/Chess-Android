package cs213.chessapp71;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

}
