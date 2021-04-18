package com.example.the_cosmic_code;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

import java.util.concurrent.TimeUnit;


public class DialogAstronaut extends Fragment {
    private String[] text = {
            "О, ты новенький.",
            "Привет, меня зовут Никита.",
            "Я пилот одно из кораблей Космопочты.",
            "Чтобы начать добаввь себе корабль.",
            "Дай ему имя и максимальный весс.",
            "После нажми на свой корабль.",
            "Дальше разберешься там не сложно."
    };

    private TextView textView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dialog_astronaut, container, false);
        textView = v.findViewById(R.id.tv_dialog_astronaut);

        AsyncTask task = new AsyncTask();
        task.execute();

        return v;
    }


    class AsyncTask extends android.os.AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            for (int j = 0; j < text.length; j++) {
                StringBuilder s = new StringBuilder(text[j].length());
                for (int i = 0; i < text[j].length(); i++) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                        s.append(text[j].charAt(i));
                        textView.setText(s);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            return null;
        }
    }
}

