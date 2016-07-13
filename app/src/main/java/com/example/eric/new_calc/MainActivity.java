package com.example.eric.new_calc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tTop = (TextView) findViewById(R.id.top_text);
        final TextView tBottom = (TextView) findViewById(R.id.bottom_text);

        final Button bClear = (Button) findViewById(R.id.b_clear);
        final Button bSwap = (Button) findViewById(R.id.b_swap);
        final Button bEnter = (Button) findViewById(R.id.b_enter);
        final Button bAdd = (Button) findViewById(R.id.b_add);
        final Button bSubtract = (Button) findViewById(R.id.b_sub);
        final Button bMultiply = (Button) findViewById(R.id.b_multy);
        final Button bDivide = (Button) findViewById(R.id.b_div);
        final Button bPower = (Button) findViewById(R.id.b_pow);
        final Button bRoot = (Button) findViewById(R.id.b_root);

        final Button[] bDigits = new Button[]{
                (Button) findViewById(R.id.b1),
                (Button) findViewById(R.id.b2),
                (Button) findViewById(R.id.b3),
                (Button) findViewById(R.id.b4),
                (Button) findViewById(R.id.b5),
                (Button) findViewById(R.id.b6),
                (Button) findViewById(R.id.b7),
                (Button) findViewById(R.id.b8),
                (Button) findViewById(R.id.b9),
                (Button) findViewById(R.id.b0)
        };


        if(bClear!=null)
        bClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tTop.setText("clear pressed!");
            }
        });

        if(bSwap!=null)
        bSwap.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tTop.setText("swap pressed!");
            }
        });

        if(bEnter!=null)
        bEnter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tTop.setText("enter pressed!");
            }
        });

        if(bAdd!=null) {
            bAdd.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    tTop.setText("add pressed!");
                }
            });
        }

        for(int i=0; i<bDigits.length && bDigits[i]!=null; i++){
            final int temp = i;
            bDigits[i].setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    tTop.setText("button pressed!");
                }
            });
        }

        //set on click listener

    }
}
