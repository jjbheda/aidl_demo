// IPwdInterface.aidl
package com.gavin.pwdInterface;
import com.gavin.pwdInterface.User;
// Declare any non-default types here with import statements

interface IPwdInterface {

    boolean verifyPwd(in User user);

}
