package com.example.android.quidditch;

import android.content.res.Configuration;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.facebook.shimmer.ShimmerFrameLayout;
import java.text.DecimalFormat;
import java.util.Random;
import static com.example.android.quidditch.R.layout.winner;


public class MainActivity extends AppCompatActivity {


    String winner1 = "Gryffindor won !";
    String winner2 = "Slytherin won !";
    String reset = "000";
    final DecimalFormat decimalFormat = new DecimalFormat("000");
    ShimmerFrameLayout shimer;
    TextView scoreView1;
    TextView scoreView2;
    String value_one;
    String value_two;
    TextView win;
    Random randomGenerator = new Random();
    int teamG = 0;
    int teamS = 0;
    boolean test = true;
    private Button rule;
    private Button button3;
    private PopupWindow popupWindow;
    private LayoutInflater layoutInflater;
    private String score1 = "G_score";
    private String score2 = "S_score";

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(score1, teamG);
        outState.putInt(score2, teamS);


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        teamG = (savedInstanceState.getInt(score1));
        teamS = (savedInstanceState.getInt(score2));
        value_one = decimalFormat.format(teamG);
        value_two = decimalFormat.format(teamS);
        scoreView1.setText(value_one);
        scoreView2.setText(value_two);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        setContentView(R.layout.activity_main);

        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE || newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.rule);
            setContentView(R.layout.winner);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        shimer = (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container);
        shimer.setDuration(1000);
        shimer.setAutoStart(true);
        rule = (Button) findViewById(R.id.game_rules);
        button3 = (Button) findViewById(R.id.button3);
        scoreView1 = (TextView) findViewById(R.id.team_g_score);
        scoreView2 = (TextView) findViewById(R.id.team_s_score);
        value_one = decimalFormat.format(teamG);
        value_two = decimalFormat.format(teamS);






        rule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.rule, null);
                popupWindow = new PopupWindow(container, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, true);
                popupWindow.showAtLocation(rule, Gravity.CENTER, 0, 0);
                container.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent event) {
                        popupWindow.dismiss();
                        return true;
                    }
                });
            }
        });


      button3.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
               if (button3()) {
                    layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    ViewGroup container = (ViewGroup) layoutInflater.inflate(winner, null);
                    popupWindow = new PopupWindow(container, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, true);
                    popupWindow.showAtLocation(button3, Gravity.CENTER, 0, 0);
                    win = (TextView) popupWindow.getContentView().findViewById(R.id.grifwon);
                    if (test)
                           win.setText(winner2);
                      else
                           win.setText(winner1);
                    popupWindow.setTouchable(true);
                    container.setOnTouchListener(new View.OnTouchListener() {

                        @Override
                        public boolean onTouch(View view, MotionEvent event) {
                            popupWindow.dismiss();
                            return true;

                        }
                    });
                }
            }
        });

    }




    public boolean button3() {


        test = randomGenerator.nextBoolean();
        if (test) {
            teamS += 150;
            displayForSlytherin(teamS);


        } else {
            teamG += 150;
            displayForGryffindor(teamG);

        }
        return true;
    }


    public void button1(View view) {
        teamG += 10;
        displayForGryffindor(teamG);
}



    public void button2(View view) {
        teamS += 10;
        displayForSlytherin(teamS);
    }

    public void resetScore(View view) {
        teamG = 0;
        teamS = 0;
        displayForGryffindor(teamG);
        scoreView1.setText(reset);
        displayForSlytherin(teamS);
        scoreView2.setText(reset);
    }



    /**
     * Displays the score for Gryffindor
     */
    public void displayForGryffindor(int score) {
        value_one = decimalFormat.format(teamG);
        scoreView1.setText(String.valueOf(score));
        scoreView1.setText(value_one);
    }

    /**
     * Displays the score for Slytherin
     */

    public void displayForSlytherin(int score) {
        value_two = decimalFormat.format(teamS);
        scoreView2.setText(String.valueOf(score));
        scoreView2.setText(value_two);
    }

    public void game_rules(View view) {
        rule.callOnClick();
    }

    public void button3(View view) {
    }
}