package com.example.map524assignment1;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.util.Log;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the { QuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_COLOR = "color_";
    public  static final String ARG_TEXT = "text_";

    // TODO: Rename and change types of parameters

   // private View v;
    private int colour;
    private int text;

    public QuestionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment QuestionFragment.
     */
    // TODO: Rename and change types and number of parameters
   public static QuestionFragment newInstance(int text, int colour) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putInt(QuestionFragment.ARG_TEXT,text);
        args.putInt(QuestionFragment.ARG_COLOR,colour);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments()!=null){
            this.text = getArguments().getInt(ARG_TEXT);
            this.colour = getArguments().getInt(ARG_COLOR);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View v =  inflater.inflate(R.layout.fragment_question, container, false);
         //this.setQuestion();
        v.setBackgroundResource(this.colour);

        ((TextView)v.findViewById(R.id.question_id)).setText(this.text);

        return v;
    }





}