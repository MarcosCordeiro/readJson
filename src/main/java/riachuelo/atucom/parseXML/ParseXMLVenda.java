package riachuelo.atucom.parseXML;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;

public class ParseXMLVenda {
    public static void main(String[] args) {

        try {

            StringBuilder saida = new StringBuilder();
            Files.walk(Paths.get("/home/marcosc/Desktop/tools/getFile2/logs/LOJA_11")).forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    BufferedReader br;
                    try {
                        
                        br = new BufferedReader(new FileReader(filePath.toString()));
                        ParseXMLVenda parseVenda = new ParseXMLVenda();
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line.trim());
                            sb.append(System.getProperty("line.separator"));
                        }
                        saida.append(parseVenda.getTRXML(sb));
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });

            PrintWriter writer = new PrintWriter("/home/marcosc/Desktop/saida_comissao.txt", "UTF-8");
            writer.write(saida.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private StringBuilder getTRXML(StringBuilder file) {
        StringBuilder saida = new StringBuilder();
        try {
            String[] lines = file.toString().split("\\n");
            // System.out.println("FAZENDO PARSE DO ARQUIVO...");
            for (String line : lines) {
                JSONObject featureJsonObj = (JSONObject) new JSONParser().parse(line);

                String tr = (String) featureJsonObj.get("TIPO");
                String registro = (String) featureJsonObj.get("TIPO_REGISTRO");

                if (tr.toUpperCase().equals("XML") && registro.toUpperCase().equals("T18")) {
                    String xml = (String) featureJsonObj.get("REGISTRO");
                    InputStream stream = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
                    saida.append(getTR(stream).toString());
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return saida;
    }

    private StringBuilder getTR(InputStream xml) {
        StringBuilder saida = new StringBuilder();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xml);

            String data = doc.getElementsByTagName("tr:data_abertura_sembarra").item(0).getTextContent();
            saida.append("INSERT INTO #TMP VALUES (");
            saida.append("11,");

            String codCaixa = doc.getElementsByTagName("tr:id_caixa").item(0).getTextContent();
            saida.append(codCaixa);
            saida.append(",");

            String codCupom = doc.getElementsByTagName("tr:sequencia").item(0).getTextContent();
            saida.append(codCupom);
            saida.append(",'");

            saida.append(data.substring(4) + "-" + data.substring(2, 4) + "-" + data.substring(0,2));
            saida.append(" 00:00:00',");
            String codVendedor = doc.getElementsByTagName("tr:codVendedor").item(0).getTextContent();
            saida.append(codVendedor);
            saida.append(",");
            String precoFinal = doc.getElementsByTagName("tr:precoFinalSTR").item(0).getTextContent();
            saida.append((Double.parseDouble(precoFinal.trim()) / 100));
            saida.append(")");
            saida.append(System.getProperty("line.separator"));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return saida;
    }
}
