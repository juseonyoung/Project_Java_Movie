package common;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Scanner;

import daum.BoxOfficeDaum;
import naver.BoxOfficeNaver;
import naver.ReplyCrawlerNaver;

public class MovieMain {
	

	public static void main(String[] args) throws Exception { // 왜 IOException아닌지?

		// scanner는 사용자에게 키보드로 입력받게 하는 것

		// 박스오피스 가져오기 위해 객체 생성
		BoxOfficeParser bParser = new BoxOfficeParser(); // 박스오피스의 생성자 호출
		BoxOfficeNaver bon = new BoxOfficeNaver();
		BoxOfficeDaum don = new BoxOfficeDaum();
		ReplyCrawlerNaver nCrawler = new ReplyCrawlerNaver();

		// 순위, 영화제목, 예매율, 장르, 상영시간, 개봉일자, 감독
		// 출연진, 누적관객수, 누적매출액, 네이버코드 , 다음코드
		String[][] mvRank = new String[10][12];

		// 1. 박스오피스 정보 + 네이버 영화 정보 + 다음영화 정보(1-10)
		// 1-1. 박스오피스 파싱
		mvRank = bParser.getParser(); // 박스오피스의 3번 호출

		// 1-2 네이버 박스오피스 크롤링
		mvRank = bon.naverMovieRank(mvRank); // 4개 저장

		// 1-3. daum 박스오피스 크롤링
		mvRank = don.daumMovieRank(mvRank);

		// 2.뷰단 실행
		// userVal = 사용자가 입력한 영화순위
		int userVal = userInterface(mvRank);
		
		
		// 유효성 체크( 비정상의 범주를 내가 정해야 함)
		// >> 1~10까지의 값(정상)
		// 1. 1~10 이외의 숫자를 넣었을 때
		// 2. 정보없는 영화 선택했을 때2
		

		// 3. 사용자가 선택한 영화의 네이버, 다음 댓글 정보를 수집 및 분석
		HashMap<String, Integer> nmap = nCrawler.naverCrawler(mvRank[userVal-1][1], mvRank[userVal-1][10]); //유저가 선택한 영화순위, 네이버 코드
		
		
		// 4. 사용자 결과 출력
		// (double) 기본자료형에 괄호 씌워서 형변환
		// round 함수 반올림
		//Double avgNaver = (double) (nmap.get("total")/nmap.get("count"));
		double avgNaver = Math.round(nmap.get("total")/nmap.get("count")*100 /100.0);
		DecimalFormat dropDot = new DecimalFormat(".#"); //소수점 첫째자리까지 나와라
		DecimalFormat threeDot = new DecimalFormat("###,###");
		// 28line  보면 배열에 문자열 담음!! 매출액, 관객수는 integer로 바꿔주기 위해
		//integer.parseInt 써준다 
		
		
		System.out.println("○○○○○○○○○○○○○○○○○○○○○○○○○○○○○○○○○○○");
		System.out.println("○ Description of" + mvRank[userVal-1][1]);
		System.out.println("○ 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.println("예매율" + mvRank[userVal-1][2] + "%, 장르" + mvRank[userVal-1][3]);
		System.out.println("상영시간:" + mvRank[userVal-1][4] + ",개봉일자" + mvRank[userVal-1][5]);
		System.out.println("감독:" + mvRank[userVal-1][6]);
		System.out.println("출연진:" + mvRank[userVal-1][7]);
		System.out.println("누적 ->[관객수:" + threeDot.format(Integer.parseInt(mvRank[userVal-1][8])) + "명] [매출액:" +threeDot.format(Integer.parseInt(mvRank[userVal-1][9]))+"원]");
		System.out.println("네이버 ->[댓글수 :" + nmap.get("count") + "건][평점:" + avgNaver +"점]");
		System.out.println("○○○○○○○○○○○○○○○○○○○○○○○○○○○○○○○○○○○");
		
	} 

	
// mvRank 출력하는 코드
// view :프로그램 시작 인터페이스 + 사용자 값 입력
	public static int userInterface(String[][] mvRank) {
		int userVal = 0; // nextline은 문자열 받을때
		Scanner sc = new Scanner(System.in); // static 붙이면 되지만 사용하는 것 좋은 방법은 아님
		// 2. view단
		// 2-1. 유저에게 박스오피스 예매율 1~10위까지의 정보 제공

		// 현재 날짜 계산하기
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss"); // 내맘대로
		String today = sdf.format(cal.getTime());
		// SimpleDateFormat engSdf = new SimpleDateFormat("MM.dd", new Locale("en", "");
		// String engDay = engSdf.format(cal.getTime());

		System.out.println(
				"☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞☞");
		System.out.println("movie ver1.2");
		System.out.println(">>> Developer : nyongnyong");
		System.out.println("TODAY:" + today);
		System.out.println(
				"박스오피스 예매율" + (cal.get(Calendar.MONTH) + 1) + "월" + cal.get(Calendar.DATE) + "일 한국 박스오피스 10위권");

		for (int i = 0; i < mvRank.length; i++) {
			String noneCode = "";
			if (mvRank[i][10] == null) {
				noneCode = "정보없음";
			}

			System.out.println("■■■" + "\t" + mvRank[i][0] + "위:" + mvRank[i][1] + noneCode);
		}

		// 2-2. 사용자가 입력하는 부분
		// 몇번 틀릴 지 모르니까 while로 돌려줌
		while (true) {
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.println("★★보고싶은 영화 번호를 입력하세요★★");
			System.out.print(" >> 번호:");
			userVal =sc.nextInt();

			if (userVal < 0 || userVal > 10) {
				// 잘못된 값
				// 0은 범주 아니지만 이스터에그 숨겨놀 것임
				System.out.println(">> [Warning] 1~10 사이의 숫자를 입력하세요:(");
				continue;
			} else if (mvRank[userVal - 1][10] == null) {
				// 사용자가 입력한 번호의 영화가 있는지 없는지 체크
				System.out.println(">> [Warning] 해당 영화는 상영정보가 없습니다! 다른 영화를 선택해 주세요^^");
				continue;
			} else {
				// 통과 :사용자의 값이 0~10
				//sc.close();
				break;
			}
		}
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		return userVal;
	}

	public static void printArr(String[][] mvRank) {
		System.out.println(
				"◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆");
		for (int i = 0; i < mvRank.length; i++) {
			System.out.print(mvRank[i][0] + "\t");
			System.out.print(mvRank[i][1] + "\t");
			System.out.print(mvRank[i][2] + "\t");
			System.out.print(mvRank[i][3] + "\t");
			System.out.print(mvRank[i][4] + "\t");
			System.out.print(mvRank[i][5] + "\t");
			System.out.print(mvRank[i][6] + "\t");
			System.out.print(mvRank[i][7] + "\t");
			System.out.print(mvRank[i][8] + "\t");
			System.out.print(mvRank[i][9] + "\t");
			System.out.print(mvRank[i][10] + "\t");
			System.out.println(mvRank[i][11]);
		}

	}
}
