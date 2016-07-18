/*
package com.example.a42.ultimatehamquiz;
import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 42 on 7/13/2016.

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "questionsDB";
    // tasks table name
    private static final String TABLE_QUEST = "quest";
    // tasks Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_PROBLEM = "Problem";
    private static final String KEY_ANSWER = "correct"; //correct option
    private static final String KEY_ANSWERA= "answerA";
    private static final String KEY_ANSWERB= "answerB";
    private static final String KEY_ANSWERC= "answerC";
    private static final String KEY_ANSWERD= "answerD";
    private SQLiteDatabase dbase;
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
        dbase = db;
    }

    public void forceUpdate(){
        dbase.execSQL("DROP TABLE IF EXISTS " + TABLE_QUEST);
        // Create tables again
        onCreate(dbase);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        dbase=db;
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_QUEST + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_PROBLEM
                + " TEXT, " + KEY_ANSWER + " TEXT, "+KEY_ANSWERA +" TEXT, "
                +KEY_ANSWERB +" TEXT, "+KEY_ANSWERC +" TEXT, "+KEY_ANSWERD+" TEXT)";
        db.execSQL(sql);
        addQuestions();
        //db.close();

    }

    private void addQuestions()
    {
        Question q1=new Question(
                "Which of the following is a purpose of the Amateur Radio Service as stated in the FCC rules and regulations?",
                "A. Providing personal radio communications for as many citizens as possible",
                "B. Providing communications for international non-profit organizations",
                "C. Advancing skills in the technical and communication phases of the radio art",
                "D. All of these choices are correct );",
                Question.AnswerEnum.C);
        this.addQuestion(q1);
        Question q2=new Question(
                "Which agency regulates and enforces the rules for the Amateur Radio Service in the United States?",
                "A. FEMA",
                "B. The ITU",
                "C. The FCC",
                "D. Homeland Security",
                Question.AnswerEnum.C);
        this.addQuestion(q2);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUEST);
        // Create tables again
        onCreate(db);
    }
    // Adding new question
    public void addQuestion(Question quest) {

        ContentValues values = new ContentValues();
        values.put(KEY_PROBLEM, quest.getProblem());
        values.put(KEY_ANSWER, quest.getCorrect().toString());
        values.put(KEY_ANSWERA, quest.getAnswer(Question.AnswerEnum.A));
        values.put(KEY_ANSWERB, quest.getAnswer(Question.AnswerEnum.B));
        values.put(KEY_ANSWERC, quest.getAnswer(Question.AnswerEnum.C));
        values.put(KEY_ANSWERC, quest.getAnswer(Question.AnswerEnum.D));
        // Inserting Row
        dbase.insert(TABLE_QUEST, null, values);
    }
    public List<Question> getAllQuestions() {
        List<Question> quesList = new ArrayList<Question>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_QUEST;
        dbase=this.getReadableDatabase();
        Cursor cursor = dbase.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Question quest = new Question();
                quest.setID(cursor.getInt(0));
                quest.setProblem(cursor.getString(1));
                quest.setCorrect(Question.AnswerEnum.valueOf(cursor.getString(2)));
                quest.setAnswer(Question.AnswerEnum.A,cursor.getString(3));
                quest.setAnswer(Question.AnswerEnum.A,cursor.getString(4));
                quest.setAnswer(Question.AnswerEnum.A,cursor.getString(5));
                quest.setAnswer(Question.AnswerEnum.A,cursor.getString(6));
                quesList.add(quest);
            } while (cursor.moveToNext());
        }
        // return quest list
        return quesList;
    }
    public int rowcount()
    {
        int row=0;
        String selectQuery = "SELECT  * FROM " + TABLE_QUEST;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        row=cursor.getCount();
        return row;
    }
}

*/