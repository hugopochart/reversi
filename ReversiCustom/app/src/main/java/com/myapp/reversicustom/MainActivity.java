package com.myapp.reversicustom;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    public  PixelGridView gridView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);

        //Scoredisplay scoredisplay = new Scoredisplay(this);
        PixelGridView pixelGrid = new PixelGridView(this);
        pixelGrid.setNumColumns(8);
        pixelGrid.setNumRows(8);
        pixelGrid.forceLayout();




        ll.addView(pixelGrid);

        String player0res = Integer.toString(pixelGrid.countValue(0));
        String player1res = Integer.toString(pixelGrid.countValue(1));
        String player0resultat = "score player 0: " + player0res;
        String player1resultat = "score player 1: " + player1res;

        //Button button1 = new Button(this);
        TextView textView1 = new TextView(this);
        textView1.setId(R.id.edit);
        TextView textView2 = new TextView(this);
        //button1.setId(R.id.button);
        //ll.addView(textView1);
        //ll.addView(textView2);
        //ll.addView(button1);
        setContentView(R.layout.activity_main);
        setContentView(ll);
        textView1.setText(player0resultat);
        textView2.setText(player1resultat);

    }


    public void Onclicker (View view)
    {
        Intent intent = new Intent(MainActivity.this,MainActivity.class);
        startActivity(intent);
    }

}
