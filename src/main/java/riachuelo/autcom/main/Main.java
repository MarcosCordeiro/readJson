package riachuelo.autcom.main;

import riachuelo.autcom.readJson.ParseJson;
import riachuelo.autcom.scp.ReadRemoteFile;

public class Main {
    public static void main(String[] args) {
        ReadRemoteFile remoteFile = new ReadRemoteFile();
        ParseJson parseJson = new ParseJson();
        parseJson.parseJson(remoteFile.readRemoteFile("root", "@#BranchPDV01", "10.132.3.67", "/tmp/MV_132_067_2015-11-11.log"));
    }
}
