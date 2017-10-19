package com.example.myfirstapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int MODE_PLUS = 1;
    private static final int MODE_MINUS = 2;
    private static final int MODE_DIVISION = 3;
    private static final int MODE_MULTIPLY = 4;

    private static final int MODE_RESULT = 5;

    public String first_register = "";
    public String second_register = "";
    public String result = "";

    public int mode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void makeResult(View view) {

        TextView editText = (TextView) findViewById(R.id.editText);

        this.calculate();

        this.mode = MODE_RESULT;

        editText.setText(this.result);
    }

    public void button(View view) {
        if (this.mode == 5) {
            this.reset();
        }

        Button b = (Button)view;
        String buttonText = b.getText().toString();

        this.second_register += buttonText;

        this.buildExpression(false);
    }

    public void dotButton(View view) {
        if ("".equals(this.second_register)) {
            this.second_register += "0";
        }

        if (this.second_register.charAt(this.second_register.length() - 1) != '.') {
            this.second_register += ".";
            this.buildExpression(true);
        }
    }

    public void operations(View view) {

        if (this.mode > 0  || this.mode == MODE_RESULT) {
            this.calculate();
        } else {
            this.first_register = this.second_register;
        }

        this.second_register = "";

        switch (view.getId()) {
            case R.id.button_plus:
                this.mode = MODE_PLUS;
                break;
            case R.id.button_minus:
                this.mode = MODE_MINUS;
                break;
            case R.id.button_multiply:
                this.mode = MODE_MULTIPLY;
                break;
            case R.id.button_division:
                this.mode = MODE_DIVISION;
                break;
        }

        this.buildExpression(true);
    }

    protected void buildExpression(boolean trim_nulls) {

        String operator = "";

        switch (this.mode) {
            case MODE_PLUS:
                operator = "+";
                break;
            case MODE_MINUS:
                operator = "-";
                break;
            case MODE_DIVISION:
                operator = "/";
                break;
            case MODE_MULTIPLY:
                operator = "x";
                break;
        }

        TextView editText = (TextView) findViewById(R.id.editText);
        if (this.mode > 0) {
            editText.setText(this.first_register + operator + this.second_register);
        } else {
            editText.setText(this.second_register);
        }
    }

    public void clear(View view) {
        TextView editText = (TextView) findViewById(R.id.editText);
        editText.setText("");

        this.reset();
    }

    protected void reset() {
        this.result = "";
        this.mode = 0;
        this.first_register = "";
        this.second_register = "";
    }

    protected void calculate() {
        Float result_reg = 0f;
        Float first_reg = 0f;
        Float second_reg = 0f;

        if (!this.first_register.equals("")) {
            first_reg = Float.parseFloat(this.first_register);
        }

        if (!this.second_register.equals("")) {
            second_reg = Float.parseFloat(this.second_register);
        }

        switch (this.mode) {
            case MODE_PLUS:
                result_reg = first_reg += second_reg;
                break;
            case MODE_MINUS:
                result_reg = first_reg -= second_reg;
                break;
            case MODE_DIVISION:
                result_reg = first_reg /= second_reg;
                break;
            case MODE_MULTIPLY:
                result_reg = first_reg *= second_reg;
                break;
        }

        this.first_register = String.valueOf(first_reg);
        if (first_reg%1 == 0) {
            this.first_register = this.first_register.replace (".0", "");
        }

        this.second_register = String.valueOf(second_reg);
        if (second_reg%1 == 0) {
            this.second_register = this.second_register.replace (".0", "");
        }

        this.result = String.valueOf(result_reg);
        if (result_reg%1 == 0) {
            this.result = this.result.replace (".0", "");
        }
    }
}