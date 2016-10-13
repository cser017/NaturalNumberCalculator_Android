package com.example.eric.new_calc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import java.math.BigInteger;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private enum State {
        SAW_CLEAR, SAW_ENTER, SAW_OTHER_OP, SAW_DIGIT, SAW_SWAP
    }

    State currentState = State.SAW_CLEAR;

    private BigInteger top = BigInteger.valueOf(0);
    private BigInteger bottom = BigInteger.valueOf(0);

    private static final BigInteger TWO = BigInteger.valueOf(2),
            INT_LIMIT = BigInteger.valueOf(Integer.MAX_VALUE);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button buttonClear = (Button) findViewById(R.id.b_clear);
        final Button buttonSwap = (Button) findViewById(R.id.b_swap);
        final Button bEnter = (Button) findViewById(R.id.b_enter);
        final Button buttonAdd = (Button) findViewById(R.id.b_add);
        final Button bSubtract = (Button) findViewById(R.id.b_sub);
        final Button bMultiply = (Button) findViewById(R.id.b_multy);
        final Button bDivide = (Button) findViewById(R.id.b_div);
        final Button bPower = (Button) findViewById(R.id.b_pow);
        final Button bRoot = (Button) findViewById(R.id.b_root);

        final Button[] bDigits = new Button[]{
                (Button) findViewById(R.id.b0),
                (Button) findViewById(R.id.b1),
                (Button) findViewById(R.id.b2),
                (Button) findViewById(R.id.b3),
                (Button) findViewById(R.id.b4),
                (Button) findViewById(R.id.b5),
                (Button) findViewById(R.id.b6),
                (Button) findViewById(R.id.b7),
                (Button) findViewById(R.id.b8),
                (Button) findViewById(R.id.b9)
        };

        if(buttonClear!=null)
        buttonClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                processClearEvent();
                currentState = State.SAW_CLEAR;
            }
        });

        if(buttonSwap!=null)
        buttonSwap.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                processSwapEvent();
                currentState = State.SAW_SWAP;
            }
        });

        if(bEnter!=null)
        bEnter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                processEnterEvent();
                currentState = State.SAW_ENTER;
            }
        });

        if(buttonAdd!=null) {
            buttonAdd.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    processAddEvent();
                    currentState = State.SAW_OTHER_OP;
                }
            });
        }

        if(bSubtract!=null){
            bSubtract.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    processSubtractEvent();
                    currentState = State.SAW_OTHER_OP;
                }
            });
        }

        if(bMultiply!=null){
            bMultiply.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    processMultiplyEvent();
                    currentState = State.SAW_OTHER_OP;
                }
            });
        }

        if(bDivide!=null){
            bDivide.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    processDivideEvent();
                    currentState = State.SAW_OTHER_OP;
                }
            });
        }

        if(bPower!=null){
            bPower.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    processPowerEvent();
                    currentState = State.SAW_OTHER_OP;
                }
            });
        }

        if(bRoot!=null){
            bRoot.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    processRootEvent();
                    currentState = State.SAW_OTHER_OP;
                }
            });
        }

        for(int i=0; i<bDigits.length && bDigits[i]!=null; i++){
            final int temp = i;
            bDigits[temp].setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (currentState){
                        case SAW_ENTER:
                            processClearEvent();
                            break;
                        case SAW_SWAP:
                            processClearEvent();
                            break;
                        case SAW_OTHER_OP:
                            processEnterEvent();
                            processClearEvent();
                            break;
                        default:
                            break;
                    }
                    processAddNewDigitEvent(temp);
                    currentState = State.SAW_DIGIT;

                }
            });
        }

        updateViewToMatchModel(top,bottom);

    }


    //update view
    private void updateViewToMatchModel(BigInteger _top, BigInteger _bottom){

        final TextView tTop = (TextView) findViewById(R.id.top_text);
        final TextView tBottom = (TextView) findViewById(R.id.bottom_text);

        if(tTop!=null && tBottom!=null) {
            tTop.setText(String.format(Locale.US, "%d", _top));
            tBottom.setText(String.format(Locale.US, "%d", _bottom));
        }

        updatePowerAllowed(bottom.compareTo(INT_LIMIT) <= 0);
        updateRootAllowed((bottom.compareTo(TWO) >= 0)
                && (bottom.compareTo(INT_LIMIT)) <= 0);
        updateSubtractAllowed(top.compareTo(bottom) >= 0);
        updateDivideAllowed(!bottom.equals(BigInteger.ZERO));
    }

    public void updateSubtractAllowed(boolean allowed) {
        final Button bSubtract = (Button) findViewById(R.id.b_sub);
        if(bSubtract!=null)
        bSubtract.setEnabled(allowed);
    }

    public void updateDivideAllowed(boolean allowed) {
        final Button bDivide = (Button) findViewById(R.id.b_div);
        if(bDivide!=null)
        bDivide.setEnabled(allowed);
    }

    public void updatePowerAllowed(boolean allowed) {
        final Button bPower = (Button) findViewById(R.id.b_pow);
        if(bPower!=null)
        bPower.setEnabled(allowed);
    }

    public void updateRootAllowed(boolean allowed) {
        final Button bRoot = (Button) findViewById(R.id.b_root);
        if(bRoot!=null)
        bRoot.setEnabled(allowed);
    }
    //end of update view

    //calculation
    void processClearEvent(){
        bottom = BigInteger.valueOf(0);
        updateViewToMatchModel(top, bottom);
    }

    void processSwapEvent(){
        BigInteger temp = top;
        top = bottom;
        bottom = temp;
        updateViewToMatchModel(top, bottom);
    }

    void processEnterEvent(){
        top = bottom;
        bottom = BigInteger.valueOf(0);
        updateViewToMatchModel(top,bottom);

    }

    void processAddEvent(){
        BigInteger res = bottom.add(top);
        top = BigInteger.valueOf(0);
        bottom = res;
        updateViewToMatchModel(top,bottom);
    }

    void processSubtractEvent(){
        BigInteger res = top.subtract(bottom);
        top = BigInteger.valueOf(0);
        bottom = res;
        updateViewToMatchModel(top,bottom);

    }

    void processMultiplyEvent(){
        BigInteger res = top.multiply(bottom);
        top = BigInteger.valueOf(0);
        bottom = res;
        updateViewToMatchModel(top,bottom);

    }

    void processDivideEvent(){
        BigInteger[] res = top.divideAndRemainder(bottom);
        top = res[1];
        bottom = res[0];
        updateViewToMatchModel(top,bottom);
    }

    void processPowerEvent(){
        BigInteger res = top.pow(bottom.intValue());
        top = BigInteger.valueOf(0);
        bottom = res;
        updateViewToMatchModel(top,bottom);
    }

    void processRootEvent(){
        updateViewToMatchModel(top,bottom);
    }

    void processAddNewDigitEvent(int digit){
        bottom = bottom.multiply(BigInteger.TEN).add(BigInteger.valueOf(digit));
        updateViewToMatchModel(top,bottom);
    }
    //end of calculation

}
