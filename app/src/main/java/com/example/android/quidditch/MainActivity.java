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

    public static final String KEY_IMAGE_ONE = "BITMAP ONE";
    public static final String KEY_IMAGE_TWO = "BITMAP TWO";
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("000");
    private int mTeamA = 0;
    private int mTeamB = 0;
    private int mImageId;
    private Button mScoreBtnA;
    private Button mScoreBtnB;
    private Button mRuleBtn;
    private Button mSnitchBtn;
    private RelativeLayout mLayoutShimmer, mLayoutTeamSelection;
    private LayoutInflater layoutInflater;
    private Drawable mBird;
    private Drawable mSnake;
    private Drawable mBadger;
    private boolean mSnitch = true;
    private Bitmap mBitmap;
    private TextView mTeamScoreA;
    private TextView mTeamScoreB;
    private TextView mWinner;
    private String mResetValueA;
    private String mResetValueB;
    private ImageView mTeamImageHolderA;
    private ImageView mTeamImageHolderB;
    private String mTeamScoreAString = String.valueOf((R.string.score1));
    private String mTeamScoreBString = String.valueOf((R.string.score2));
    private PopupWindow mPopupWindow;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mBitmap = ((BitmapDrawable) mTeamImageHolderA.getDrawable()).getBitmap();
        outState.putParcelable(KEY_IMAGE_ONE, mBitmap);
        mBitmap = ((BitmapDrawable) mTeamImageHolderB.getDrawable()).getBitmap();
        outState.putParcelable(KEY_IMAGE_TWO, mBitmap);
        outState.putInt(mTeamScoreAString, mTeamA);
        outState.putInt(mTeamScoreBString, mTeamB);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mBitmap = (savedInstanceState.getParcelable(KEY_IMAGE_ONE));
        mBitmap = (savedInstanceState.getParcelable(KEY_IMAGE_TWO));
        mTeamImageHolderA.setImageBitmap(mBitmap);
        mTeamImageHolderB.setImageBitmap(mBitmap);
        mTeamA = (savedInstanceState.getInt(mTeamScoreAString));
        mTeamB = (savedInstanceState.getInt(mTeamScoreBString));
        mResetValueA = DECIMAL_FORMAT.format(mTeamA);
        mResetValueB = DECIMAL_FORMAT.format(mTeamB);
        mTeamScoreA.setText(mResetValueA);
        mTeamScoreB.setText(mResetValueB);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ShimmerFrameLayout shimmerFrameLayout = (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container);
        shimmerFrameLayout.setDuration(1000);
        shimmerFrameLayout.setAutoStart(true);

        mRuleBtn = (Button) findViewById(R.id.game_rules);
        mSnitchBtn = (Button) findViewById(R.id.snitch_btn);
        mTeamScoreA = (TextView) findViewById(R.id.team_A_score);
        mTeamScoreB = (TextView) findViewById(R.id.team_B_score);
        mLayoutShimmer = (RelativeLayout) findViewById(R.id.container_for_shimmer);
        mLayoutTeamSelection = (RelativeLayout) findViewById(R.id.player_selection_layout);
        mTeamImageHolderA = (ImageView) findViewById(R.id.icon_team_A);
        mTeamImageHolderB = (ImageView) findViewById(R.id.icon_team_B);
        mScoreBtnA = (Button) findViewById(R.id.score_team_A_btn);
        mScoreBtnB = (Button) findViewById(R.id.score_team_B_btn);
        ImageView ravenclaw = (ImageView) findViewById(R.id.pic_1);
        ImageView slytherin = (ImageView) findViewById(R.id.pic_2);
        ImageView hufflepuff = (ImageView) findViewById(R.id.pic_3);
        ImageView gryffindor = (ImageView) findViewById(R.id.pic_4);

        mResetValueA = DECIMAL_FORMAT.format(mTeamA);
        mResetValueB = DECIMAL_FORMAT.format(mTeamB);

        mBird = getDrawable(R.drawable.ravenclaw);
        mSnake = getDrawable(R.drawable.slyth);
        mBadger = getDrawable(R.drawable.hufflepuff);
        Drawable lion = getDrawable(R.drawable.gryf);

        ravenclaw.setImageDrawable(mBird);
        slytherin.setImageDrawable(mSnake);
        hufflepuff.setImageDrawable(mBadger);
        gryffindor.setImageDrawable(lion);

        mTeamScoreA.onSaveInstanceState();
        mTeamScoreB.onSaveInstanceState();

         /*
         * Display PopupWindow for the game rules.
         */
        mRuleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.rule, null);
                mPopupWindow = new PopupWindow(container, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, true);
                mPopupWindow.showAtLocation(mRuleBtn, Gravity.CENTER, 0, 0);
                container.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent event) {
                        mPopupWindow.dismiss();
                        return true;
                    }
                });
            }
        });

        /*
         * Display PopupWindow to show the winner.
         */
        mSnitchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button3()) {
                    layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    ViewGroup container = null;
                    if (layoutInflater != null) {
                        container = (ViewGroup) layoutInflater.inflate(winner, null);
                    }
                    mPopupWindow = new PopupWindow(container, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, true);
                    mPopupWindow.showAtLocation(mSnitchBtn, Gravity.CENTER, 0, 0);
                    mWinner = (TextView) mPopupWindow.getContentView().findViewById(R.id.winner_text);
                    if (mSnitch)
                        mWinner.setText(R.string.winner_B);
                    else
                        mWinner.setText(R.string.winner_A);
                    mPopupWindow.setTouchable(true);
                    if (container != null) {
                        container.setOnTouchListener(new View.OnTouchListener() {

                            @Override
                            public boolean onTouch(View view, MotionEvent event) {
                                mPopupWindow.dismiss();
                                return true;
                            }
                        });
                    }
                }
            }
        });
    }

    public boolean button3() {
        Random randomGenerator = new Random();
        mSnitch = randomGenerator.nextBoolean();
        if (mSnitch) {
            mTeamB += 150;
            displayForTeamB(mTeamB);


        } else {
            mTeamA += 150;
            displayForTeamA(mTeamA);
        }
        return true;
    }

    public void button1(View view) {
        mTeamA += 10;
        displayForTeamA(mTeamA);
    }

    public void button2(View view) {
        mTeamB += 10;
        displayForTeamB(mTeamB);
    }

    /*
     * Reset score if the user changes the team.
     */
    public void resetScore() {
        mTeamA = 0;
        mTeamB = 0;
        displayForTeamA(mTeamA);
        mTeamScoreA.setText(R.string.reset);
        displayForTeamB(mTeamB);
        mTeamScoreB.setText(R.string.reset);
    }

    public void resetScore(View view) {
        resetScore();
    }


    /*
     * Displays the score for team A.
     */
    public void displayForTeamA(int score) {
        mResetValueA = DECIMAL_FORMAT.format(mTeamA);
        mTeamScoreA.setText(String.valueOf(score));
        mTeamScoreA.setText(mResetValueA);
    }

    /*
     * Displays the score for team B.
     */
    public void displayForTeamB(int score) {
        mResetValueB = DECIMAL_FORMAT.format(mTeamB);
        mTeamScoreB.setText(String.valueOf(score));
        mTeamScoreB.setText(mResetValueB);
    }

    public void gameRules(View view) {
        mRuleBtn.callOnClick();
    }

    public void button3(View view) {
        button3();
    }

    public void click(View view) {
        mImageId = view.getId();
        mLayoutShimmer.setVisibility(View.GONE);
        mLayoutTeamSelection.setVisibility(View.VISIBLE);
    }

    /*
     * Method to select the team.
     */
    public void selectTeam(View picture) {
        ImageView chosenImage = (ImageView) picture;
        ImageView findIt = (ImageView) findViewById(mImageId);
        Drawable drawable = chosenImage.getDrawable();
        findIt.setImageDrawable(drawable);

        resetScore();

        if (mTeamImageHolderA.getDrawable() == drawable || mTeamImageHolderB.getDrawable() == drawable) {
            mLayoutShimmer.setVisibility(View.VISIBLE);
            mLayoutTeamSelection.setVisibility(View.GONE);

            /*
             * Check whether two chosen teams are the same and display the toast message.
             */
            if (mTeamImageHolderA.getDrawable() == mTeamImageHolderB.getDrawable()) {
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.message, (ViewGroup) findViewById(R.id.messageL));
                view.setBackgroundResource(R.drawable.selector);

                TextView textMessage = (TextView) view.findViewById(R.id.error_toast_message);
                textMessage.setText(getResources().getText(R.string.message));

                Toast toast = new Toast(this);
                toast.setGravity(Gravity.BOTTOM, 0, 50);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(view);
                toast.show();
            }

            {
                /*
                * For team A: Set text color and background tint for buttons according to the selected team.
                */
                if (mTeamImageHolderA.getDrawable() == mBird) {
                    mTeamScoreA.setTextColor(getResources().getColor(R.color.bird));
                    mScoreBtnA.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.bird)));
                } else if (mTeamImageHolderA.getDrawable() == mSnake) {
                    mTeamScoreA.setTextColor(getResources().getColor(R.color.score_slyth));
                    mScoreBtnA.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.score_slyth)));
                } else if (mTeamImageHolderA.getDrawable() == mBadger) {
                    mTeamScoreA.setTextColor(getResources().getColor(R.color.badger));
                    mScoreBtnA.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.badger)));
                } else {
                    mTeamScoreA.setTextColor(getResources().getColor(R.color.score_gryf));
                    mScoreBtnA.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.score_gryf)));
                }

                /*
                * For team B: Set text color and background tint for buttons according to the selected team.
                */
                if (mTeamImageHolderB.getDrawable() == mBird) {
                    mTeamScoreB.setTextColor(getResources().getColor(R.color.bird));
                    mScoreBtnB.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.bird)));
                } else if (mTeamImageHolderB.getDrawable() == mSnake) {
                    mTeamScoreB.setTextColor(getResources().getColor(R.color.score_slyth));
                    mScoreBtnB.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.score_slyth)));
                } else if (mTeamImageHolderB.getDrawable() == mBadger) {
                    mTeamScoreB.setTextColor(getResources().getColor(R.color.badger));
                    mScoreBtnB.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.badger)));
                } else {
                    mTeamScoreB.setTextColor(getResources().getColor(R.color.score_gryf));
                    mScoreBtnB.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.score_gryf)));
                }
            }
        }
    }
}


