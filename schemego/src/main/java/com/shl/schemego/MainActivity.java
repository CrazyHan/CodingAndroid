package com.shl.schemego;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    EditText etGo;
    EditText etLink;
    EditText etLinkValue;
    EditText etUrl;
    RadioGroup radioPlatform;
    LinearLayout linkParamsContainer;
    Map<String, String> linkMap = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etGo = findViewById(R.id.et_go);
        etLink = findViewById(R.id.et_link);
        etLinkValue = findViewById(R.id.et_link_value);
        etUrl = findViewById(R.id.et_url);
        radioPlatform = findViewById(R.id.radio_platform);
        linkParamsContainer = findViewById(R.id.params_container);
        findViewById(R.id.btn_launch).setOnClickListener(this);
        findViewById(R.id.btn_url_launch).setOnClickListener(this);
        findViewById(R.id.iv_url_clear).setOnClickListener(this);
        findViewById(R.id.btn_copy).setOnClickListener(this);
        findViewById(R.id.confirm_add).setOnClickListener(this);
    }

    private boolean checkAppValite(Intent intent) {
        PackageManager packageManager = getPackageManager();
        List activities = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return activities.size() > 0;
    }

    private Uri getSchemeUri() {
        // 完整的uri：jiayuan://com.jiayuan?from_scheme=true&params={\"go\":289000,\"link\":{http://w.jiayuan.com/w/newm/promotion/newtest.jsp?xxtype=3&sname=zs&uid=-1&sex=m&location=appstartpage-&clk=m_appstartpage&ad_id=&clientVer=7.0&channelID=000&clientID=13}

        String go = etGo.getText().toString();

        // link值，例如：""link\":{http://w.jiayuan.com/w/newm/promotion/newtest.jsp?xxtype=3&sname=zs&uid=-1&sex=m&location=appstartpage-&clk=m_appstartpage&ad_id=&clientVer=7.0&channelID=000&clientID=13}"
//        String link = etLink.getText().toString();


        JSONObject linkObj = new JSONObject(linkMap);

        try {
            linkObj.put("go", go);
            if (!linkMap.isEmpty()) {
                linkObj.put("link", new JSONObject(linkMap));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String launchUriFormat;
        int platform = radioPlatform.getCheckedRadioButtonId();
        if (platform == R.id.radio_jiayuan) {
            launchUriFormat = "jiayuan://com.jiayuan?from_scheme=true&params=%s";
        } else if (platform == R.id.radio_baihe) {
            launchUriFormat = "baihe://com.baihe?from_scheme=true&params=%s";
        } else if (platform == R.id.radio_union) {
            launchUriFormat = "baihejiayuan://com.jiayuan?from_scheme=true&params=%s";
        } else if (platform == R.id.radio_abroad) {
            launchUriFormat = "thisfate://com.jiayuan?from_scheme=true&params=%s";
        } else if(platform == R.id.radio_miyou){
            //Todo 需要改包名
            launchUriFormat = "thisfate://com.jiayuan?from_scheme=true&params=%s";
        }else{
            return null;
        }

        return Uri.parse(String.format(launchUriFormat,linkObj.toString()));
    }

    public void processGoLink() {


        Intent intent = new Intent(Intent.ACTION_VIEW, getSchemeUri());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if (checkAppValite(intent))
            startActivity(intent);
        else
            Toast.makeText(this, "未找到相应APP", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_launch:
                processGoLink();
                break;
            case R.id.btn_url_launch:
                String url = etUrl.getText().toString().trim();
                Intent itUrl = new Intent();
                itUrl.setAction(Intent.ACTION_VIEW);
                itUrl.setData(Uri.parse(url));
                startActivity(itUrl);
                break;
            case R.id.iv_url_clear:
                etUrl.setText("");
                break;
            case R.id.btn_copy:
                ClipboardManager clip = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                clip.setText(getSchemeUri().toString());
                break;
            case R.id.confirm_add:
                if (!TextUtils.isEmpty(etLink.getText().toString()) && !TextUtils.isEmpty(etLinkValue.getText().toString())) {
                    if (!linkMap.containsKey(etLink.getText().toString())) {
                        linkMap.put(etLink.getText().toString(), etLinkValue.getText().toString());
                        addParams(etLink.getText().toString(), etLinkValue.getText().toString());
                    } else {
                        Toast.makeText(this, "不能添加重复的参数", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Link参数名或者参数值不能为空", Toast.LENGTH_SHORT).show();
                }
                break;


        }
    }

    public void addParams(final String key, String value) {
        final View view = LayoutInflater.from(this).inflate(R.layout.link_viewholder_item, linkParamsContainer, false);

        TextView textView = view.findViewById(R.id.param);

        Button button = view.findViewById(R.id.delete);

        textView.setText(String.format(getResources().getString(R.string.params), key, value));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linkMap.remove(key);
                linkParamsContainer.removeView(view);
            }
        });
        final GestureDetector detector = new GestureDetector(MainActivity.this, listener);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return detector.onTouchEvent(event);
            }
        });
        linkParamsContainer.addView(view);
    }

    GestureDetector.SimpleOnGestureListener listener = new GestureDetector.SimpleOnGestureListener() {
        @Override public boolean onDoubleTap(MotionEvent e) {
            Toast.makeText(MainActivity.this, "双击666", Toast.LENGTH_SHORT).show();
            return super.onDoubleTap(e);
        }
    };


}
