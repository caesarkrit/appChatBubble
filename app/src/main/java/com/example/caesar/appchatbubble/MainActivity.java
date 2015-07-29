package com.example.caesar.appchatbubble;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class MainActivity extends Activity {
    ListView listview;
    EditText chat_text;


    Button SEND;
    boolean position = false;
    ChatAdapter adapter;
    Context ctx = this;



    @Override
    protected void onCreate(Bundle saveInstanceState){



        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main);

        mSocket.on("new message", onNewMessage);
        mSocket.connect();


        listview = (ListView) findViewById(R.id.chat_list_view);
        chat_text = (EditText) findViewById(R.id.chatTxt);
        SEND = (Button) findViewById(R.id.send_button);
        adapter = new ChatAdapter(ctx,R.layout.activity_chat);
        listview.setAdapter(adapter);
        listview.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listview.setSelection(adapter.getCount() - 1);
            }
        });


        SEND.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mymessage = chat_text.getText().toString().trim();
                mSocket.emit("new message", mymessage);
                adapter.add(new DataProvider(position, mymessage,0));
                chat_text.setText(" ");
            }
        });



    }

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("https://death-sarodh.c9.io/");
        }
        catch (URISyntaxException e){}
    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    //    String username;
                    String message; // = (String) args[0];
                    try {
                        message = data.getString("message");
                        adapter.add(new DataProvider(position, message,1));//.toString();
                        //message = data.getString("message").toString();
                    } catch (JSONException e) {
                        return;
                    }

                    // add the message to view
                    TextView textView = (TextView) findViewById(R.id.singleMessage);
                   textView.setText(textView.getText() + "\n" + message);

                }
            });
        }
    };

}

























