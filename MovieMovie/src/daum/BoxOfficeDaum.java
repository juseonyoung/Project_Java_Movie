package daum;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BoxOfficeDaum {

	String baseurl = "http://ticket2.movie.daum.net/Movie/MovieRankList.aspx";
	int finalCnt = 0; // 수집을 멈추기 위한 변수(1~10위까지 완료)

	public String[][] daumMovieRank(String[][] mvRank) throws IOException {
		Document doc = Jsoup.connect(baseurl).get();
		Elements movielist = doc.select("div.desc_boxthumb > strong.tit_join > a");

		for (Element movie : movielist) {
			if (finalCnt == 10) {
				// 1~10위까지의 영화정보 수집완료! 빠져나가세요
				break;
			}
			
			String title = movie.text();
			int flag = 0;
			for (int i = 0; i < mvRank.length; i++) {
				if (mvRank[i][1].equals(title)) { // i번지는 순위를 알려줌
					// 박스오피스 1~10위권 내의 영화로 판별 되어 크롤링 시작!
					flag = i; // 0~9값만 input
					break;
				}
			}
			// 1-10위권 외의 영화는 크롤링 하지 않겠다는 코드
			// flag가 0~9사이의 값이면 크롤링 시작
			// 그 외의 값이면 말아라..

			if (flag < 0 || flag > 9) {
				continue;
			}

			String url = movie.attr("href");
			Document movieDoc = Jsoup.connect(url).get();

			// 영화 상세페이지가 없는 영화는 제거
			if (movieDoc.select("span.txt_name").size() == 0) {
				continue;
			}

			String daumHref = movieDoc.select("a.area_poster").get(0).attr("href");
			String daumCode = daumHref.substring(daumHref.lastIndexOf("=") + 1, daumHref.lastIndexOf("#"));

			mvRank[flag][11] = daumCode;
			finalCnt += 1;
			// substring 함수!! 앞과 뒤 기준으로 자른다

		}

		return mvRank;
	}
}
