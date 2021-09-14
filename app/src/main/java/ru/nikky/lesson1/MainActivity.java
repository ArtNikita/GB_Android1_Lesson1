package ru.nikky.lesson1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //region Values declaration
    private ScrollView backgroundScrollView;
    private TextView greetingsTextView;
    private EditText rgbEditText;
    private CheckBox backgroundCheckBox;
    private CheckBox welcomeStringCheckBox;
    private SwitchCompat randomColorSwitch;
    private Button changeColorButton;
    private ToggleButton discoToggleButton;

    private Random random;

    private Button startNextActivityButton;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initValues();

        setupChangeColorButton();
        setupDiscoToggleButton();

        setupStartNextActivityButton();
    }

    private void initValues() {
        backgroundScrollView = findViewById(R.id.background_scroll_view);
        greetingsTextView = findViewById(R.id.greetings_text_view);
        rgbEditText = findViewById(R.id.rgb_edit_text);
        backgroundCheckBox = findViewById(R.id.background_checkbox);
        welcomeStringCheckBox = findViewById(R.id.welcome_string_checkbox);
        randomColorSwitch = findViewById(R.id.random_color_switch);
        changeColorButton = findViewById(R.id.change_color_button);
        discoToggleButton = findViewById(R.id.disco_toggle_button);

        random = new Random();

        startNextActivityButton = findViewById(R.id.start_next_activity_button);
    }

    private void setupChangeColorButton() {
        changeColorButton.setOnClickListener(view -> {
            int colorInt;
            if (!randomColorSwitch.isChecked()) {//use input color
                String colorStr = rgbEditText.getText().toString();
                if (colorStr.length() != 6) {
                    Toast.makeText(this, R.string.less_than_6_chars_toast_alert, Toast.LENGTH_SHORT).show();
                    return;
                }
                colorInt = Color.parseColor("#" + colorStr);
            } else {//use random color
                colorInt = generateRandomColor();
            }

            if (backgroundCheckBox.isChecked()) {
                backgroundScrollView.setBackgroundColor(colorInt);
                getWindow().setStatusBarColor(colorInt);
            }
            if (welcomeStringCheckBox.isChecked()) greetingsTextView.setTextColor(colorInt);
        });
    }

    private void setupDiscoToggleButton() {
        discoToggleButton.setOnCheckedChangeListener((compoundButton, b) -> {
            if (!b) return;

            DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        noDisco();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        startDisco();
                        break;
                }
            };

            new AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setMessage(R.string.epileptic_alert_dialog_message)
                    .setPositiveButton(R.string.yes, dialogClickListener)
                    .setNegativeButton(R.string.no, dialogClickListener).show();
        });
    }

    private void noDisco() {
        Toast.makeText(this, R.string.no_disco_toast, Toast.LENGTH_SHORT).show();
        discoToggleButton.setChecked(false);
    }

    private void startDisco() {
        Toast.makeText(this, R.string.start_disco_toast, Toast.LENGTH_SHORT).show();
        discoToggleButton.setEnabled(false);
        new CountDownTimer(10000, 250) {
            @Override
            public void onTick(long l) {
                int backgroundColor = generateRandomColor();
                backgroundScrollView.setBackgroundColor(backgroundColor);
                getWindow().setStatusBarColor(backgroundColor);
                greetingsTextView.setTextColor(generateRandomColor());
            }

            @Override
            public void onFinish() {
                discoToggleButton.setChecked(false);
                discoToggleButton.setEnabled(true);
            }
        }.start();
    }

    private int generateRandomColor(){
        return Color.argb(
                255,
                random.nextInt(256),
                random.nextInt(256),
                random.nextInt(256));
    }

    private void setupStartNextActivityButton() {
        startNextActivityButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, SecondTaskActivity.class);
            startActivity(intent);
        });
    }
}