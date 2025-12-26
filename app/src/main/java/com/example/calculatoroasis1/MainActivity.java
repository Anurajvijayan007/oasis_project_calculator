package com.example.calculatoroasis1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editTextText;
    private String currentInput = "";
    private boolean isResultDisplayed = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextText = findViewById(R.id.editTextText);
        editTextText.setShowSoftInputOnFocus(false);

        int[] layoutIds = {
                R.id.scientific_row,
                R.id.top_operators_row,
                R.id.number_row_1,
                R.id.number_row_2,
                R.id.number_row_3,
                R.id.bottom_row
        };

        for (int layoutId : layoutIds) {
            View layout = findViewById(layoutId);
            if (layout instanceof LinearLayout) {
                LinearLayout linearLayout = (LinearLayout) layout;
                for (int i = 0; i < linearLayout.getChildCount(); i++) {
                    View child = linearLayout.getChildAt(i);
                    if (child instanceof Button) {
                        child.setOnClickListener(this);
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        String buttonText = button.getText().toString();
        int viewId = v.getId();

        if (isResultDisplayed && isNumericOrDot(buttonText)) {
            currentInput = "";
            isResultDisplayed = false;
        }

        if (isNumericOrDot(buttonText)) {
            currentInput += buttonText;
            editTextText.setText(currentInput);
            isResultDisplayed = false;

        } else if (viewId == R.id.button_c) {
            currentInput = "";
            editTextText.setText("");
            isResultDisplayed = false;

        } else if (viewId == R.id.button22) {
            if (currentInput.length() > 0) {
                currentInput = currentInput.trim();
                if (currentInput.endsWith(" ") && currentInput.length() > 2) {
                    currentInput = currentInput.substring(0, currentInput.lastIndexOf(' '));
                } else if (!currentInput.isEmpty()){
                    currentInput = currentInput.substring(0, currentInput.length() - 1);
                }
                editTextText.setText(currentInput);
            }
            isResultDisplayed = false;

        } else if (isBinaryOperator(buttonText) || buttonText.equals("^")) {
            if (!currentInput.isEmpty() && !isOperator(currentInput.charAt(currentInput.trim().length() - 1))) {
                currentInput += " " + buttonText + " ";
                editTextText.setText(currentInput);
                isResultDisplayed = false;
            } else if (buttonText.equals("-") && currentInput.isEmpty()) {
                currentInput += "-";
                editTextText.setText(currentInput);
            }

        } else if (viewId == R.id.button21) {
            try {
                String finalResult = evaluateExpression(currentInput);
                editTextText.setText(finalResult);
                currentInput = finalResult;
                isResultDisplayed = true;
            } catch (Exception e) {
                editTextText.setText("Error");
                currentInput = "";
                isResultDisplayed = true;
                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }

        } else if (viewId == R.id.button_sqrt) {
            try {
                double val = Double.parseDouble(currentInput);
                if (val < 0) throw new ArithmeticException("Invalid input for square root");
                double result = Math.sqrt(val);
                currentInput = formatResult(result);
                editTextText.setText(currentInput);
                isResultDisplayed = true;
            } catch (Exception e) {
                handleScientificError(e);
            }

        } else if (viewId == R.id.button2) {
            try {
                double val = Double.parseDouble(currentInput);
                double result = val / 100.0;
                currentInput = formatResult(result);
                editTextText.setText(currentInput);
                isResultDisplayed = true;
            } catch (Exception e) {
                handleScientificError(e);
            }

        } else if (viewId == R.id.button_sin) {
            try {
                double val = Double.parseDouble(currentInput);
                double result = Math.sin(Math.toRadians(val));
                currentInput = formatResult(result);
                editTextText.setText(currentInput);
                isResultDisplayed = true;
            } catch (Exception e) {
                handleScientificError(e);
            }

        } else if (viewId == R.id.button_cos) {
            try {
                double val = Double.parseDouble(currentInput);
                double result = Math.cos(Math.toRadians(val));
                currentInput = formatResult(result);
                editTextText.setText(currentInput);
                isResultDisplayed = true;
            } catch (Exception e) {
                handleScientificError(e);
            }

        } else if (viewId == R.id.button_tan) {
            try {
                double val = Double.parseDouble(currentInput);
                double result = Math.tan(Math.toRadians(val));
                currentInput = formatResult(result);
                editTextText.setText(currentInput);
                isResultDisplayed = true;
            } catch (Exception e) {
                handleScientificError(e);
            }

        } else if (viewId == R.id.button_log) {
            try {
                double val = Double.parseDouble(currentInput);
                if (val <= 0) throw new ArithmeticException("Invalid input for log");
                double result = Math.log10(val);
                currentInput = formatResult(result);
                editTextText.setText(currentInput);
                isResultDisplayed = true;
            } catch (Exception e) {
                handleScientificError(e);
            }
        }
    }

    private void handleScientificError(Exception e) {
        editTextText.setText("Error");
        currentInput = "";
        isResultDisplayed = true;
        String message = e.getMessage() != null ? e.getMessage() : "Invalid input";
        Toast.makeText(this, "Math Error: " + message, Toast.LENGTH_SHORT).show();
    }

    private boolean isNumericOrDot(String text) {
        return text.matches("[0-9]|00|\\.");
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }

    private boolean isBinaryOperator(String text) {
        return text.equals("+") || text.equals("-") || text.equals("*") || text.equals("/");
    }

    private String formatResult(double result) {
        if (result == (long) result) {
            return String.format("%d", (long) result);
        } else {
            return String.valueOf(result);
        }
    }

    private String evaluateExpression(String expression) throws ArithmeticException {
        if (expression.isEmpty()) return "";

        Pattern pattern = Pattern.compile("(-?\\d+(\\.\\d+)?|\\.\\d+|[+\\-*/^])");
        Matcher matcher = pattern.matcher(expression.trim());

        Stack<Double> values = new Stack<>();
        Stack<String> ops = new Stack<>();

        while (matcher.find()) {
            String token = matcher.group();

            if (token.matches("^-?\\d+(\\.\\d+)?|\\.\\d+$")) {
                values.push(Double.parseDouble(token));

            } else if (isBinaryOperator(token) || token.equals("^")) {
                while (!ops.empty() && hasPrecedence(token, ops.peek())) {
                    if (values.size() < 2) throw new ArithmeticException("Invalid expression format.");
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                }
                ops.push(token);
            }
        }

        while (!ops.empty()) {
            if (values.size() < 2) throw new ArithmeticException("Invalid expression format.");
            values.push(applyOp(ops.pop(), values.pop(), values.pop()));
        }

        if (values.empty()) return "";

        return formatResult(values.pop());
    }

    private boolean hasPrecedence(String op1, String op2) {
        if (op2.equals("^")) return true;

        if (op2.equals("*") || op2.equals("/")) {
            return op1.equals("*") || op1.equals("/") || op1.equals("+") || op1.equals("-") || op1.equals("^");
        }

        if (op2.equals("+") || op2.equals("-")) {
            return op1.equals("+") || op1.equals("-");
        }
        return false;
    }

    private double applyOp(String op, double b, double a) throws ArithmeticException {
        switch (op) {
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            case "/":
                if (b == 0) throw new ArithmeticException("Division by zero");
                return a / b;
            case "^":
                if (a == 0 && b < 0) throw new ArithmeticException("0 raised to a negative power is undefined");
                return Math.pow(a, b);
        }
        return 0;
    }
}