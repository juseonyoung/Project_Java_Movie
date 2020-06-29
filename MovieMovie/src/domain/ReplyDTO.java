package domain;

public class ReplyDTO { // 순서대로
	// 반드시 private 붙여주기
	// 1.변수

	private String movieNm; // 영화 제목
	private String content; // 댓글내용
	private String writer; // 작성자
	private int score; // 평점
	private String date; // 작성일자

	public ReplyDTO() {
	} // 디폴트 생성자

	// 5개의 값을 담을 수 있는 DTO가방
	// 메서드 오버로딩
	// 2. 생성자
	public ReplyDTO(String movieNm, String content, String writer, int score, String date) {
		super();
		this.movieNm = movieNm;
		this.content = content;
		this.writer = writer;
		this.score = score;
		this.date = date;
	}

	// 3. getter setter
	public String getMovieNm() {
		return movieNm;
	}

	public void setMovieNm(String movieNm) {
		this.movieNm = movieNm;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	// 4. toString!! source 이용해
	@Override
	public String toString() {
		return "ReplyDTO [movieNm=" + movieNm + ", content=" + content + ", writer=" + writer + ", score=" + score
				+ ", date=" + date + "]";
	}
}
