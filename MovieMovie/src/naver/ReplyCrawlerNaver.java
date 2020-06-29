package naver;

import java.io.IOException;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import domain.ReplyDTO;
import persistence.ReplyDAO;

public class ReplyCrawlerNaver {

	int count = 0;
	int page = 1; // 1페이지 최초에 한번 실행
	int total =0;
	String prePage = "";

	ReplyDAO rDao = new ReplyDAO();

	// hashmap은 파이썬의 dict(key:value)type, 기본자료형을 쓸 수 없다
	// <>꺾새.. generic(맞는 타입이 오는지 확인하는 수문장 역할)
	// 기본자료형8가지를 객체자료형으로 바꿔주는 것이 wrapper class
	// 기본자료형 int를 Integer로 입력하여 객체자료형으로 변환
	public HashMap<String, Integer> naverCrawler(String movieNm, String naverCode) throws IOException {
		// System.out.println(naverCode);
		// 수집하는 댓글의 영화가 mongoDB에 저장되어 있는 영화라면
		// 해당 영화 댓글 우선삭제 후 새로운 댓글 저장
		rDao.deleteReply(movieNm);

		while (true) {
			String url = "https://movie.naver.com/movie/bi/mi/pointWriteFormList.nhn?code=" + naverCode
					+ "&type=after&isActualPointWriteExecute=" + "false&isMileageSubscriptionAlready="
					+ "false&isMileageSubscriptionReject=false&page=" + page; // 페이지 바꿔줘ㅏ

			Document doc = Jsoup.connect(url).get();
			// movielist의 주소를 담은 doc
			Elements movielist = doc.select("div.score_result > ul>li");
			// 10건을 돌리려면 movielist 필요
			String nowPage = doc.select("input#page").attr("value");
			System.out.println(prePage + "," + nowPage);

			if (nowPage.equals(prePage)) {
				break;
			} else {
				prePage = nowPage;
			}

			String writer = "";
			int score = 0;
			String regdate = "";
			String content = "";

			for (Element movie : movielist) {

				writer = movie.select("div.score_reple a>span").get(0).text();
				score = Integer.parseInt(movie.select("div.star_score >em").get(0).text());
				regdate = movie.select("div.score_reple em").get(1).text().substring(0, 10);
				content = movie.select("div.score_reple > p>span").get(0).text();

				 System.out.println("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
				 System.out.println("작성자:" + writer);
				 System.out.println("댓글:" + content);
				 System.out.println("평점:" + score + "점");
				 System.out.println("작성시간:" + regdate);

				// mongodb 에 댓글 1건을 저장하는 코드
				ReplyDTO rDto = new ReplyDTO(movieNm, content, writer, score, regdate);
				// System.out.println(rDto.toString()); //가방에 잘 담겼는지 tostring으로 확인
				rDao.addReply(rDto);
				total+= score;
				count++;
			} // 여기까지 10건 반복이 끝
			page++;
		}
		System.out.printf("총 %d건", count);
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("count", count); //key값 이용해 value 꺼낸다
		map.put("total", total);
		
		return map;
	}

}
