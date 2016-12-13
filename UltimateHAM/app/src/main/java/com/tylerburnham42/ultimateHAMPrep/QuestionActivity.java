package com.tylerburnham42.ultimateHAMPrep;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ProgressBar;

import com.tylerburnham42.ultimateHAMPrep.databinding.ContentGameBinding;

/**
 * Created by joseph on 12/12/16.
 */

public class QuestionActivity extends Activity implements View.OnClickListener {
    private ProgressBar progressBar;
    private ContentGameBinding binding;
    private boolean outOfTime;
    private CustomCountDownTimer countDownTimer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.content_game);
        binding.setQuestion(QuestionHandler.getNextQuestion());
        resetColors();
        binding.answerA.setOnClickListener(this);
        binding.answerB.setOnClickListener(this);
        binding.answerC.setOnClickListener(this);
        binding.answerD.setOnClickListener(this);
        binding.scoreText.setText(String.valueOf(QuestionHandler.getCurrentScore()));
        binding.problemText.setText(String.valueOf(binding.getQuestion().getID()));
        binding.bestScoreText.setText(String.valueOf(QuestionHandler.getBestScore()));
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(100);
        progressBar.setIndeterminate(false);
        outOfTime = false;
        countDownTimer = new CustomCountDownTimer(QuestionHandler.getTime(), 100);
        countDownTimer.start();
    }

    @Override
    public void onClick(View v) {
        if (v.equals(binding.answerA)) {
            onAnswerClicked(v, Question.AnswerEnum.A);
        } else if (v.equals(binding.answerB)) {
            onAnswerClicked(v, Question.AnswerEnum.B);
        } else if (v.equals(binding.answerC)) {
            onAnswerClicked(v, Question.AnswerEnum.C);
        } else {
            onAnswerClicked(v, Question.AnswerEnum.D);
        }
    }

    class CustomCountDownTimer extends CountDownTimer {
        private long startTime;

        public CustomCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
            this.startTime = startTime;
        }

        @Override
        public void onFinish() {
            progressBar.setProgress(0);
            outOfTime = true;

            binding.answerA.setBackgroundColor(Color.RED);
            binding.answerB.setBackgroundColor(Color.RED);
            binding.answerC.setBackgroundColor(Color.RED);
            binding.answerD.setBackgroundColor(Color.RED);

            switch (binding.getQuestion().getCorrect()) {
                case A:
                    binding.answerA.setBackgroundColor(Color.GREEN);
                    break;
                case B:
                    binding.answerB.setBackgroundColor(Color.GREEN);
                    break;
                case C:
                    binding.answerC.setBackgroundColor(Color.GREEN);
                    break;
                case D:
                    binding.answerD.setBackgroundColor(Color.GREEN);
                    break;
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {
            int percentLeft = (int) Math.round(((float) (startTime - millisUntilFinished) / startTime) * 100);
            progressBar.setProgress(100 - percentLeft);
        }
    }

    public void onAnswerClicked(View v, Question.AnswerEnum answer) {
        countDownTimer.cancel();
        progressBar.setProgress(0);
        outOfTime = false;
        if (answer == binding.getQuestion().getCorrect()) {
            if (!outOfTime) {
                QuestionHandler.incrementScore(v.getContext());
            } else {
                QuestionHandler.resetScore();
            }
            binding.setQuestion(QuestionHandler.getNextQuestion());
            countDownTimer = new CustomCountDownTimer(QuestionHandler.getTime(), 100);
            countDownTimer.start();
            resetColors();
        } else {
            QuestionHandler.resetScore();
            v.setBackgroundColor(Color.RED);
        }
        binding.scoreText.setText(String.valueOf(QuestionHandler.getCurrentScore()));
        binding.problemText.setText(String.valueOf(binding.getQuestion().getID()));
        binding.bestScoreText.setText(String.valueOf(QuestionHandler.getBestScore()));
    }

    private void resetColors() {
        if (binding != null) {
            binding.answerA.setBackgroundColor(Color.LTGRAY);
            binding.answerB.setBackgroundColor(Color.LTGRAY);
            binding.answerC.setBackgroundColor(Color.LTGRAY);
            binding.answerD.setBackgroundColor(Color.LTGRAY);
        }
    }
}
