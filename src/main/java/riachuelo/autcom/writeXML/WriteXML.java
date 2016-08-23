package riachuelo.autcom.writeXML;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WriteXML {
    public static void main(String[] args) {
        WriteXML write = new WriteXML();
        write.mergeCupomNFCe("teste");
    }

    private void mergeCupomNFCe(String template) {
        try (Writer writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream("/home/marcosc/Desktop/filename.txt"), "utf-8"))) {

            writer.write("<?xml version='1.0' encoding='UTF-8'?>\n");
            writer.write("<impressao version='3.10'>\n");
            writer.write("  <dadosAdic>\n");
            writer.write("      <LAR001></LAR001>\n");

            InputStream is = new ByteArrayInputStream(template.getBytes());
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//            BufferedReader reader = readFile("/home/marcosc/git/aut-pdvr/src/main/resources/riachuelo/autcom/impressao/AberturaDoCaixa.mdl");

            StringBuilder content = new StringBuilder();
            String line = null;
            int i = 2;
            while ((line = reader.readLine()) != null) {

                if (line.trim().equals("#parse(\"Rodape.mdl\")")) {
                    BufferedReader readRodape = rodape();
                    String lineRodape = null;
                    while ((lineRodape = readRodape.readLine()) != null) {
                        content.append("        <LAR" + String.format("%03d", i) + ">");
                        content.append(lineRodape);
                        content.append("        </LAR" + String.format("%03d", i) + ">");
                        content.append(System.getProperty("line.separator"));
                        i++;
                    }
                } else if (line.trim().equals("#parse(\"Cabecalho.mdl\")")) {
                    BufferedReader readRodape = cabecalho();
                    String lineRodape = null;
                    while ((lineRodape = readRodape.readLine()) != null) {
                        content.append("        <LAR" + String.format("%03d", i) + ">");
                        content.append(lineRodape);
                        content.append("        </LAR" + String.format("%03d", i) + ">");
                        content.append(System.getProperty("line.separator"));
                        i++;
                    }
                } else {
                    content.append("        <LAR" + String.format("%03d", i) + ">");
                    content.append(line);
                    content.append("        </LAR" + String.format("%03d", i) + ">");
                    content.append(System.getProperty("line.separator"));
                }

                i++;
            }
            System.out.println(content.toString());
            writer.write(content.toString());

            writer.write("  </dadosAdic>\n");
            writer.write("</impressao>\n");
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    private BufferedReader cabecalho() {
        return readFile("/home/marcosc/git/aut-pdvr/src/main/resources/riachuelo/autcom/impressao/Cabecalho.mdl");
    }

    private BufferedReader rodape() {
        return readFile("/home/marcosc/git/aut-pdvr/src/main/resources/riachuelo/autcom/impressao/Rodape.mdl");
    }

    private BufferedReader readFile(String pathFile) {
        BufferedReader reader = null;
        Path file = Paths.get(pathFile);

        try {
            reader = Files.newBufferedReader(file, Charset.forName("utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reader;
    }
}
