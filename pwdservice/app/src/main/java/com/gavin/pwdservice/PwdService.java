package com.gavin.pwdservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;

import com.gavin.pwdInterface.IPwdInterface;
import com.gavin.pwdInterface.User;

public class PwdService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    private IBinder binder = new IPwdInterface.Stub() {
        @Override
        public boolean verifyPwd(User user) throws RemoteException {
           if (user == null || user.name == null
                   || user.pwd == null) {
               return false;
           }

           if (user.name.equals( "abc") && user.pwd.equals("123")) {
               return true;
           }

            return false;
        }
    };

}
