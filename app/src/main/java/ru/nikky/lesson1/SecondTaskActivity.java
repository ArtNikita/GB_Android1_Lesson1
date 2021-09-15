package ru.nikky.lesson1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class SecondTaskActivity extends AppCompatActivity {

    //region Values declaration
    private EditText nameEditText;
    private EditText phoneEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private CalendarView calendarView;
    private Button submitButton;
    private TextView resultTextView;

    private String currentDate;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_task);

        initValues();

        setupCalendarView();
        setupSubmitButton();
    }

    private void initValues() {
        nameEditText = findViewById(R.id.name_edit_text);
        phoneEditText = findViewById(R.id.phone_edit_text);
        emailEditText = findViewById(R.id.email_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        calendarView = findViewById(R.id.calendar_view);
        submitButton = findViewById(R.id.submit_button);
        resultTextView = findViewById(R.id.result_text_view);
    }

    private void setupCalendarView() {
        calendarView.setOnDateChangeListener((calendarView, year, month, day) ->
                currentDate = String.format(Locale.ENGLISH,
                        "%d/%d/%d", year, month + 1, day));
    }

    private void setupSubmitButton() {
        submitButton.setOnClickListener(view -> {
            String resultText = String.format(
                    "Name: %s\nPhone: %s\nEmail: %s\nPassword: %s\nDate: %s",
                    nameEditText.getText().toString(),
                    phoneEditText.getText().toString(),
                    emailEditText.getText().toString(),
                    passwordEditText.getText().toString(),
                    currentDate == null ? "" : currentDate);
            resultTextView.setText(resultText);
        });
    }
}