package com.example.edith.fragments;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.edith.R;
import com.example.edith.activities.MainActivity;
//import com.example.edith.activities.TouchHelper;
import com.example.edith.adapters.TaskAdapter;
import com.example.edith.models.Task;
import com.example.edith.models.ToDoModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.testng.reporters.jq.Main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link todoList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class todoList extends Fragment {

    private RecyclerView recyclerView;
    private FirebaseFirestore firestore;
    private TaskAdapter adapter;
    private List<ToDoModel> list;
    private Query query;
    private ListenerRegistration listenerRegistration;

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
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_todo_list, container, false);

        // Find the RecyclerView in the layout
        recyclerView = rootView.findViewById(R.id.taskRV);
        // Find Data Source
        firestore = FirebaseFirestore.getInstance();
        list = new ArrayList<>();
        adapter = new TaskAdapter((FragmentActivity) getActivity(), list);
        recyclerView.setAdapter(adapter);
        showData();

        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        TextView hello = rootView.findViewById(R.id.Hello);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());


        String userName = account.getDisplayName();
        String[] parts = userName.split("\\s+");
        String firstname = parts[0];
        hello.setText("Hello " + firstname + "!");
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
  
    public TaskAdapter getAdapter(){
        return adapter;
    }

    public void showData() {
        // Query to get the data from the firestore
        firestore.collection("tasks").orderBy("orderDate", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for (DocumentChange documentChange : value.getDocumentChanges()) {
                            if (documentChange.getType() == DocumentChange.Type.ADDED) {
                                String id = documentChange.getDocument().getId();
                                ToDoModel toDoModel = documentChange.getDocument().toObject(ToDoModel.class).withId(id);

                                list.add(toDoModel);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }
}