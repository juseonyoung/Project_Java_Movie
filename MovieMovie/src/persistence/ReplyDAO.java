package persistence;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import domain.ReplyDTO;

public class ReplyDAO {

	MongoClient client = MongoClients.create();
	MongoDatabase db = client.getDatabase("moviereply");
	MongoCollection<Document> collection = db.getCollection("movie");

	public void addReply(ReplyDTO rDto) { // 댓글 하나를 넣음
		Document doc = new Document("movieNm", rDto.getMovieNm())
				.append("content", rDto.getContent())
				.append("writer", rDto.getWriter())
				.append("score", rDto.getScore())
				.append("regdate", rDto.getDate());

		// 댓글 1건 등록
		collection.insertOne(doc);

	}

	// 댓글 삭제(등록하려는 영화의 댓글이 존재할 때 해당 영화 댓글만 삭제)
	public void deleteReply(String movieNm) {
		collection.deleteMany(new Document("movieNm", movieNm)); // 영화이름이 getmovie 전부삭제
	}

}
