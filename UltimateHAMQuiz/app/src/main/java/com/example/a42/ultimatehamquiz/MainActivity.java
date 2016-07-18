package com.example.a42.ultimatehamquiz;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
    private Button answerA;
    private Button answerB;
    private Button answerC;
    private Button answerD;
    private TextView problem;
    private TextView problemNumberText;
    private TextView scoreText;
    private TextView bestScoreText;
    private ProgressBar progressBar;
    private CustomCountDownTimer countDownTimer;
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

        scoreText.setText("00");
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onClick(View v) {
        if(v.getId() == R.id.imReadyButton){
            setContentView(R.layout.content_game);
            LoadGame();
            return;
        }

        countDownTimer.cancel();
        countDownTimer.start();

        Boolean wasCorrect = false;
        switch(v.getId()) {
            case R.id.answerA: wasCorrect = currentQuestion.isCorrect(Question.AnswerEnum.A); break;
            case R.id.answerB: wasCorrect = currentQuestion.isCorrect(Question.AnswerEnum.B); break;
            case R.id.answerC: wasCorrect = currentQuestion.isCorrect(Question.AnswerEnum.C); break;
            case R.id.answerD: wasCorrect = currentQuestion.isCorrect(Question.AnswerEnum.D); break;
        }

        if(wasCorrect){
            currentScore ++;
            if(currentScore > bestScore){
                bestScore = currentScore;
            }
            currentID ++;
        }
        else{
            currentScore = 0;
            currentID = 0;
        }

        //Get next Question
        currentQuestion = questionList.get(currentID);

        //Update Score
        scoreText.setText(String.valueOf(currentScore));
        problemNumberText.setText(String.valueOf(currentID));
        bestScoreText.setText(String.valueOf(bestScore));

        loadQuestion();
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
        }

        @Override
        public void onTick(long millisUntilFinished)
        {
            int percentLeft = (int)Math.round(((float)(startTime-millisUntilFinished)/startTime)*100);
            progressBar.setProgress(100-percentLeft);
        }
    }

}


