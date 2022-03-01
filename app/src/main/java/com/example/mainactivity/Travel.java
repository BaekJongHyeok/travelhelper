package com.example.mainactivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;


public class Travel extends AppCompatActivity {
    EditText edit;
    public String key = "PPq%2FP8f%2FbAJ4oRgKDDoa3Zt7BLBOwjHUD7Sh1wFd27lA739KV54sunuwkXPjq7pUWr4yFlDewA18fEIl4d2c2g%3D%3D";
    private String requestUrl;
    ArrayList<Travel_item> list = null;
    Travel_item bus = null;
    RecyclerView recyclerView;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();



        setContentView(R.layout.activity_travel);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        findViewById(R.id.button).setOnClickListener(mClickListener);
        edit = (EditText) findViewById(R.id.edit);
//        AsyncTask



    }


    Button.OnClickListener mClickListener = new View.OnClickListener() {
        public void onClick(View v) {

            com.example.mainactivity.Travel.MyAsyncTask myAsyncTask = new com.example.mainactivity.Travel.MyAsyncTask();
            myAsyncTask.execute();
            //이곳에 버튼 클릭시 일어날 일을 적습니다.
        }
    };







    public class MyAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            StringBuffer buffer=new StringBuffer();

            String str= edit.getText().toString();//EditText에 작성된 Text얻어오기
            String location = URLEncoder.encode(str);//한글의 경우 인식이 안되기에 utf-8 방식으로 encoding     //지역 검색 위한 변수


            String requestUrl="http://api.visitkorea.or.kr/openapi/service/rest/KorService/searchKeyword?"
                    +"&serviceKey="+key
                    +"&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest"
                    +"&keyword="+location;


            try {
                boolean b_locationNo1 = false;
                boolean b_plateNo1 = false;
                boolean b_routeId = false;
                boolean b_mapx = false;
                boolean b_mapy = false;
                boolean b_firstimage = false;
                URL url = new URL(requestUrl);
                InputStream is = url.openStream();
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = factory.newPullParser();
                parser.setInput(new InputStreamReader(is, "UTF-8"));

                String tag;
                int eventType = parser.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    switch (eventType) {
                        case XmlPullParser.START_DOCUMENT:
                            list = new ArrayList<Travel_item>();
                            break;
                        case XmlPullParser.END_DOCUMENT:
                            break;
                        case XmlPullParser.END_TAG:
                            if (parser.getName().equals("item") && bus != null) {
                                list.add(bus);
                            }
                            break;
                        case XmlPullParser.START_TAG:
                            if (parser.getName().equals("item")) {
                                bus = new Travel_item();
                            }
                            if (parser.getName().equals("addr1")) b_locationNo1 = true;
                            if (parser.getName().equals("title")) b_plateNo1 = true;
                            if (parser.getName().equals("contentid")) b_routeId = true;
                            if (parser.getName().equals("mapx")) b_mapx = true;
                            if (parser.getName().equals("mapy")) b_mapy = true;
                            if (parser.getName().equals("firstimage")) b_firstimage = true;
                            break;
                        case XmlPullParser.TEXT:
                            if (b_locationNo1) {
                                bus.setLocationNo1(parser.getText());
                                b_locationNo1 = false;
                            } else if (b_plateNo1) {
                                bus.setPlateNo1(parser.getText());
                                b_plateNo1 = false;
                            } else if (b_routeId) {
                                bus.setRouteId(parser.getText());
                                b_routeId = false;
                            } else if (b_mapx) {
                                bus.setMapx(parser.getText());
                                b_mapx = false;

                             } else if (b_mapy) {
                                bus.setMapy(parser.getText());
                                 b_mapy = false;
                            }
                            else if (b_firstimage) {
                                bus.setFirstimage(parser.getText());
                                b_firstimage = false;
                            }
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + eventType);
                    }
                    eventType = parser.next();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //어답터 연결
           Travel_adapter adapter = new Travel_adapter(getApplicationContext(), list);
            recyclerView.setAdapter(adapter);
        }
    }
}
