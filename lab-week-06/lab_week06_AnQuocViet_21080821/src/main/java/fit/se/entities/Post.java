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
@Table(name = "post")
public class Post {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id", nullable = false)
   private Long id;

   @NotNull
   @ManyToOne(fetch = FetchType.LAZY, optional = false)
   @JoinColumn(name = "authorId", nullable = false)
   private User author;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "parentId")
   private Post parent;

   @Size(max = 75)
   @NotNull
   @Column(name = "title", nullable = false, length = 75)
   private String title;

   @Size(max = 100)
   @Column(name = "metaTitle", length = 100)
   private String metaTitle;

   @Lob
   @Column(name = "summary")
   private String summary;

   @NotNull
   @ColumnDefault("0")
   @Column(name = "published", nullable = false)
   private Boolean published = false;

   @NotNull
   @Column(name = "createdAt", nullable = false)
   private Instant createdAt;

   @Column(name = "updatedAt")
   private Instant updatedAt;

   @Column(name = "publishedAt")
   private Instant publishedAt;

   @Lob
   @Column(name = "content")
   private String content;

   @OneToMany(mappedBy = "parent")
   private Set<Post> posts = new LinkedHashSet<>();

   @OneToMany(mappedBy = "post")
   private Set<PostComment> postComments = new LinkedHashSet<>();

}