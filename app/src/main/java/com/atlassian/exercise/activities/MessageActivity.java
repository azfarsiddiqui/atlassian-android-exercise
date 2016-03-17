package com.atlassian.exercise.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.atlassian.exercise.R;
import com.atlassian.exercise.entities.Message;
import com.atlassian.exercise.listeners.MessageSegmentationListener;

public class MessageActivity extends BaseActivity {

    EditText mTextViewMessage;
    TextView mTextViewMessageJson;
    Button mButtonConvert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUI();
        attachUIListeners();
    }

    private void setupUI() {
        setContentView(R.layout.activity_message);

        mTextViewMessage = (EditText) findViewById(R.id.txt_message);
        mTextViewMessageJson = (TextView) findViewById(R.id.txt_message_json);
        mButtonConvert = (Button) findViewById(R.id.btn_convert);
    }

    private void attachUIListeners() {
        mButtonConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleConvertButtonPress();
            }
        });
    }

    private void handleConvertButtonPress() {
        String messageString = mTextViewMessage.getText().toString();

        if (messageString.length() == 0) {
            Toast.makeText(this, R.string.prompt_enter_message, Toast.LENGTH_SHORT).show();
            return;
        }

        Message message = new Message(messageString);
        message.segmentize(new MessageSegmentationListener() {
            @Override
            public void onMessageSegmentationCompleted(Message message) {
                mTextViewMessageJson.setText(message.toJson());
            }
        });
    }
}
