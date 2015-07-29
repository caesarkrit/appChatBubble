package com.example.caesar.appchatbubble;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.widget.LinearLayout.*;

public class ChatAdapter extends ArrayAdapter<DataProvider>{

    private List<DataProvider> chat_list = new ArrayList<DataProvider>();

    private TextView CHAT_TXT;

    int numbers;

    Context CTX;


        public ChatAdapter(Context context, int resource) {

            super(context, resource);
            CTX = context;
        }

        @Override
        public void add(DataProvider object){
            chat_list.add(object);
            super.add(object);

        }

    @Override
    public int getCount() {
        return chat_list.size();
    }

    @Override
    public DataProvider getItem(int position) {

        return chat_list.get(position);
    }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){


                LayoutInflater inflator = (LayoutInflater) CTX.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflator.inflate(R.layout.activity_chat,parent,false);
            }
        CHAT_TXT = (TextView) convertView.findViewById(R.id.singleMessage);

        String Message;
        boolean POSITION;
        DataProvider provider = getItem(position);

            //DataProvider providers = getItem(position);

        Message = provider.message;

            numbers = provider.number;

        POSITION = provider.position;
        CHAT_TXT.setText(Message);
        //CHAT_TXT.setBackgroundResource(POSITION ? R.drawable.bubble_b : R.drawable.bubble_a);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    if (numbers == 0)
    {
        params.gravity = Gravity.RIGHT;
        CHAT_TXT.setBackgroundResource(R.drawable.bubble_a);

    }
    else
    {
        params.gravity = Gravity.LEFT;
        CHAT_TXT.setBackgroundResource(R.drawable.bubble_b);
   }



        CHAT_TXT.setLayoutParams(params);
        return convertView;
    }


}

