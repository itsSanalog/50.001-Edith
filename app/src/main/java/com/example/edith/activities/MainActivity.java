package com.example.edith.activities;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.view.MenuItem;

import com.example.edith.R;
import com.example.edith.adapters.TaskAdapter;
import com.example.edith.data.DatabaseOperations;
import com.example.edith.data.FirebaseOperations;
import com.example.edith.fragments.AddTaskBottomFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public DrawerLayout drawerLayout;
    public RecyclerView recyclerView;
    public TaskAdapter adapter;
    public List<Task> taskList;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // < initialisation -- create >
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            this.finish();
        }



        // < finding elements >
        // TextView for intro
        TextView hello = findViewById(R.id.Hello);
        // ToolBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        // RecyclerView
        DatabaseOperations db = new FirebaseOperations();

        recyclerView = findViewById(R.id.taskRV);
        adapter = new TaskAdapter(MainActivity.this, db);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fab);
        setSupportActionBar(toolbar);


        // drawer layout instance to toggle the menu icon back open
        // drawer and back button to close drawer
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.nav_open, R.string.nav_close);

        // pass the open and close toggle for the drawer layout listener to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set the navigation item click listener
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Set the navigation icon to the right side of the Toolbar
        toolbar.setNavigationIcon(R.drawable.navigation_icon);
        toolbar.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        toolbar.setTextDirection(View.TEXT_DIRECTION_LTR);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        // Set the intro for hello textview
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(MainActivity.this);
        String userName = account.getDisplayName();
        String[] parts = userName.split("\\s+");
        String firstName = parts[0];
        hello.setText("Hello " + firstName + "!");
        hello.setTextColor(R.color.white);
        Shader textShader = new LinearGradient(0, 0, hello.getPaint()
                .measureText(hello.getText().toString()), hello.getTextSize(),
                new int[]{R.color.gradientblue, R.color.gradientpurple, R.color.gradientpink},
                null, Shader.TileMode.CLAMP);
        hello.getPaint().setShader(textShader);

        // Set the floating action button on click listener
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTaskBottomFragment bottomFragment = new AddTaskBottomFragment();
                bottomFragment.show(getSupportFragmentManager(),bottomFragment.getTag());
            }
        });

    }

    // to Override the onOptionsItemSelected()
    // function to implement
    // the item click listener callback
    // to open and close the navigation
    // drawer when the icon is clicked
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){
        // Handle navigation view item selected here
        int id = item.getItemId();

        if (id == R.id.signOut) {
            logout(null);
        }
        // Close the navigation drawer when an item is selected
        drawerLayout.closeDrawer(GravityCompat.END);
        return true;
    }

    public void logout(View view){

        GoogleSignIn.getClient(this,
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build())
                .signOut().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(MainActivity.this, "Signout Successful.",
                                Toast.LENGTH_SHORT).show();
                        // Finish current activity to prevent going back.
                        //finish();     // OPTIONAL
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Signout Failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}