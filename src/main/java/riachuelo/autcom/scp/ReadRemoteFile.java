package riachuelo.autcom.scp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class ReadRemoteFile {

    public static void main(String args[]) {
        ReadRemoteFile sftp = new ReadRemoteFile();
        StringBuilder retorno = sftp.readRemoteFile("root", "@#BranchPDV01", "10.100.3.65", "/tmp/2015-12-11-0065-0100-PDVRLOG.TXT");
        if(retorno != null)
            System.out.println(retorno.toString());
    }

    public StringBuilder readRemoteFile(String user, String pwd, String ip, String file) {
        ChannelSftp sftpChannel = null;
        Session session = null;
        Channel channel = null;
        StringBuilder sb = null;
        BufferedReader br = null;
        
        try {
            JSch jsch = new JSch();
            
            System.out.println("CONECTANDO EM: " + ip);
            session = jsch.getSession(user, ip, 22);
            session.setPassword(pwd);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setTimeout(3000);
            session.connect();
            System.out.println("CONEXAO ESTABELECIDA");

            channel = session.openChannel("sftp");
            channel.connect();
            sftpChannel = (ChannelSftp) channel;

            System.out.println("BUSCANDO ARQUIVO: " + file);
            System.out.println("FAZENDO DOWNLOAD...");
            InputStream in = sftpChannel.get(file);
            br = new BufferedReader(new InputStreamReader(in));
            sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line.trim());
                sb.append(System.getProperty("line.separator"));
            }
            
        } catch (JSchException je) {
            System.out.println("ERRO AO CONECTAR");
        }catch (Exception ex) {
            System.out.println(ex);
        }finally {
            if(sftpChannel!=null)
                sftpChannel.exit();
            
            if(session!=null)
                session.disconnect();
            
            if(channel!=null)
                channel.disconnect();
        }
        return sb;
    }
}