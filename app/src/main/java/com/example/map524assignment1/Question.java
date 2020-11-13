package com.example.map524assignment1;

import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class Question implements Parcelable {
    private int question;
    private boolean answer;
    //TODO change to boolean

    public Question(int question, boolean answer){
        this.question=question;
        this.answer=answer;
    }

    protected Question(Parcel in) {
        question = in.readInt();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            answer = in.readBoolean();
        }
        else{
            answer = in.readInt()==1;
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(question);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            dest.writeBoolean(this.answer);
        }
        else
        {
            dest.writeInt(this.answer?1:0);
        }
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

    public int getQuestion(){


            return this.question;
    }

    public boolean answerQuestion(boolean attempt){
        return this.answer==attempt;
    }

}
