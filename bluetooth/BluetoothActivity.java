package com.example.hugo.guitarledgend.bluetooth;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import android.content.IntentFilter;
import android.os.Bundle;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hugo.guitarledgend.MyApp;
import com.example.hugo.guitarledgend.R;

public class BluetoothActivity extends AppCompatActivity {

    private TextView mTextView;
    private TextView mTextViewState;
    private EditText mEditTextCorde;
    private EditText mEditTextFrette;
    private EditText mEditTextDoigt;
    private Button mButtonSearch;
    private BluetoothModule myDevice;


    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (action.equals(BluetoothDevice.ACTION_ACL_CONNECTED)) {
                // TODO : action when connection established
            }
            else if (action.equals(BluetoothDevice.ACTION_ACL_DISCONNECTED)) {
                // TODO : action when connection lost
            }
            else if (action.equals(BluetoothModule.ACTION_BATTERY_LOW)) {
                // TODO : action when battery low
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.tvDevices);
        mTextViewState = (TextView) findViewById(R.id.textViewState);
        mEditTextCorde = (EditText) findViewById(R.id.editTextCorde);
        mEditTextFrette = (EditText) findViewById(R.id.editTextFrette);
        mEditTextDoigt = (EditText) findViewById(R.id.editTextDoigt);
        mButtonSearch = (Button) findViewById(R.id.button_id);

        IntentFilter mFilter = new IntentFilter(BluetoothDevice.ACTION_ACL_CONNECTED);
        registerReceiver(mReceiver, mFilter);
        mFilter = new IntentFilter(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        registerReceiver(mReceiver, mFilter);
        mFilter = new IntentFilter(BluetoothModule.ACTION_BATTERY_LOW);
        registerReceiver(mReceiver, mFilter);

        // Initialize and connect the BluetoothModule if none exists
        myDevice = ((MyApp)getApplicationContext()).getDevice();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

        unregisterReceiver(mReceiver);
    }

 /*   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

    public void onClick_Search(View v) {
        myDevice.search();
    }

    public void onClick_Connect(View v) {
        /*
        // Irrelevant
        try {
            myDevice.connect();
            Toast.makeText(BluetoothActivity.this,"Connecté à l'Arduino",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            DialogFragment newFragment = new ErrorConnectFragment();
            newFragment.show(getSupportFragmentManager(), "delete");
        }
        */
    }

    public void onClick_Disconnect(View v) {
        myDevice.disconnect();
    }

    public void onClick_Play(View v) {
        try {
            myDevice.send(Integer.parseInt(mEditTextCorde.getText().toString()), Integer.parseInt(mEditTextFrette.getText().toString()), Integer.parseInt(mEditTextDoigt.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClick_Test(View v) {
        try {
            myDevice.send(0,0,1);
            Toast.makeText(BluetoothActivity.this,"L'affichage des LEDs fonctionne",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            DialogFragment newFragment = new ErrorLedFragment();
            newFragment.show(getSupportFragmentManager(), "delete");;
        }
    }
}