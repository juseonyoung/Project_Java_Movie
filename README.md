# :movie_camera:Project_Java_Movie

JAVA 기반의 한국 영화 박스오피스 1~10위까지의 정보를 파싱 및 크롤링하고 mognoD에 저장 후 사용자에게
정보를 출력해주는 콘솔 프로그래밍


## :heavy_check_mark:Developer Environment

  - Language: [:crocodile:JAVA 1.8](#getting-started)
  - IDE Tool: [:computer:Eclipse](#running-the-tests)
  - Package Manager: [:snake:MavenRepository](#deployment)
  - Using Package: [Jsoup, Json-simple, mongo-java-driver](#built-with)
  - Version Tools: [Github, Sourcetree]()
  - Parsing URL: [:ghost:한국 영화진흥위원회](https://www.kofic.or.kr/kofic/business/main/main.do)
  - Crawling URL: [네이버 영화](https://movie.naver.com/)[다음 영화](https://movie.daum.net/main/new#slide-1-0)
  
## :floppy_disk: Repository structure description
#### 1. src/common
  - [MovieMovieMain](https://github.com/juseonyoung/Project_Java_Movie/blob/master/MovieMovie/src/common/MovieMain.java): 프로그램 시작하는 곳 + 콘솔 프로그래밍 view단
  - [BoxOfficeParser](https://github.com/juseonyoung/Project_Java_Movie/blob/master/MovieMovie/src/common/BoxOfficeParser.java): 한국영화진흥위원회에서 일별 박스오피스 정보 수집(랭크, 영화제목, 누적 관객수, 누적 매출액)
#### 2. src/naver
  - [BoxOfficeNaver](https://github.com/juseonyoung/Project_Java_Movie/blob/master/MovieMovie/src/naver/BoxOfficeNaver.java): Naver에서 Boxoffice 1~10위까지 영화 코드(제목, 상영일자, 감독, 출연진 등) 및 코드(네이버 고유 영화코드) 수집
  - [ReplyCrawlerNaver](https://github.com/juseonyoung/Project_Java_Movie/blob/master/MovieMovie/src/naver/ReplyCrawlerNaver.java): Naver에서 해당 영화의 댓글, 평점, 작성자, 작성일자 수집해서 mongoDB에 저장
#### 3. src/daum
  - [BoxOfficeDaum](https://github.com/juseonyoung/Project_Java_Movie/blob/master/MovieMovie/src/daum/BoxOfficeDaum.java): Daum에서 Boxoffice 1~10위까지 영화 코드(다음 고유 영화코드) 수집
  - [ReplyCrawlerDaum](): Daum에서 해당 영화의 댓글, 평점, 작성자, 작성일자 수집해서 mongoDB에 저장
#### 4. src/persistence
  - [ReplyDAO](https://github.com/juseonyoung/Project_Java_Movie/blob/master/MovieMovie/src/persistence/ReplyDAO.java): 네이버, 다음에서 수집한 영화 댓글 저장 또는 삭제할 때 사용하는 DAO
#### 5. src/domain
  - [ReplyDTO](https://github.com/juseonyoung/Project_Java_Movie/blob/master/MovieMovie/src/domain/ReplyDTO.java): 네이버, 다음에서 영화 댓글 수집 후 mongoDB에 저장 할 때 사용하는 DTO
#### 6. pom.xml
  - [pom.mxl](https://github.com/juseonyoung/Project_Java_Movie/blob/master/MovieMovie/pom.xml): Maven 에서 build할 library 설정하는 장소


## :speech_balloon: How to use?
  1. 한국영화진흥위원회에서 key를 가져온다
  2. 박스오피스Parser에서 발급받은 key를 교체한다.
  3. ReplyDAO에서 MongoDB를 세팅한다.(connect, DB, Collection 등)
  4. 메인프로그램을 실행한다.
  5. 1~10위 중 원하는 영화를 선택한다 → 범위 내 원하는번호 숫자 입력
  6. 실행한다
