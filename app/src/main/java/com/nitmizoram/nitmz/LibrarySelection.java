package com.nitmizoram.nitmz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LibrarySelection extends AppCompatActivity {


    private AutoCompleteTextView autoCompleteTextView_course;
    private AutoCompleteTextView autoCompleteTextView_branch;
    private AutoCompleteTextView autoCompleteTextView_semester;


    private final String[] Course ={"Bachelor of Technology","Master of Technology","Doctor of Philosophy"};

    private final String[] branch = {"CS", "EC", "EE", "ME", "CE", "MA", "HS", "PH", "CH",};

    private final String[] semester = {"Semester 1", "Semester 2", "Semester 3", "Semester 4", "Semester 5", "Semester 6", "Semester 7", "Semester 8", "Semester 9", "Semester 10"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_selection);

        Button buttonGet = findViewById(R.id.registerbtn);
        ImageButton backbtn = findViewById(R.id.backbtn);



        autoCompleteTextView_course = findViewById(R.id.autoComplete_course);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(LibrarySelection.this, R.layout.dropdown_item, Course);
        autoCompleteTextView_course.setAdapter(adapter);

        autoCompleteTextView_course.setOnItemClickListener((adapterView, view, i, l) -> {
//                String items = adapterView.getItemAtPosition(i).toString();
        });

        autoCompleteTextView_branch = findViewById(R.id.autoComplete_branch);

        adapter = new ArrayAdapter<>(LibrarySelection.this,R.layout.dropdown_item,branch);
        autoCompleteTextView_branch.setAdapter(adapter);

        autoCompleteTextView_branch.setOnItemClickListener((adapterView, view, i, l) -> {
//                String items = adapterView.getItemAtPosition(i).toString();
        });

        autoCompleteTextView_semester = findViewById(R.id.autoComplete_semester);

        adapter = new ArrayAdapter<>(LibrarySelection.this,R.layout.dropdown_item,semester);
        autoCompleteTextView_semester.setAdapter(adapter);

        autoCompleteTextView_semester.setOnItemClickListener((adapterView, view, i, l) -> {
//                String items = adapterView.getItemAtPosition(i).toString();
        });

        backbtn.setOnClickListener(view -> onBackPressed());

        buttonGet.setOnClickListener(view -> {
            String selectedCourse = autoCompleteTextView_course.getText().toString();
            String selectedSemester = autoCompleteTextView_semester.getText().toString();
            String selectedBranch = autoCompleteTextView_branch.getText().toString();

            if (isValidInput()) {
                Intent intent = new Intent(getApplicationContext(), LibraryShow.class);
                intent.putExtra("course", selectedCourse);
                intent.putExtra("semester", selectedSemester);
                intent.putExtra("branch", selectedBranch);
                startActivity(intent);
            }
        });



    }

    private boolean isValidInput() {

        String selectedCourse = autoCompleteTextView_course.getText().toString();
        String selectedSemester = autoCompleteTextView_semester.getText().toString();
        String selectedBranch = autoCompleteTextView_branch.getText().toString();

        if(selectedCourse.equals("Select Your Course")){
            Toast.makeText(getApplicationContext(),"Please Select Your Course",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(selectedBranch.equals("Select Your Branch")){
            Toast.makeText(getApplicationContext(),"Please Select Your Branch",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(selectedSemester.equals("Select Your Semester")){
            Toast.makeText(getApplicationContext(),"Please Select Your Semester",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}