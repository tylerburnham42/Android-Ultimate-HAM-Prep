package com.tylerburnham42.ultimateHAMPrep;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;
import java.util.Random;

/**
 * Created by joseph on 12/12/16.
 */

public class QuestionHandler {
    private static final String SHARED_PREFERENCES_FILE_NAME = "Question handler file name";
    private static final String SHARED_PREFERENCES_BEST_SCORE_KEY = "Question handler shared prefs best score key";
    private static boolean isRandom;
    private static int currentQuestion;
    private static List<Question> questionList;
    private static int currentScore = 0;
    private static int bestScore;
    private static int timerTime = 120000;

    public static void init(Context context){
        isRandom = false;
        currentQuestion = 0;
        initializeQuestionList(context);
        initializeBestScore(context);
    }

    public static void setIsRandom(boolean shouldBeRandom){
        isRandom = shouldBeRandom;
    }

    public static Question getNextQuestion(){
        if (isRandom) return questionList.get((new Random()).nextInt(questionList.size()));
        return questionList.get(currentQuestion++ % questionList.size());
    }

    public static int getCurrentScore(){
        return currentScore;
    }

    public static void resetScore(){
        currentScore = 0;
    }

    public static void incrementScore(Context context){
        currentScore++;
        if (currentScore > bestScore){
            bestScore = currentScore;
            setBestScore(context);
        }
    }

    public static int getBestScore(){
        return bestScore;
    }

    public static int getTime(){
        return timerTime;
    }

    public static void setTime(int time){
       timerTime = time;
    }

    public static void setBestScore(Context context){
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, Context.MODE_APPEND);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putInt(SHARED_PREFERENCES_BEST_SCORE_KEY, bestScore);
        edit.commit();
    }

    private static void initializeQuestionList(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        questionList = db.getAllQuestions();
    }

    private static void initializeBestScore(Context context){
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        bestScore = prefs.getInt(SHARED_PREFERENCES_BEST_SCORE_KEY, 0);
    }
}
