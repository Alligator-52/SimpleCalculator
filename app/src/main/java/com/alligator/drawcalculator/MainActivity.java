package com.alligator.drawcalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class MainActivity extends AppCompatActivity
{
    // <editor-fold desc="Public Declarations">
    public String ExpressionToShow;
    public String Expression;
    public double Result;
    private TextView _currentExpressionText;
    private TextView _currentOutputText;
    // </editor-fold>

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        _currentExpressionText = findViewById(R.id.currentCalc);
        _currentOutputText = findViewById(R.id.solutionView);
        ExpressionToShow = "";
        Expression = "";
        Result = 0;
        _currentOutputText.setText("0");
        _currentExpressionText.setText("0");
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    public void GetValue(View v)
    {
        if (!(v instanceof Button)) return;
        Button currButton = (Button) v;
        String currentText = currButton.getText().toString();
        if(currentText.equals("AC"))
        {
            Expression = "";
            ExpressionToShow = "";
            _currentExpressionText.setText("0");
            _currentOutputText.setText("0");
            return;
        }
        if (currentText.equals("C"))
        {
            StringBuilder sb = new StringBuilder(_currentExpressionText.getText());
            if(sb.length() <= 0)
            {
                _currentExpressionText.setText("0");
                return;
            }

            sb.deleteCharAt(sb.length() - 1);

            _currentExpressionText.setText(sb.toString());
            Expression = sb.toString();

            return;
        }
        if(currentText.equals("="))
        {
            if(!Expression.isEmpty())
            {
                List<String> rpn = ShuntingYard.convertToRPN(Expression);
                Result = ShuntingYard.evaluateRPN(rpn);
                String result = String.valueOf(Result);
                _currentOutputText.setText(result);
                _currentExpressionText.setText(result);
            }
            return;
        }
        ExpressionToShow += currentText;
        if(currentText.equals("x")) currentText = "*";
        Expression += currentText;
        _currentExpressionText.setText(ExpressionToShow);
    }
}