package cs213.chessapp71;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.graphics.drawable.Drawable;


/**
 * Created by Steven on 11/30/2017.
 */

public class PlayActivity extends AppCompatActivity implements OnClickListener
{

    protected TableLayout board;
    public String tag1 = null;
    public String tag2 = null;
    public String temp = null;
    int itemsSelected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        board = (TableLayout) findViewById(R.id.chessBoardLayout);

        board.setOnClickListener(PlayActivity.this);

    }


    public void resignGame(View view) {
        MyDialogFragment myDiag = new MyDialogFragment();
        myDiag.show(getFragmentManager(), "Diag");
        if (myDiag.getResign()) {
            //Code saying the current player resigned
        } else {
            //Continue
        }
    }

    public void highlight(View v) {

        temp = tag1;
        tag1 = v.getTag().toString();

        if (tag1.equals(tag2)) {   // Double-clicking same piece cancels out selection
            v.setBackground(null);
            tag1 = "";
            tag2 = "";
            temp = "";
            itemsSelected = 0;
        } else {
            Drawable highlight = getResources().getDrawable(R.drawable.highlight);
            v.setBackground(highlight);


            if (itemsSelected == 1) {


                ImageView imageView = (ImageView) board.findViewWithTag(tag2);
                ImageView imageView2 = (ImageView) board.findViewWithTag(tag1);
                Drawable i = imageView.getDrawable();
                Drawable j = imageView2.getDrawable();
                imageView.setImageDrawable(j);
                imageView2.setImageDrawable(i);
                imageView.setBackground(null);
                imageView2.setBackground(null);
                itemsSelected = 0;
                tag1 = "";
                tag2 = "";
                temp = "";
            } else {
                tag2 = v.getTag().toString();
                itemsSelected++;
            }

        }
    }

    @Override
    public void onClick(View v) {

       highlight(v);

         }

    }