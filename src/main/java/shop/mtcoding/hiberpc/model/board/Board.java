package shop.mtcoding.hiberpc.model.board;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.hiberpc.model.user.User;

@Getter
@Setter // dto 생성 시 setter는 필요 x
@Entity
@NoArgsConstructor
@Table(name = "board_tb")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String content;
    // private Integer userId; 원래 외래키를 붙였던 방식
    @ManyToOne // (fetch = FetchType.EAGER) eager이면 연관된 테이블 들고 오고, lazy면 들고오지 않음
    private User user;
    // 연결시키고싶은 객체를 기입 후 어노테이션 붙이면 hibernate가 알아서 db에 해당 객체의 primary key만을 저장해줌
    // To 뒤에 있는 게 객체 --- 즉,ManyToOne 이란 유저 한 명이 n개의 board를 작성할 수 있지만 board 하나는 user
    // 하나가 작성가능하다는 의미
    @CreationTimestamp
    private Timestamp createdAt;

    @Builder
    public Board(Integer id, String title, String content, User user, Timestamp createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
        this.createdAt = createdAt;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Board [id=" + id + ", title=" + title + ", content=" + content + ", user=" + user + ", createdAt="
                + createdAt + "]";
    }

}
