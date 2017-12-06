package cs213.chessapp71;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
/**
 * Created by Steven on 11/30/2017.
 */
public class PlayActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
    }
    public void resignGame(View view)
    {
        MyDialogFragment myDiag = new MyDialogFragment();
        myDiag.show(getFragmentManager(), "Diag");
        if(myDiag.getResign())
        {
            //Code saying the current player resigned
        }
        else
        {
            //Continue
        }
    }
}
