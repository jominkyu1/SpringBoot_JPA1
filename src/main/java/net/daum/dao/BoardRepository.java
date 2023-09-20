package net.daum.dao;

import net.daum.vo.BoardVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
/** 쿼리 메서드란?
 *  검색 쿼리문에서 메서드 이름만으로 원하는 검색쿼리문을 만들어 내는 메서드를 말한다.
 * */
public interface BoardRepository extends JpaRepository<BoardVO, Integer> {
    public List<BoardVO> findBoardVOByTitle(String title);
    public List<BoardVO> findBoardVOByWriterIsNotNull();
    public List<BoardVO> findBoardVOByContentEndingWith(String endWords);
    public Collection<BoardVO> findByWriter(String writer);

    /** 와일드카드를 이용한 쿼리메서드
     *  like 검색 쿼리메서드 형태)
     *  -----------------------------
     *  형태                쿼리메서드
     *  검색어+'%"         StartingWith
     *  '%'+검색어         EndingWith
     *  '%'+검색어+'%'     Containing
     * */
    public Collection<BoardVO> findByWriterContaining(String writer);

    //or 조건 처리 => '%'+제목+'%' or '%'+내용+'%'
    public Collection<BoardVO> findByTitleContainingOrContentContaining(String title, String content);

    //title like %?% and Bno > ?
    public Collection<BoardVO> findByTitleContainingAndBnoGreaterThan(String title, Integer bno);

    //bno > ? order by bno desc
    public Collection<BoardVO> findByBnoGreaterThanOrderByBnoDesc(Integer bno);


    // JPQL (Java Persistence Query Language)
    @Query( "select b from BoardVO b " +
            "where b.title like %?1% and b.bno >0 " +
            "order by b.bno desc")
    //실제 컬럼명이나 테이블명대신 Entity Bean의 필드명과 클래스명을 사용 (?1의 뜻은 첫번째 pstm)
    public List<BoardVO> findByTitle(String title); //Query어노테이션 사용시 메서드명은 임의대로 사용가능


    @Query( "select b from BoardVO b " +
            "where b.content like %:content% and b.bno>0 " +
            "order by b.bno desc")
    // :변수명을 @Param어노테이션을통해 값을 넘겨줌
    public List<BoardVO> findByContent(@Param("content") String content);


    @Query( "select b.bno, b.title, b.writer, b.regdate " +
            "from BoardVO b " +
            "where b.title like %?1% and b.bno > 0 " +
            "order by b.bno desc")
    //원하는 컬럼만 검색할때는 반환타입이 Entity Bean이 아닌 Object[] 배열이다
    public List<Object[]> findSomeColumnsByTitle(String title);
}
