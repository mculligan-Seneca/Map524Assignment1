package com.example.map524assignment1;

public class Question {
    private String question;
    private boolean answer;


    public Question(String question, boolean answer){
        this.question=question;
        this.answer=answer;
    }

    public String getQuestion(){
        return this.question;
    }

    public boolean answerQuestion(boolean attempt){
        return this.answer==attempt;
    }

}
