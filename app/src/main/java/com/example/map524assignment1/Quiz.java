package com.example.map524assignment1;

public class Quiz {
    private int quizSize;
    private int currentQuestion;
    private int correct;
    private Question[] questions;


    public Quiz(Question[] quests){
        this.quizSize=quests.length;
        this.correct=0;
        this.currentQuestion=0;
        this.questions= new Question[this.quizSize];
        System.arraycopy(quests,0,this.questions,0,this.questions.length);
    }

    public int getCurrentQuestion(){
        return this.currentQuestion+1;
    }

    public int getTotalQuestions(){
        return this.quizSize;
    }

    public String getQuestion()throws QuizException{
        if(this.isFinished()) throw new QuizException("Next question does not exist");
        return this.questions[this.currentQuestion].getQuestion();
    }

    public boolean isFinished(){
        return this.currentQuestion==this.quizSize;
    }

    public boolean answerNextQuestion(boolean attempt)throws QuizException{
        if(this.isFinished()) throw new QuizException("Next question does not exist");
        return this.questions[this.currentQuestion++].answerQuestion(attempt);

    }

    public void reset(boolean newOrder){

        this.correct=0;
        this.currentQuestion=0;
        if(newOrder)
            this.shuffle();

    }

    public void shuffle(){
        
    }
}

