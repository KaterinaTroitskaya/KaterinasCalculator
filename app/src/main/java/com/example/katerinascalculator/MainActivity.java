package com.example.katerinascalculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.button.MaterialButton;


public class MainActivity extends AppCompatActivity {
    public static final String KEY_DISPLAY = "KEY_DISPLAY";

    private static final String AppTheme = "Theme.Homework4";
    private static final int MY_LIGHT_STYLE = 0;
    private static final int MY_DARK_STYLE = 1;
    private static final String NAME_SHARED_PREFERENSE = "SharedPref";
    static String operator = "0";
    private Calculator calc;
    private EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(getAppTheme(R.style.MyDarkStyle));
        setContentView(R.layout.activity_main);

        checkNightModeActivated();

        calc = new Calculator();
        initView();
        initThemeChooser();

        String value = getIntent().getStringExtra(KEY_DISPLAY);
    }
    public void checkNightModeActivated() {
        SharedPreferences sharedPref = getSharedPreferences(NAME_SHARED_PREFERENSE, MODE_PRIVATE);
        if (sharedPref.getInt(AppTheme, 0) == 0) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
    private void initThemeChooser() {
        initRadioButton(findViewById(R.id.rBtnLight), MY_LIGHT_STYLE);
        initRadioButton(findViewById(R.id.radioButtonDarkAction), MY_DARK_STYLE);
    }

    private void initRadioButton(View button, final int codeStyle) {
        button.setOnClickListener(v -> {
            setAppTheme(codeStyle);
            if (codeStyle == MY_LIGHT_STYLE) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            recreate();
        });
    }

    private int getAppTheme(int codeStyle) {
        return codeStyleToStyleId(getCodeStyle(codeStyle));

    }

    private int getCodeStyle(int codeStyle) {
        SharedPreferences sharedPref = getSharedPreferences(NAME_SHARED_PREFERENSE, MODE_PRIVATE);
        return sharedPref.getInt(AppTheme, codeStyle);
    }

    private void setAppTheme(int codeStyle) {
        SharedPreferences sharedPref = getSharedPreferences(NAME_SHARED_PREFERENSE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(AppTheme, codeStyle);
        editor.apply();
    }

    private int codeStyleToStyleId(int codeStyle) {
        if (codeStyle == MY_LIGHT_STYLE) {
            setTheme(R.style.MyLightStyle);
            return R.style.MyLightStyle;
        }
        setTheme(R.style.MyDarkStyle);
        return R.style.MyDarkStyle;
    }


    private void initView() {
        text = findViewById(R.id.key_result);

        initButtonsClickListener();
    }

    private void initButtonsClickListener() {
        Button button1 = findViewById(R.id.key_1);
        Button button2 = findViewById(R.id.key_2);
        Button button3 = findViewById(R.id.key_3);
        Button button4 = findViewById(R.id.key_4);
        Button button5 = findViewById(R.id.key_5);
        Button button6 = findViewById(R.id.key_6);
        Button button7 = findViewById(R.id.key_7);
        Button button8 = findViewById(R.id.key_8);
        Button button9 = findViewById(R.id.key_9);
        Button button0 = findViewById(R.id.key_0);
        Button button_plus = findViewById(R.id.key_addition);
        Button button_min = findViewById(R.id.key_subtraction);
        Button button_mult = findViewById(R.id.key_composition);
        Button button_div = findViewById(R.id.key_division);
        Button button_clear = findViewById(R.id.key_clear);
        Button button_equal = findViewById(R.id.key_equally);
        Button button_dot = findViewById(R.id.key_dot);

        button1.setOnClickListener(numberButtonsClickListener);
        button2.setOnClickListener(numberButtonsClickListener);
        button3.setOnClickListener(numberButtonsClickListener);
        button4.setOnClickListener(numberButtonsClickListener);
        button5.setOnClickListener(numberButtonsClickListener);
        button6.setOnClickListener(numberButtonsClickListener);
        button7.setOnClickListener(numberButtonsClickListener);
        button8.setOnClickListener(numberButtonsClickListener);
        button9.setOnClickListener(numberButtonsClickListener);
        button0.setOnClickListener(numberButtonsClickListener);
        button_clear.setOnClickListener(numberClearClickListener);
        button_dot.setOnClickListener(returnButtonsClickListener);
        button_plus.setOnClickListener(operationPlusButtonsClickListener);
        button_min.setOnClickListener(operationMinButtonsClickListener);
        button_div.setOnClickListener(operationDivButtonsClickListener);
        button_mult.setOnClickListener(operationMultButtonsClickListener);
        button_equal.setOnClickListener(equalButtonsClickListener);
    }
    public View.OnClickListener numberButtonsClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MaterialButton tv;
            tv = (MaterialButton) v;
            String textFromTV = tv.getText().toString();
            text.append(textFromTV);
        }
    };

    public View.OnClickListener numberClearClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            text.setText("");
            calc.number2 = "";
            calc.number1 = "";
            calc.result = 0;
        }
    };

    public View.OnClickListener returnButtonsClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String str = text.getText().toString().trim();
            if (str.length() == 0) return;
            str = str.substring(0, str.length() - 1);
            text.setText(str);
        }
    };

    public View.OnClickListener operationPlusButtonsClickListener = v -> setOperator("+");

    public View.OnClickListener operationMinButtonsClickListener = v -> setOperator("-");

    public View.OnClickListener operationDivButtonsClickListener = v -> setOperator("/");

    public View.OnClickListener operationMultButtonsClickListener = v -> setOperator("*");

    public View.OnClickListener equalButtonsClickListener = v -> Equals();

    public void setOperator(String _operator) {
        if (calc.number1.equals("")) {
            calc.number1 = text.getText().toString().trim();
        } else {
            calc.number2 = text.getText().toString().trim();
            calc.number1 = String.valueOf(calc.result);
        }
        operator = _operator;
        text.setText("");

    }

    public void Equals() {
        String str = text.getText().toString().trim();
        if (str.length() == 0) return;

        calc.number2 = text.getText().toString().trim();
        if (calc.number1.equals("")) return;


        switch (operator) {
            case "+":
                calc.Sum();
                break;

            case "-":
                calc.Min();
                break;

            case "/":
                calc.Div();

                break;

            case "*":
                calc.Mult();
                break;

            case "%":
                calc.Proc();
                break;

            default:
                calc.result = 0;
        }

        text.setText((int) calc.result);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("calc", calc);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        calc = savedInstanceState.getParcelable("calc");
        text = findViewById(R.id.key_result);
        text.setText(String.valueOf(calc.result));
    }
    }