package fit.se.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "post_comment")
public class PostComment {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id", nullable = false)
   private Long id;

   @NotNull
   @ManyToOne(fetch = FetchType.LAZY, optional = false)
   @JoinColumn(name = "postId", nullable = false)
   private Post post;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "parentId")
   private PostComment parent;

   @Size(max = 100)
   @NotNull
   @Column(name = "title", nullable = false, length = 100)
   private String title;

   @NotNull
   @ColumnDefault("0")
   @Column(name = "published", nullable = false)
   private Boolean published = false;

   @NotNull
   @Column(name = "createdAt", nullable = false)
   private Instant createdAt;

   @Column(name = "publishedAt")
   private Instant publishedAt;

   @Lob
   @Column(name = "content")
   private String content;

   @OneToMany(mappedBy = "parent")
   private Set<PostComment> postComments = new LinkedHashSet<>();

}