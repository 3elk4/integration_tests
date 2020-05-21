package edu.iis.mto.blog.domain.repository;

import edu.iis.mto.blog.domain.model.AccountStatus;
import edu.iis.mto.blog.domain.model.BlogPost;
import edu.iis.mto.blog.domain.model.LikePost;
import edu.iis.mto.blog.domain.model.User;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class LikePostRepositoryTest {
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private LikePostRepository repository;

	private LikePost likePost;
	private User user;
	private BlogPost blogPost;

	@Before
	public void setUp() {
		user = new User();
		user.setFirstName("Jan");
		user.setLastName("Kowalski");
		user.setEmail("john@domain.com");
		user.setAccountStatus(AccountStatus.NEW);

		blogPost = new BlogPost();
		blogPost.setEntry("SomeBlogPostEntry");
		blogPost.setUser(user);

		likePost = new LikePost();
		likePost.setUser(user);
		likePost.setPost(blogPost);

		repository.deleteAll();
	}

	@Test
	public void shouldFindNoLikesIfRepositoryIsEmpty() {
		List<LikePost> likes = repository.findAll();
		assertThat(likes, hasSize(0));
	}

	@Test
	public void shouldFindOneLikeIfRepositoryContainsOneBlogPostAndUser(){
		entityManager.persist(user);
		entityManager.persist(blogPost);
		LikePost persistedLike = entityManager.persist(likePost);

		List<LikePost> likes = repository.findAll();

		assertThat(likes, hasSize(1));
		assertThat(likes.get(0)
						.getUser(),
				equalTo(persistedLike.getUser()));
		assertThat(likes.get(0)
						.getPost(),
				equalTo(persistedLike.getPost()));
	}

	@Test
	public void shouldSaveNewLike() {
		entityManager.persist(user);
		entityManager.persist(blogPost);

		LikePost result = repository.save(likePost);

		assertThat(result.getId(), notNullValue());
	}

	@Test
	public void shouldDeleteExistingLike() {
		entityManager.persist(user);
		entityManager.persist(blogPost);
		repository.save(likePost);
		repository.delete(likePost);
		List<LikePost> likes = repository.findAll();
		assertThat(likes, hasSize(0));
	}
}
