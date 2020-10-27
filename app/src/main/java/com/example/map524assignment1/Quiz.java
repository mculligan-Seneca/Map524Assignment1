package com.example.map524assignment1;

import java.util.Arrays;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Collections;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class Quiz implements Parcelable {
    private int quizSize;
    private int currentQuestion;
    private int correct;
    private ArrayList<Question> questions;
    private ArrayList<Integer> colors;


    public Quiz(Collection<Question> quests, Collection<Integer> colors){
        this.quizSize=quests.size();
        this.correct=0;
        this.currentQuestion=0;
        this.questions = new ArrayList<Question>(quests);
        this.colors= new ArrayList<>(colors);

    }

    protected Quiz(Parcel in) {
        quizSize = in.readInt();
        currentQuestion = in.readInt();
        correct = in.readInt();
        questions = in.createTypedArrayList(Question.CREATOR);
    }

    public static final Creator<Quiz> CREATOR = new Creator<Quiz>() {
        @Override
        public Quiz createFromParcel(Parcel in) {
            return new Quiz(in);
        }

        @Override
        public Quiz[] newArray(int size) {
            return new Quiz[size];
        }
    };

    public int getCorrect(){
        return this.correct;
    }
    public int getQuestionsCompleted(){
        return this.currentQuestion;
    }

    public int getTotalQuestions(){
        return this.quizSize;
    }

    public int getQuestion(){






        return !this.isFinished()?this.questions.get(this.currentQuestion).getQuestion():
                R.string.error_msg;
    }

    public int getQuestionColor(){
        return this.colors.get(this.currentQuestion%this.colors.size());
    }
    public boolean isFinished(){
        return this.currentQuestion==this.quizSize;
    }

    public boolean answerNextQuestion(int attempt){
        boolean isCorrect=false;
        if(!this.isFinished()) {

            isCorrect = this.questions.get(this.currentQuestion++).answerQuestion(attempt);
            if (isCorrect)
                this.correct++;
        }
        return isCorrect;
    }

    public void reset(boolean newOrder){
        //resets the state of th quiz
        this.correct=0;
        this.currentQuestion=0;
        if(newOrder) {//when newOrder is true the questions are shuffled
            Collections.shuffle(this.questions);
            Collections.shuffle(this.colors);
        }

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(this.quizSize);
        dest.writeInt(this.currentQuestion);
        dest.writeInt(this.correct);
        dest.writeTypedList(this.questions);
        dest.writeList(this.colors);
    }
}

