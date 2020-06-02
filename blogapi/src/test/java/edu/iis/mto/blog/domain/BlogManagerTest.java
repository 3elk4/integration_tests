package edu.iis.mto.blog.domain;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import edu.iis.mto.blog.api.request.PostRequest;
import edu.iis.mto.blog.domain.errors.DomainError;
import edu.iis.mto.blog.domain.model.BlogPost;
import edu.iis.mto.blog.domain.model.LikePost;
import edu.iis.mto.blog.domain.repository.BlogPostRepository;
import edu.iis.mto.blog.domain.repository.LikePostRepository;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import edu.iis.mto.blog.api.request.UserRequest;
import edu.iis.mto.blog.domain.model.AccountStatus;
import edu.iis.mto.blog.domain.model.User;
import edu.iis.mto.blog.domain.repository.UserRepository;
import edu.iis.mto.blog.mapper.BlogDataMapper;
import edu.iis.mto.blog.services.BlogService;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogManagerTest {
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private BlogPostRepository blogPostRepository;
    @MockBean
    private LikePostRepository likePostRepository;

    @Autowired
    private BlogDataMapper dataMapper;

    @Autowired
    private BlogService blogService;

    @Captor
    private ArgumentCaptor<User> userParam;
    @Captor
    private ArgumentCaptor<BlogPost> postParam;
    @Captor
    private ArgumentCaptor<LikePost> likeParam;

    @Test
    public void creatingNewUserShouldSetAccountStatusToNEW() {
        User user = createUser();
        assertThat(user.getAccountStatus(), Matchers.equalTo(AccountStatus.NEW));
    }

    @Test(expected = DomainError.class)
    public void checkIfUserWithNewStatusCanLikePost() {
        User liker = createUser();
        User postman = createPostman();
        BlogPost blogPost = createBlogPost(postman.getId());
        blogService.addLikeToPost(liker.getId(), blogPost.getId());
    }

    @Test(expected = DomainError.class)
    public void checkIfUserWithRemovedStatusCanLikePost(){
        User liker = createUser();
        liker.setAccountStatus(AccountStatus.REMOVED);
        User postman = createPostman();
        BlogPost blogPost = createBlogPost(postman.getId());
        blogService.addLikeToPost(liker.getId(), blogPost.getId());
    }

    @Test
    public void checkIfUserWithConfirmedStatusCanLikePost(){
        User liker = createUser();
        liker.setAccountStatus(AccountStatus.CONFIRMED);
        User postman = createPostman();
        when(userRepository.findById(liker.getId())).thenReturn(Optional.of(liker));
        when(userRepository.findById(postman.getId())).thenReturn(Optional.of(postman));

        BlogPost blogPost = createBlogPost(postman.getId());
        when(blogPostRepository.findById(blogPost.getId())).thenReturn(Optional.of(blogPost));

        assertTrue(blogService.addLikeToPost(liker.getId(), blogPost.getId()));
        verify(likePostRepository).save(likeParam.capture());
        LikePost likePost = likeParam.getValue();
        assertThat(likePost.getUser(), Matchers.equalTo(liker));
        assertThat(likePost.getPost(), Matchers.equalTo(blogPost));
    }

    private User createUser(){
        blogService.createUser(new UserRequest("John", "Steward", "john@domain.com"));
        verify(userRepository).save(userParam.capture());
        return userParam.getValue();
    }

    private User createPostman(){
        User postman = new User();
        postman.setFirstName("Kate");
        postman.setLastName("Jackson");
        postman.setEmail("kate@domain.com");
        postman.setAccountStatus(AccountStatus.CONFIRMED);
        postman.setId(20L);
        return postman;
    }

    private BlogPost createBlogPost(Long user_id){
        blogService.createPost(user_id, new PostRequest());
        verify(blogPostRepository).save(postParam.capture());
        return postParam.getValue();
    }
}
