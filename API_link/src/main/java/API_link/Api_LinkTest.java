package API_link;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.net.URLEncoder;

@RestController
public class Api_LinkTest {

  @GetMapping("/forecast")
  public ResponseEntity<String> callForecastApi(
      @RequestParam(value = "rsdAreaCd") String rsdArea,
      @RequestParam(value = "ageCd") String ageCd,
      @RequestParam(value = "closStdrYm") String closStdrYm
  ) {
    StringBuilder result = new StringBuilder();

    try {
      String apiUrl = "https://eis.work.go.kr/opi/joApi.do";
      String apiSecd = "OPIA";
      String sxdsCd = "M";
      int bgnPage = 1;
      int display = 10;

      // URL 구성
      String urlStr = apiUrl + "?apiSecd=" + URLEncoder.encode(apiSecd, "UTF-8")
          + "&rsdAreaCd=" + URLEncoder.encode(rsdArea, "UTF-8")
          + "&sxdsCd=" + URLEncoder.encode(sxdsCd, "UTF-8")
          + "&ageCd=" + URLEncoder.encode(ageCd, "UTF-8")
          + "&rernSecd=XML"
          + "&closStdrYm=" + URLEncoder.encode(closStdrYm, "UTF-8")
          + "&bgnPage=" + bgnPage
          + "&display=" + display;

      URL url = new URL(urlStr);

      HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
      urlConnection.setConnectTimeout(3000);
      urlConnection.setReadTimeout(3000);
      urlConnection.setRequestMethod("GET");
      urlConnection.setDoInput(true);

      if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
        InputStream inputStream = urlConnection.getInputStream();
        result.append(readStreamToString(inputStream, "EUC-KR"));
      } else {
        throw new IOException("HTTP error code : " + urlConnection.getResponseCode());
      }

      urlConnection.disconnect();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return new ResponseEntity<>(result.toString(), HttpStatus.OK);
  }

  // InputStream을 문자열로 변환하는 메서드
  private String readStreamToString(InputStream stream, String charset) throws IOException {
    StringBuilder result = new StringBuilder();

    try (BufferedReader br = new BufferedReader(new InputStreamReader(stream, charset))) {
      String readLine;
      while ((readLine = br.readLine()) != null) {
        result.append(readLine).append("\n");
      }
    }

    return result.toString();
  }
}
