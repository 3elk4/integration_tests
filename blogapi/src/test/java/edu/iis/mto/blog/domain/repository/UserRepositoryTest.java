package edu.iis.mto.blog.domain.repository;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import edu.iis.mto.blog.domain.model.AccountStatus;
import edu.iis.mto.blog.domain.model.User;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repository;

    private User user;

    @Before
    public void setUp() {
        user = new User();
        user.setFirstName("Jan");
        user.setLastName("Kowalski");
        user.setEmail("john@domain.com");
        user.setAccountStatus(AccountStatus.NEW);
        repository.deleteAll();
    }

    @Test
    public void shouldFindNoUsersIfRepositoryIsEmpty() {
        List<User> users = repository.findAll();
        assertThat(users, hasSize(0));
    }

    @Test
    public void shouldFindOneUsersIfRepositoryContainsOneUserEntity() {
        User persistedUser = entityManager.persist(user);
        List<User> users = repository.findAll();

        assertThat(users, hasSize(1));
        assertThat(users.get(0)
                        .getEmail(),
                equalTo(persistedUser.getEmail()));
    }

    @Test
    public void shouldStoreANewUser() {

        User persistedUser = repository.save(user);

        assertThat(persistedUser.getId(), notNullValue());
    }

    @Test
    public void shouldFindUserByFirstName() {
        entityManager.persist(user);
        String searchString = "Jan";
        List<User> users = findByString(searchString);
        assertThat(users, hasSize(1));
        assertThat(users.get(0).getFirstName(), equalTo(searchString));
    }

    @Test
    public void shouldFindUserByLastName() {
        entityManager.persist(user);
        String searchString = "Kowalski";
        List<User> users = findByString(searchString);
        assertThat(users, hasSize(1));
        assertThat(users.get(0).getLastName(), equalTo(searchString));
    }

    @Test
    public void shouldFindUserByEmail() {
        entityManager.persist(user);
        String searchString = "john@domain.com";
        List<User> users = findByString(searchString);
        assertThat(users, hasSize(1));
        assertThat(users.get(0).getEmail(), equalTo(searchString));
    }

    @Test
    public void shouldFindNoUser() {
        entityManager.persist(user);
        String searchString = "TestName";
        List<User> users = findByString(searchString);
        assertThat(users, hasSize(0));
    }

    private List<User> findByString(String searchString){
        return repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase(searchString,
                searchString, searchString);
    }
}
