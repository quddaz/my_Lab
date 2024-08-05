package quddaz.myblog.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Board {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  private String title;

  private String content;

  private Long views;

  private Timestamp updateTimestamp;

  @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
  private List<Recommend> recommendList = new ArrayList<>();

  public Board(){};
}
