package com.example.android.quidditch;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.text.DecimalFormat;
import java.util.Random;

import static com.example.android.quidditch.R.layout.winner;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_IMAGE_ONE = "bitmap1";
    public static final String KEY_IMAGE_TWO = "bitmap2";
    final DecimalFormat decimalFormat = new DecimalFormat("000");
    int teamG = 0, teamS = 0;
    int ImageId;
    ShimmerFrameLayout shimmerFrameLayout;
    Random randomGenerator = new Random();
    TextView scoreView1, scoreView2;
    TextView textMessage;
    TextView win;
    String value_one, value_two;
    Button first, second;
    Button rule;
    Button button3;
    RelativeLayout above, below;
    LayoutInflater layoutInflater, inflater;
    ImageView pictureOne, pictureTwo;
    ImageView chosenImage;
    ImageView findIt;
    ImageView pic, pic2, pic3, pic4;
    Drawable bird, snake, badger, lion;
    Drawable drawable;
    boolean test = true;
    Toast toast;
    View view;
    Bitmap mBitmap;
    private String score1 = String.valueOf((R.string.score1));
    private String score2 = String.valueOf((R.string.score2));
    private PopupWindow popupWindow;

    // Save score and  choosen team
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mBitmap = ((BitmapDrawable) pictureOne.getDrawable()).getBitmap();
        outState.putParcelable(KEY_IMAGE_ONE, mBitmap);
        mBitmap = ((BitmapDrawable) pictureTwo.getDrawable()).getBitmap();
        outState.putParcelable(KEY_IMAGE_TWO, mBitmap);
        outState.putInt(score1, teamG);
        outState.putInt(score2, teamS);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mBitmap = (savedInstanceState.getParcelable(KEY_IMAGE_ONE));
        mBitmap = (savedInstanceState.getParcelable(KEY_IMAGE_TWO));
        pictureOne.setImageBitmap(mBitmap);
        pictureTwo.setImageBitmap(mBitmap);
        teamG = (savedInstanceState.getInt(score1));
        teamS = (savedInstanceState.getInt(score2));
        value_one = decimalFormat.format(teamG);
        value_two = decimalFormat.format(teamS);
        scoreView1.setText(value_one);
        scoreView2.setText(value_two);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shimmerFrameLayout = (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container);
        shimmerFrameLayout.setDuration(1000);
        shimmerFrameLayout.setAutoStart(true);

        rule = (Button) findViewById(R.id.game_rules);
        button3 = (Button) findViewById(R.id.button3);

        scoreView1 = (TextView) findViewById(R.id.team_g_score);
        scoreView2 = (TextView) findViewById(R.id.team_s_score);

        value_one = decimalFormat.format(teamG);
        value_two = decimalFormat.format(teamS);

        above = (RelativeLayout) findViewById(R.id.layoutLayout);
        below = (RelativeLayout) findViewById(R.id.choiceplayer);

        pictureOne = (ImageView) findViewById(R.id.icon_1);
        pictureTwo = (ImageView) findViewById(R.id.icon_2);

        first = (Button) findViewById(R.id.button1);
        second = (Button) findViewById(R.id.button2);

        pic = (ImageView) findViewById(R.id.pic_1);
        pic2 = (ImageView) findViewById(R.id.pic_2);
        pic3 = (ImageView) findViewById(R.id.pic_3);
        pic4 = (ImageView) findViewById(R.id.pic_4);

        bird = getDrawable(R.drawable.ravenclaw);
        snake = getDrawable(R.drawable.slyth);
        badger = getDrawable(R.drawable.hufflepuff);
        lion = getDrawable(R.drawable.gryf);

        pic.setImageDrawable(bird);
        pic2.setImageDrawable(snake);
        pic3.setImageDrawable(badger);
        pic4.setImageDrawable(lion);

        scoreView1.onSaveInstanceState();
        scoreView2.onSaveInstanceState();

        // PopupWindow for game rules
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

        // PopupWindow to show the winner
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
                        win.setText(R.string.winner_B);
                    else
                        win.setText(R.string.winner_A);
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

    public void resetScore() {
        teamG = 0;
        teamS = 0;
        displayForGryffindor(teamG);
        scoreView1.setText(R.string.reset);
        displayForSlytherin(teamS);
        scoreView2.setText(R.string.reset);
    }

    public void resetScore(View view) {
        resetScore();
    }

    // Displays the score for Gryffindor
    public void displayForGryffindor(int score) {
        value_one = decimalFormat.format(teamG);
        scoreView1.setText(String.valueOf(score));
        scoreView1.setText(value_one);
    }

    // Displays the score for Slytherin
    public void displayForSlytherin(int score) {
        value_two = decimalFormat.format(teamS);
        scoreView2.setText(String.valueOf(score));
        scoreView2.setText(value_two);
    }

    public void game_rules(View view) {
        rule.callOnClick();
    }

    public void button3(View view) {
        button3();
    }

    public void click(View view) {
        ImageId = view.getId();
        above.setVisibility(View.GONE);
        below.setVisibility(View.VISIBLE);
    }

    // Team selection
    public void selectTeam(View picture) {
        chosenImage = (ImageView) picture;
        findIt = (ImageView) findViewById(ImageId);
        drawable = chosenImage.getDrawable();
        findIt.setImageDrawable(drawable);

        // Reset score if the user changes the team
        resetScore();

        if (pictureOne.getDrawable() == drawable || pictureTwo.getDrawable() == drawable) {
            above.setVisibility(View.VISIBLE);
            below.setVisibility(View.GONE);

            // Check whether two choosen teams are the same, display the toast message
            if (pictureOne.getDrawable() == pictureTwo.getDrawable()) {
                inflater = getLayoutInflater();
                view = inflater.inflate(R.layout.message, (ViewGroup) findViewById(R.id.messageL));
                view.setBackgroundResource(R.drawable.selector);
                textMessage = (TextView) view.findViewById(R.id.messageT);
                textMessage.setText(getResources().getText(R.string.message));
                toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.BOTTOM, 0, 50);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(view);
                toast.show();
            }

            {   // Check for A team
                if (pictureOne.getDrawable() == bird) {
                    scoreView1.setTextColor(getResources().getColor(R.color.bird));
                    first.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.bird)));
                } else if (pictureOne.getDrawable() == snake) {
                    scoreView1.setTextColor(getResources().getColor(R.color.score_2));
                    first.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.score_2)));
                } else if (pictureOne.getDrawable() == badger) {
                    scoreView1.setTextColor(getResources().getColor(R.color.badger));
                    first.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.badger)));
                } else if (pictureOne.getDrawable() == lion) {
                    scoreView1.setTextColor(getResources().getColor(R.color.score_1));
                    first.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.score_1)));
                }

                // Check for B team
                if (pictureTwo.getDrawable() == bird) {
                    scoreView2.setTextColor(getResources().getColor(R.color.bird));
                    second.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.bird)));
                } else if (pictureTwo.getDrawable() == snake) {
                    scoreView2.setTextColor(getResources().getColor(R.color.score_2));
                    second.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.score_2)));
                } else if (pictureTwo.getDrawable() == badger) {
                    scoreView2.setTextColor(getResources().getColor(R.color.badger));
                    second.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.badger)));
                } else if (pictureTwo.getDrawable() == lion) {
                    scoreView2.setTextColor(getResources().getColor(R.color.score_1));
                    second.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.score_1)));
                }
            }
        }
    }
}


