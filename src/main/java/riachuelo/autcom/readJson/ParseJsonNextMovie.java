package riachuelo.autcom.readJson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ParseJsonNextMovie {

    public static void main(String[] args) {
        try {
            FileReader file = new FileReader("UpComing.json");
            ParseJsonNextMovie teste = new ParseJsonNextMovie();
            teste.parseJson(file);
//            teste.readPage(new URL("http://ec2-52-37-24-245.us-west-2.compute.amazonaws.com:8080/NextMovieWS/api/movies/upcoming"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void parseJson(FileReader file) {
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject)jsonParser.parse(file);

            Long page = (Long) jsonObject.get("page");
            JSONObject dates = (JSONObject) jsonObject.get("dates");
            String dateMax = (String)dates.get("maximum");
            String dateMin = (String)dates.get("minimum");
            Long totalPages = (Long)jsonObject.get("total_pages");
            Long totalResults = (Long)jsonObject.get("total_results");
            
            System.out.println("===================================================");
            System.out.println("                  NEXT MOVIE APP                   ");
            System.out.println("===================================================");
            System.out.println("Page: " + page);
            System.out.println("Dates Between: " + dateMin + " and " + dateMax);
            System.out.println("Total Pages: " + totalPages);
            System.out.println("Total Results: " + totalResults);
            
            
            JSONArray results = (JSONArray)jsonObject.get("results");
            
//            for(int i=0; i<results.size();i++) {
//               }
            @SuppressWarnings("rawtypes")
            Iterator i = results.iterator();
            while(i.hasNext()) {
                JSONObject  innerObj        = (JSONObject) i.next();
                String      posterPath      = (String)innerObj.get("poster_path");
                Boolean      adult          = (Boolean)innerObj.get("adult");
                String      overview        = (String)innerObj.get("overview");
                String      releaseDate     = (String)innerObj.get("release_date");
                JSONArray   genreIds        = (JSONArray)innerObj.get("genre_ids");
                Long        id              = (Long)innerObj.get("id");
                String      originalTitle   = (String)innerObj.get("original_title");
                String      originalLang    = (String)innerObj.get("original_language");
                String      title           = (String)innerObj.get("title");
                String      backdropPath    = (String)innerObj.get("backdrop_path");
                BigDecimal  popularity      = new BigDecimal(innerObj.get("popularity").toString());
                Long        vote            = (Long)innerObj.get("vote_count");
                Boolean     video           = (Boolean)innerObj.get("video");
                BigDecimal  voteAverage     = new BigDecimal(innerObj.get("vote_average").toString());
                
                System.out.println("==================================================");
                System.out.println("      " + title);
                System.out.println("==================================================");
                System.out.println("Poster Path: " + posterPath);
                System.out.println("Adult: " + adult);
                System.out.println("Overview: " + overview);
                System.out.println("Release Date: " + releaseDate);
                System.out.println("Genre IDs: " + genreIds);
                System.out.println("ID: " + id);
                System.out.println("Original Title: " + originalTitle);
                System.out.println("Original Language: " + originalLang);
                System.out.println("Title: " + title);
                System.out.println("Backdrop Path: " + backdropPath);
                System.out.println("Popularity: " + popularity);
                System.out.println("Video: " + video);
                System.out.println("Vote: " + vote);
                System.out.println("Vote Average:" + voteAverage);                  
            }
            
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }
    
    

    private String readPage(URL url) throws Exception {

        @SuppressWarnings("deprecation")
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url.toURI());
        HttpResponse response = client.execute(request);

        Reader reader = null;
        try {
            reader = new InputStreamReader(response.getEntity().getContent());

            StringBuffer sb = new StringBuffer();
            {
                int read;
                char[] cbuf = new char[1024];
                while ((read = reader.read(cbuf)) != -1)
                    sb.append(cbuf, 0, read);
            }

            return sb.toString();

        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    private void getJson() {
        try {
            URL url = new URL("http://ec2-52-37-24-245.us-west-2.compute.amazonaws.com:8080/NextMovieWS/api/movies/upcoming");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

//            if (conn.getResponseCode() != 200) {
//                throw new RuntimeException("Failed : HTTP error code : "
//                        + conn.getResponseCode());
//            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
