/*
Mitchell Culligan
161293170
mculligan@myseneca.ca
Nov 17th,2020
Lab 4

 */
package com.example.map524assignment1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.content.Context;
import android.content.DialogInterface;

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

public class MainActivity extends AppCompatActivity implements DialogInterface.OnClickListener{


    private Quiz quiz;
    private StorageManager storage;
    private FragmentManager fm;
    private ProgressBar progress;
    private AlertDialog.Builder builder;
    private Button true_btn;
    private Button false_btn;
    private static int language=1;
    private Menu menu;

        @Override
        public void onClick(DialogInterface dialog, int id) {
            this.storage.saveAverageInternal(this,this.quiz.calculateAverage());
            this.quiz.reset(DialogInterface.BUTTON_POSITIVE==id);

            this.setQuizDisplay();
        }

    public void viewClick(View v) {



               if (quiz.answerNextQuestion(v.getId()==R.id.true_btn ))
                   Toast.makeText(getApplicationContext(), getResources()
                                   .getString(R.string.right_answer),
                           Toast.LENGTH_SHORT)
                           .show();
                else{
                    Toast.makeText(getApplicationContext(), getResources()
                            .getString(R.string.wrong_answer),
                            Toast.LENGTH_SHORT)
                            .show();

               }
                setQuizDisplay();


    }
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if(savedInstanceState==null) {
            this.quiz = QuestionDB.createQuiz();
            //this.language=1;

        }
        else{
            this.quiz = savedInstanceState.getParcelable("saved_quiz");
            //language=savedInstanceState.getInt("lang");
            if(language==1)
                this.changeLanguage("en");
            else
                this.changeLanguage("fr");


        }


        setContentView(R.layout.activity_main);


        this.storage= new StorageManager();
        this.builder = new AlertDialog.Builder(this);

        this.progress= findViewById(R.id.quiz_progress);
        this.true_btn = findViewById(R.id.true_btn);
        this.false_btn = findViewById(R.id.false_btn);

        this.progress.setMax(this.quiz.getTotalQuestions());
        this.fm = getSupportFragmentManager();

        this.true_btn.setText(R.string.t_btn);
        this.false_btn.setText(R.string.f_btn);

        this.setQuizDisplay();
    }




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

                   this.changeLanguage("fr");
                  // LocaleHelper.updateLocale(this,"fr");
                }else if(language==2){
                    language=1;
                   this.changeLanguage("en");
                 //  LocaleHelper.updateLocale(this,"en");
                }

                finish();
                startActivity(getIntent());


                break;
            case R.id.get_average:
                this.displayAverages();
                break;
            case R.id.reset_average:
                this.storage.fileReset(this);
                Toast.makeText(getApplicationContext(),R.string.reset_complete,Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    public void displayAverages(){
        AlertDialog.Builder build= new AlertDialog.Builder(this);

        build.setMessage(this.formatAverages())
                .setCancelable(true)
                .setNegativeButton(R.string.cancel,null)
                .setTitle(R.string.get_averages);

        build.create().show();

    }


    public String formatAverages(){
           final String QUIZ_STR = getResources().getString(R.string.quiz);
           final String AVERAGE=getResources().getString(R.string.total_average);
            StringBuilder sb=new StringBuilder();
            double totalAvg=0.0;
            double next;
            String[] averages= this.storage.readAverageDataInternal(this).split(" ");


                    for(int i=0;i<averages.length;i++) {
                        next=Double.parseDouble(averages[i]);
                        totalAvg += next;
                        sb.append(String.format(Locale.getDefault(), "%s %d: %s\n",QUIZ_STR ,
                                i+1, NumberFormat.getPercentInstance().format(next)));

                    }

            if(averages.length>0)
                totalAvg/=averages.length;


            sb.append(String.format(Locale.getDefault(),"%s: %s",AVERAGE,
                NumberFormat.getPercentInstance().format(totalAvg)));

            return sb.toString();
    }
    public void setQuizDisplay(){
        //FragmentTransaction ft=null;

        this.progress.setProgress(this.quiz.getQuestionsCompleted());
        if(!this.quiz.isFinished()){
               
                this.updateFragment();

        }
        else{

            this.builder.setMessage(this.generateCompletionMessage())
            .setCancelable(true)
            .setPositiveButton(R.string.repeat,this )
            .setNegativeButton(R.string.cancel,this)
            .setTitle(R.string.app_name);
            AlertDialog alert = this.builder.create();
            alert.show();

        }
    }

    private void updateFragment(){
        FragmentTransaction transaction = this.fm.beginTransaction();
        Fragment question = fm.findFragmentById(R.id.question_frame_id);

        if(question==null){

            transaction.add(R.id.question_frame_id,
                    QuestionFragment.newInstance(this.quiz.getQuestion(),this.quiz.getQuestionColor()));

        } else{

            transaction.replace(R.id.question_frame_id,
                    QuestionFragment.newInstance(this.quiz.getQuestion(),this.quiz.getQuestionColor()));
        }
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }

    public String generateCompletionMessage(){

        String message = getResources().getString(R.string.completion_mssg);



        return String.format(Locale.getDefault(),"%s %d / %d  %s\n",message,this.quiz.getCorrect(),
                this.quiz.getTotalQuestions(),
                NumberFormat.getPercentInstance().format(this.quiz.calculateAverage()));

    }



  public void changeLanguage(String tag){


        Locale locale=new Locale(tag);
        Resources res = super.getBaseContext().getResources();
        Configuration config = res.getConfiguration();
        Locale.setDefault(locale);

        config.locale=locale;

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
          config.setLayoutDirection(locale);
      }
      res.updateConfiguration(config,res.getDisplayMetrics());



    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);

        outState.putParcelable("saved_quiz",this.quiz);
      // outState.putInt("lang",language);

    }


}