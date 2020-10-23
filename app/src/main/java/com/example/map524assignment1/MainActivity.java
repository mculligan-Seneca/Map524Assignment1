/*
Mitchell Culligan
161293170
mculligan@myseneca.ca
Oct 12th 2020
 */
package com.example.map524assignment1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.content.DialogInterface;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.ProgressBar;
import android.app.AlertDialog;
import android.widget.Toast;
import android.os.Bundle;
import android.content.res.Resources;
import android.view.MenuItem;

import java.text.NumberFormat;
import androidx.fragment.app.Fragment;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    private Quiz quiz;
    private FragmentManager fm;
    private ProgressBar progress;
    private AlertDialog.Builder builder;
    private Button true_btn;
    private Button false_btn;
    private static int language=1;
    private Menu menu;
    private  final DialogInterface.OnClickListener dialogClick = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int id) {
            quiz.reset(DialogInterface.BUTTON_POSITIVE==id);
            setQuizDisplay();
        }
    };
    public void viewClick(View v) {



                if (quiz.answerNextQuestion(v.getId() ))
                    Toast.makeText(getApplicationContext(),R.string.right_answer,Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),R.string.wrong_answer,Toast.LENGTH_SHORT).show();


                setQuizDisplay();


    }
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        if(savedInstanceState==null) {
            this.quiz = QuestionDB.createQuiz();
            //this.language=1;
            Log.d("init_state","instance state was not saved");
        }
        else{
            this.quiz = savedInstanceState.getParcelable("saved_quiz");
            language=savedInstanceState.getInt("lang");
            if(language==1)
                this.changeLanguage("en");
            else
                this.changeLanguage("fr");




        }



        Log.d("lang_init",Locale.getDefault().toLanguageTag());


        this.builder = new AlertDialog.Builder(this);

        this.progress= findViewById(R.id.quiz_progress);
        this.true_btn = findViewById(R.id.true_btn);
        this.false_btn = findViewById(R.id.false_btn);

        this.progress.setMax(this.quiz.getTotalQuestions());
        this.fm = getSupportFragmentManager();



        this.setQuizDisplay();
    }



 /*   @Override
    protected void attachBaseContext(Context newBase ){
            String tag;
        if(language==1)
            tag=Locale.ENGLISH.toLanguageTag();
        else
            tag=Locale.FRENCH.toLanguageTag();
        super.attachBaseContext(LocaleHelper.updateLocale(newBase,tag));
    }
*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.getMenuInflater().inflate(R.menu.lang_menu,menu);
        this.menu=menu;
        this.updateMenu();
        return super.onCreateOptionsMenu(menu);
    }



    public void updateMenu(){
        if(this.menu!=null){
            if(language==1){
                this.menu.findItem(R.id.lang_changeId).setTitle(R.string.French);

            } else if(language==2){
                this.menu.findItem(R.id.lang_changeId).setTitle(R.string.English);
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch(item.getItemId()) {
            case R.id.lang_changeId:

                if(language==1) {
                    language = 2;
                    Log.d("updated_lang",String.valueOf(language));
                    this.changeLanguage("fr");
               //     LocaleHelper.updateLocale(this,"fr");
                }else if(language==2){
                    language=1;
                    this.changeLanguage("en");
                 //   LocaleHelper.updateLocale(this,"en");
                }

                finish();
                startActivity(getIntent());


                break;
        }

        return super.onOptionsItemSelected(item);
    }
    public void setQuizDisplay(){
        //FragmentTransaction ft=null;

        this.progress.setProgress(this.quiz.getQuestionsCompleted());
        if(!this.quiz.isFinished()){
                //ft = this.fm.beginTransaction();
                //maybe not best performance
                //ft.replace(R.id.quest_frag_id,QuestionFragment.newInstance(this.quiz.getQuestionInfo());
                this.updateFragment();

        }
        else{

            this.builder.setMessage(this.generateCompletionMessage())
            .setCancelable(true)
            .setPositiveButton(R.string.repeat,this.dialogClick )
            .setNegativeButton(R.string.cancel,this.dialogClick)
            .setTitle(R.string.app_name);
            AlertDialog alert = this.builder.create();
            alert.show();

        }
    }

    private void updateFragment(){
        FragmentTransaction transaction = this.fm.beginTransaction();
        Fragment question = fm.findFragmentById(R.id.question_frame_id);

        if(question==null){
            transaction.add(R.id.question_frame_id,QuestionFragment.newInstance(this.quiz.getQuestionInfo()));

        } else{
            transaction.replace(R.id.question_frame_id,QuestionFragment.newInstance(this.quiz.getQuestionInfo()));
        }
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }

    public String generateCompletionMessage(){
        int correct = this.quiz.getCorrect();
        int totalQuestions=this.quiz.getTotalQuestions();
        String message = getResources().getString(R.string.completion_mssg);
        double quizPercentage = (double)correct/totalQuestions;


        return String.format(Locale.getDefault(),"%s %d / %d  %s\n",message,correct,totalQuestions,
                NumberFormat.getPercentInstance().format(quizPercentage));

    }



    public void changeLanguage(String tag){


        Locale locale=new Locale(tag);
        //Context context = super.getBaseContext();
        Resources res = super.getBaseContext().getResources();
        Configuration config = res.getConfiguration();
        Locale.setDefault(locale);
      /*  if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N)
            config.setLocale(locale);
        else */
        config.locale=locale;

        config.setLayoutDirection(locale);
            res.updateConfiguration(config,res.getDisplayMetrics());



    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);

        outState.putParcelable("saved_quiz",this.quiz);
        outState.putInt("lang",language);

    }


}