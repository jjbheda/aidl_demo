package com.gavin.pwdclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gavin.pwdInterface.IPwdInterface;
import com.gavin.pwdInterface.User;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = "MainActivity";
    private EditText mEditName;
    private EditText mEditPwd;
    private Button mBtn;

    IPwdInterface pwdInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditName = findViewById(R.id.edit_name);
        mEditPwd = findViewById(R.id.edit_pwd);
        mBtn = findViewById(R.id.btn_commit);
        mBtn.setOnClickListener(this);
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.gavin.pwdservice"
                ,"com.gavin.pwdservice.PwdService"));

        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_commit) {

            String name = mEditName.getText().toString();
            String pwd = mEditPwd.getText().toString();

            if (pwdInterface != null) {
                try {
                    boolean verify = pwdInterface.verifyPwd(new User(name, pwd));
                    if (verify) {
                        Toast.makeText(this, "验证成功!!!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(this, "验证失败!!!", Toast.LENGTH_LONG).show();
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, "未绑定服务!!!", Toast.LENGTH_LONG).show();
            }
        }
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.w(TAG, "onServiceConnected: success ");
            pwdInterface = IPwdInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.w(TAG, "onServiceDisConnected! ");
            unbindService(serviceConnection);
        }
    };

}
