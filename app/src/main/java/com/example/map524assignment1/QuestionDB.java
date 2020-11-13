package com.example.map524assignment1;

import java.util.Arrays;

public class QuestionDB {
    private static final Question[] questionBank ={
            new Question(R.string.question_1, true),
            new Question(R.string.question_2,false),
            new Question(R.string.question_3,
                    true),
            new Question(R.string.question_4,true),
            new Question(R.string.question_5, false)
    };

    private static final Integer[] questionColors = {
            R.color.AliceBlue,
            R.color.Aqua,
            R.color.DarkMagenta,
            R.color.SpringGreen,

    };
    public static Quiz createQuiz(){
        return new Quiz(Arrays.asList(questionBank),Arrays.asList(questionColors));
    }
}
