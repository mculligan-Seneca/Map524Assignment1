package com.example.map524assignment1;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class Question implements Parcelable {
    private int question;
    private int answer;


    public Question(int question, int answer){
        this.question=question;
        this.answer=answer;
    }

    protected Question(Parcel in) {
        question = in.readInt();
        answer = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(question);
        dest.writeInt(answer);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public Bundle getQuestion(Bundle args){

            args.putInt(QuestionFragment.ARG_TEXT,this.question);
            return args;
    }

    public boolean answerQuestion(int attempt){
        return this.answer==attempt;
    }

}
