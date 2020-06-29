package naver;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BoxOfficeNaver {
	// 전역변수 alt 방향키 위
	String url = "https://movie.naver.com/movie/running/current.nhn";
	String title = ""; // 영화제목
	String score = ""; // 영화평점
	String bookRate = "0"; // 예매율
	String genre = ""; // 영화장르
	String movieTime = ""; // 상영시간
	String openDt = ""; // 개봉일
	String director = ""; // 감독
	String actor = ""; // 영화 출연진
	String naverCode = ""; // 네이버 영화 코드
	int finalCnt =0;    // 수집을 멈추기 위한 변수(1~10위까지 완료)
	

	public String[][] naverMovieRank(String[][] mvRank) throws IOException {

		Document doc = Jsoup.connect(url).get();

		Elements movielist = doc.select("div.lst_wrap > ul.lst_detail_t1 > li");
		for (Element movie : movielist) { // 예매율, 감독, 배우가 없는 부분도 있으니까 0으로 두고
			// 예매율, 감독, 출연진 초기화
			if(finalCnt ==10) {
				// 1~10위까지의 영화정보 수집완료! 빠져나가세요
				break;
			}
			

			// 네이버 영화정보 크롤링
			title = movie.select("dt.tit > a").text(); // 타이틀은 항상 있음
			
			int flag =0;
			for(int i=0; i<mvRank.length; i++) {
				if(mvRank[i][1].equals(title)) { //i번지는 순위를 알려줌
					// 박스오피스 1~10위권 내의 영화로 판별 되어 크롤링 시작!
					flag =i; //0~9값만 input
					break;
				}
			}
			// 1-10위권 외의 영화는 크롤링 하지 않겠다는 코드
			// flag가 0~9사이의 값이면 크롤링 시작
			// 그 외의 값이면 말아라..
			
			if(flag <0 || flag >9) {
				continue;
			}
			
			bookRate = "0";
			director = "";
			actor = "";

			
			if (movie.select("span.num").size() == 2) { // 예매율이 있으면
				bookRate = movie.select("span.num").get(1).text();
			}
			score = movie.select("span.num").get(0).text();
			genre = movie.select("dd > span.link_txt").get(0).text();

			String temp = movie.select("dl.info_txt1 > dd").get(0).text();
			int beginTimeIndex = temp.indexOf("|");
			int endTimeIndex = temp.lastIndexOf("|");

			if (beginTimeIndex == endTimeIndex) { // 3칸으로 나눠진 경우
				movieTime = temp.substring(0, endTimeIndex);
			} else { // 장르나 개봉시간 개봉일 중 2개나 하나만 있는 경우
				movieTime = temp.substring(beginTimeIndex + 2, endTimeIndex);
			}

			// 0: 없음, 1: 있음
			int dCode = 0; // 감독 유무 확인
			int aCode = 0; // 출연진 유무 확인
			if (!movie.select("dt.tit_t2").text().equals("")) {
				// dt.tit_t2 가서 실제값 가져와 ""랑 비교
				dCode = 1; // 감독 있음!
			}
			if (!movie.select("dt.tit_t3").text().equals("")) {
				aCode = 1; // 출연진 있음!
			}

			if (dCode == 1 && aCode == 0) {
				director = movie.select("dd > span.link_txt").get(1).text();
			} else if (dCode == 0 && aCode == 1) {
				actor = movie.select("dd > span.link_txt").get(1).text();
			} else if (dCode == 1 && aCode == 1) {
				director = movie.select("dd > span.link_txt").get(1).text();
				actor = movie.select("dd > span.link_txt").get(2).text();
			}

			String naverHref = movie.select("dt.tit > a").attr("href"); // 네이버 영화 URL
			naverCode = naverHref.substring(naverHref.lastIndexOf("=") + 1); // 네이버 영화코드

			// 영화 개봉일자
			int openDtTxtIndex = temp.lastIndexOf("개봉");
			openDt = temp.substring(endTimeIndex + 2, openDtTxtIndex);
			
			System.out.println("▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩");
			System.out.println("제목 : " + title);
			System.out.println("평점 : " + score + "점");
			System.out.println("예매율 : " + bookRate + "%");
			System.out.println("장르 : " + genre);
			System.out.println("PlayTime : " + movieTime);
			System.out.println("개봉일 : " + openDt);
			System.out.println("감독 : " + director);
			System.out.println("출연진 : " + actor);
			System.out.println("영화코드: " + naverCode);
			
			// 수집된 영화정보를 mvRank에 input
			mvRank[flag][2] = bookRate;
			// mvRank[flag][] =score;
			mvRank[flag][3] = genre;
			mvRank[flag][4] = movieTime;
			mvRank[flag][6] = director;
			mvRank[flag][7] = actor;
			mvRank[flag][10] = naverCode;
			mvRank[flag][5] = openDt;
			finalCnt +=1;
			
		}
		return mvRank;
	}
}
