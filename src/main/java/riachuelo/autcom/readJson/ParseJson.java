package riachuelo.autcom.readJson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ParseJson {

    public static void main(String[] args) {

        try {
            @SuppressWarnings("resource")
            BufferedReader br = new BufferedReader(new FileReader("/home/marcosc/Desktop/MV_011_040_2015-11-02.log"));
            ParseJson teste = new ParseJson();

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line.trim());
                sb.append(System.getProperty("line.separator"));
            }
            teste.parseJson(sb);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parseJson(StringBuilder file) {
        try {
            String[] lines = file.toString().split("\\n");
            System.out.println("FAZENDO PARSE DO ARQUIVO...");
            for (String line : lines) {
//                JSONObject featureJsonObj = (JSONObject) new JSONParser().parse(line);
//
//                Long filial = (Long) featureJsonObj.get("FILIAL");
//                System.out.println(filial);
//
//                Long pdv = (Long) featureJsonObj.get("PDV");
//                System.out.println(pdv);
//
//                String tipo_registro = (String) featureJsonObj.get("TIPO_REGISTRO");
//                System.out.println(tipo_registro);
//
//                String tr = (String) featureJsonObj.get("TIPO");
//                System.out.println(tr);
//
//                String xml = (String) featureJsonObj.get("REGISTRO");
//                System.out.println(xml);
                JSONObject featureJsonObj = (JSONObject) new JSONParser().parse(line);

//                Long filial = (Long) featureJsonObj.get("FILIAL");
//                System.out.println(filial);
//
//                Long pdv = (Long) featureJsonObj.get("PDV");
//                System.out.println(pdv);
//
//                String tipo_registro = (String) featureJsonObj.get("TIPO_REGISTRO");
//                System.out.println(tipo_registro);

                String tr = (String) featureJsonObj.get("TIPO");
                if(tr.equals("18")) {
                    System.out.println(tr);
                    String xml = (String) featureJsonObj.get("REGISTRO");
                    System.out.println(xml);
                }

                
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
