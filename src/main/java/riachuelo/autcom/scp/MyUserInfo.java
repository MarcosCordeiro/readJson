package riachuelo.autcom.scp;

import com.jcraft.jsch.UserInfo;

public class MyUserInfo implements UserInfo {
    public String getPassword() {
        return "@#BranchPDV01";
    }

    public String getPassphrase() {
        return "";
    }

    public boolean promptPassword(String arg0) {
        return true;
    }

    public boolean promptPassphrase(String arg0) {
        return true;
    }

    public boolean promptYesNo(String arg0) {
        return true;
    }

    public void showMessage(String arg0) {
    }
}
