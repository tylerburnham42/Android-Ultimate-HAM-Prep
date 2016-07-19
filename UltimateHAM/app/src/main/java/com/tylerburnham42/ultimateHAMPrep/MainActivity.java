package com.tylerburnham42.ultimateHAMPrep;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Question currentQuestion;
    private List<Question> questionList;
    int currentScore=0;
    int currentID=0;
    int bestScore=0;
    Button answerA;
    Button answerB;
    Button answerC;
    Button answerD;
    private TextView problem;
    private TextView problemNumberText;
    private TextView scoreText;
    private TextView bestScoreText;
    private ProgressBar progressBar;
    private CustomCountDownTimer countDownTimer;
    private Boolean ranOutOfTime = false;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        DatabaseHandler db = new DatabaseHandler(this);
        //db.loadAllQuestions();
        questionList = db.getAllQuestions();
        currentQuestion = questionList.get(currentID);

        Button btncalcu=(Button)findViewById(R.id.imReadyButton);
        btncalcu.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                setContentView(R.layout.content_game);
                LoadGame();
                return;
            }

        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void LoadGame()
    {
        problem = (TextView) findViewById(R.id.questionText);
        problemNumberText = (TextView) findViewById(R.id.problemText);
        scoreText = (TextView) findViewById(R.id.scoreText);
        bestScoreText = (TextView) findViewById(R.id.bestScoreText);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(100);
        progressBar.setIndeterminate(false);
        countDownTimer = new CustomCountDownTimer(10000,100);
        countDownTimer.start();
        ranOutOfTime = false;

        scoreText.setText("0");
        problemNumberText.setText("0");


        answerA = (Button) findViewById(R.id.answerA);
        answerB = (Button) findViewById(R.id.answerB);
        answerC = (Button) findViewById(R.id.answerC);
        answerD = (Button) findViewById(R.id.answerD);
        answerA.setOnClickListener(this);
        answerB.setOnClickListener(this);
        answerC.setOnClickListener(this);
        answerD.setOnClickListener(this);

        loadQuestion();

    }

    public void loadQuestion()
    {
        problem.setText(currentQuestion.getProblem());
        answerA.setText(currentQuestion.getAnswer(Question.AnswerEnum.A));
        answerB.setText(currentQuestion.getAnswer(Question.AnswerEnum.B));
        answerC.setText(currentQuestion.getAnswer(Question.AnswerEnum.C));
        answerD.setText(currentQuestion.getAnswer(Question.AnswerEnum.D));

        answerA.setBackgroundColor(Color.LTGRAY);
        answerB.setBackgroundColor(Color.LTGRAY);
        answerC.setBackgroundColor(Color.LTGRAY);
        answerD.setBackgroundColor(Color.LTGRAY);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onClick(View v) {

        Boolean wasCorrect = false;
        switch(v.getId()) {
            case R.id.answerA: wasCorrect = currentQuestion.isCorrect(Question.AnswerEnum.A); break;
            case R.id.answerB: wasCorrect = currentQuestion.isCorrect(Question.AnswerEnum.B); break;
            case R.id.answerC: wasCorrect = currentQuestion.isCorrect(Question.AnswerEnum.C); break;
            case R.id.answerD: wasCorrect = currentQuestion.isCorrect(Question.AnswerEnum.D); break;
        }

        if(wasCorrect){
            if(!ranOutOfTime) {
                currentScore++;
                if (currentScore > bestScore) {
                    bestScore = currentScore;
                }
            }
            else{
                currentScore = 0;
            }
            //Update Timer
            countDownTimer.cancel();
            countDownTimer.start();
            ranOutOfTime = false;

            //Update question
            if (currentID > questionList.size()) {
                currentID = 0;
            } else {
                currentID++;
            }

            //load new question
            currentQuestion = questionList.get(currentID);
            loadQuestion();
        }
        else{
            currentScore = 0;

            switch(v.getId()) {
                case R.id.answerA: answerA.setBackgroundColor(Color.RED); break;
                case R.id.answerB: answerB.setBackgroundColor(Color.RED); break;
                case R.id.answerC: answerC.setBackgroundColor(Color.RED); break;
                case R.id.answerD: answerD.setBackgroundColor(Color.RED); break;
            }
        }

        //Update Score
        scoreText.setText(String.valueOf(currentScore));
        problemNumberText.setText(String.valueOf(currentID));
        bestScoreText.setText(String.valueOf(bestScore));

    }


    // CountDownTimer class
    public class CustomCountDownTimer extends CountDownTimer
    {
        private long startTime;

        public CustomCountDownTimer(long startTime, long interval)
                {
            super(startTime, interval);
            this.startTime = startTime;
        }

        @Override
        public void onFinish()
        {
            progressBar.setProgress(0);
            ranOutOfTime = true;

            answerA.setBackgroundColor(Color.RED);
            answerB.setBackgroundColor(Color.RED);
            answerC.setBackgroundColor(Color.RED);
            answerD.setBackgroundColor(Color.RED);

            switch (currentQuestion.getCorrect()){
                case A: answerA.setBackgroundColor(Color.GREEN); break;
                case B: answerB.setBackgroundColor(Color.GREEN); break;
                case C: answerC.setBackgroundColor(Color.GREEN); break;
                case D: answerD.setBackgroundColor(Color.GREEN); break;
            }

        }

        @Override
        public void onTick(long millisUntilFinished)
        {
            int percentLeft = (int)Math.round(((float)(startTime-millisUntilFinished)/startTime)*100);
            progressBar.setProgress(100-percentLeft);
        }
    }
}


