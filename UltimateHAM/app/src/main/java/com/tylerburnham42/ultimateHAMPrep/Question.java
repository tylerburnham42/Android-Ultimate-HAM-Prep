package com.tylerburnham42.ultimateHAMPrep;

/**
 * Created by 42 on 7/13/2016.
 */
public class Question {
    public enum AnswerEnum {A,B,C,D}
    private int id;
    private String problem;
    private String answerA;
    private String answerB;
    private String answerC;
    private String answerD;
    private AnswerEnum correct;


    public Question (){
        id = 0;
        problem = "";
        answerA = "";
        answerB = "";
        answerC = "";
        answerD = "";
        correct = AnswerEnum.A;
    }

    public Question (int id, String problem, String answerA, String answerB, String answerC, String answerD, AnswerEnum correct){
        this.id = id;
        this.problem = problem;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
        this.correct = correct;
    }

    public void setID(int id){
        this.id = id;
    }
    public int getID() {return id;}

    public String getProblem(){
        return problem;
    }
    public void setProblem(String problem){
        this.problem = problem;
    }
    public String getAnswer(AnswerEnum answer){
        switch (answer){
            case A: return answerA;
            case B: return answerB;
            case C: return answerC;
            case D: return answerD;
        }
        return null;
    }

    public Boolean isCorrect(AnswerEnum guess)
    {
        return (guess == correct);
    }

    public void setAnswer(AnswerEnum answer, String value){
        switch (answer){
            case A: answerA = value; break;
            case B: answerB = value; break;
            case C: answerC = value; break;
            case D: answerD = value; break;
        }
    }

    public AnswerEnum getCorrect(){
        return correct;
    }

    public void setCorrect(AnswerEnum answer){
        correct = answer;
    }

}
