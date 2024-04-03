package com.example.edith.fragments;

import android.annotation.SuppressLint;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.edith.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link todoList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class todoList extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public todoList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment todoList.
     */
    // TODO: Rename and change types and number of parameters
    public static todoList newInstance(String param1, String param2) {
        todoList fragment = new todoList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_todo_list, container, false);

        TextView hello = rootView.findViewById(R.id.Hello);
        hello.setText("Hello, Vancence");
        hello.setTextColor(getResources().getColor(R.color.white));
        Shader textShader = new LinearGradient(0, 0, hello.getPaint().measureText(hello.getText().toString()),
                hello.getTextSize(), new int[]{getResources().getColor(R.color.gradientblue),
                getResources().getColor(R.color.gradientpurple), getResources().getColor(R.color.gradientpink)},
                null, Shader.TileMode.CLAMP);
        hello.getPaint().setShader(textShader);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView intro = rootView.findViewById(R.id.intro);
        intro.setText("Your tasks today <>");
        intro.setTextColor(getResources().getColor(R.color.darkgrey));

        // Inflate the layout for this fragment
        return rootView;
    }
}