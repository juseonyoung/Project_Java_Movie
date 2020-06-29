package common;

import java.io.BufferedInputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class BoxOfficeParser {

	String key = "430156241533f1d058c603178cc3ca0e";

	String today = "";

	String[][] mvRank = new String[10][12];
	String url =""; //여기로 호출한 url들어감
	
	// 생성자 만들기!!!!
	public BoxOfficeParser() { 
		this.url =makeURL();
		//System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		//System.out.println("LOG: Parsing URL Completed >>> "+url);
		//System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
	}
	
	// 1. parsing할 url 주소 생성(URL + KEY + DATE)
	public String makeURL() {

		// 오늘 날짜 구하기

		Calendar cal = Calendar.getInstance();

		cal.add(Calendar.DATE, -1);

		System.out.println("포맷 전 : " + (cal.getTime()));

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		today = sdf.format(cal.getTime());

		System.out.println("포맷 후 :" + today);

		// 제공서비스에서 응답예시를 복붙 후

		String url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/"

				+ "searchDailyBoxOfficeList.json"

				+ "?key=" + key

				+ "&targetDt=" + today;

		return url;

	}

	// 2. web상의 url 주소의 json데이터를 읽음
	private String readURL(String preUrl) throws Exception {
		// 읽을 게 없다
		BufferedInputStream reader = null;

		try {
			//url 가서 주소 가져와
			URL url = new URL(preUrl);
			//url 열어서 burrer에 전달->reader 저장
			reader = new BufferedInputStream(url.openStream());

			StringBuffer buffer = new StringBuffer();

			int i;
			//4096크기만큼 패킷 쪼개서 저장해라
			byte[] b = new byte[4096];

			while ((i = reader.read(b)) != -1) {
				// i에 값이 계속 들어가는데 다 ~~읽고 데이터가 없으면 -1을 반환
				// -1이 아닌 경우까지 계속while buffer에 데이터 집어넣어라
				buffer.append(new String(b, 0, i));

			}
			// buffer에 쌓인 데이터 toString 문자열로 바꿔라
			return buffer.toString();

		} finally {

			if (reader != null) {

				reader.close();

			}

		}

	}

	// 3. data parsing 
	public String[][] getParser() throws Exception {

		JSONParser parser = new JSONParser();

		JSONObject obj = (JSONObject) parser.parse(readURL(url));

		JSONObject json = (JSONObject) obj.get("boxOfficeResult"); //전체를 총괄함(key)
		//value값 다 가져와라 

		JSONArray array = (JSONArray) json.get("dailyBoxOfficeList"); //list 1~10까지 정보 담김

		for (int i = 0; i < array.size(); i++) {

			JSONObject entity = (JSONObject) array.get(i);

			String rank = (String) entity.get("rank"); //순위
			String movieNm = (String) entity.get("movieNm"); //영화제목
			String audiAcc = (String) entity.get("audiAcc"); //누적관객
			String salesAcc = (String) entity.get("salesAcc"); //누적매출

			//배열에 담아
			mvRank[i][0] = rank;
			mvRank[i][1] = movieNm;
			mvRank[i][8] = audiAcc;
			mvRank[i][9] = salesAcc;

		}

		return mvRank;

	}

}
