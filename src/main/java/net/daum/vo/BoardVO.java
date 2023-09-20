package net.daum.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Setter @Getter @ToString
@Entity //JPA(Java Persistence API) Entity
@SequenceGenerator(
        name="bno_seq2_gename", //시퀀스 제너레이터 이름
        sequenceName = "bno_seq2", //시퀀스 이름
        initialValue = 1, //초기값 (default 1)
        allocationSize = 1 //증가값 (default 50)
)//시퀀스 생성기 어노테이션
@Table(name="tbl_boards2") // tbl_board2라는 테이블명을 지정
public class BoardVO {
    //JPA에서 사용하는 Entity bean 클래스 -> 이 클래스를 통해서 오라클DB에 테이블과 시퀀스가 생성됨

    @Id // 기본키
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "bno_seq2_gename"
    ) //시퀀스설정 (시퀀스 제네레이터 이름)
    private int bno;
    private String writer;
    private String title;
    private String content;

    @CreationTimestamp //HIBERNATE의 기능 -> 게시물 등록시점 날짜값 기록
    private Timestamp regdate;
    @UpdateTimestamp //HIBERNATE의 기능 -> 게시물 수정 날짜값 기록
    private Timestamp updatedate;
}