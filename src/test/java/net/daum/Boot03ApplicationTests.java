package net.daum;

import net.daum.dao.BoardRepository;
import net.daum.vo.BoardVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@SpringBootTest
class Boot03ApplicationTests {

	@Autowired
	private BoardRepository boardRepo;

	@Test
	public void testInsertboard20(){
		//20개의 샘플 레코드 저장
		for(int i=1; i<=20; i++){
			BoardVO b = new BoardVO();
			b.setWriter("user0" + (i%10));
			b.setTitle("게시판 제목..:"+ i);
			b.setContent("내용..:" + i);

			boardRepo.save(b);
			System.out.println("------");
		}
	}

	@Test
	public void findBoardListByTitle(){
		List<BoardVO> list = boardRepo.findBoardVOByTitle("게시판 제목..:5");
		list.forEach(System.out::println);
	}

	@Test
	public void findBoardListByWriterNotNull(){
		System.out.println("----------글쓴이 NotNull 레코드----------");
		List<BoardVO> list = boardRepo.findBoardVOByWriterIsNotNull();
		list.forEach(System.out::println);

		System.out.println("----------끝 단어5인 레코드----------");
		List<BoardVO> listBoard = boardRepo.findBoardVOByContentEndingWith("5");
		listBoard.forEach(System.out::println);
	}

	@Test
	public void findByWriter(){
		boardRepo.findByWriter("user00")
				.forEach(System.out::println);
	}

	@Test
	public void findByWriterContaining(){	//user00~user09
		Collection<BoardVO> list = boardRepo.findByWriterContaining("ser0");
		list.forEach(System.out::println);
	}

	@Test
	public void findByTitleContainingOrContentContaining(){ //제목 '10' or 내용 '15' 포함
		boardRepo.findByTitleContainingOrContentContaining("10", "15")
				.forEach(System.out::println);
	}

	@Test
	public void findByTitleContainingAndBnoGreaterThan(){ // 제목에 '5' 포함 and 글번호 '10' 초과
		boardRepo.findByTitleContainingAndBnoGreaterThan("5", 10)
				.forEach(System.out::println);
	}

	@Test
	public void findByBnoGreaterThanOrderByBnoDesc(){ // 글번호 '10' 초과한 게시물들을 번호 내림차순 정렬
		boardRepo.findByBnoGreaterThanOrderByBnoDesc(10)
				.forEach(System.out::println);
	}

	@Test
	public void findByTitleWithJPQL(){
		boardRepo.findByTitle("1") //제목에 1이 포함된 게시글 order by bno desc
				.forEach(System.out::println);
	}

	@Test
	public void findByContentWithJPQL(){ //내용에 0이 포함된 게시글 order by bno desc
		boardRepo.findByContent("0")
				.forEach(System.out::println);
	}

	@Test
	public void findSomeColumnsByTitle(){
		boardRepo.findSomeColumnsByTitle("0")
				.forEach(arr -> {
					System.out.println(arr.length);
					System.out.println(Arrays.toString(arr));
				});

		List<Object[]> someColumnsByTitle = boardRepo.findSomeColumnsByTitle("0");
		System.out.println(someColumnsByTitle.size() + "------");
	}
}