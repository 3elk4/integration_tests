package edu.iis.mto.blog.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import edu.iis.mto.blog.api.request.PostRequest;
import edu.iis.mto.blog.api.request.UserRequest;
import edu.iis.mto.blog.domain.model.BlogPost;
import edu.iis.mto.blog.domain.model.User;
import edu.iis.mto.blog.dto.PostData;
import edu.iis.mto.blog.dto.UserData;

@Component
public class BlogDataMapperImpl implements BlogDataMapper {

    private final ModelMapper mapper = new ModelMapper();

    public BlogDataMapperImpl() {
        configureMapper();
    }

    @Override
    public User mapToEntity(UserRequest userRequest) {
        return mapper.map(userRequest, User.class);
    }

    @Override
    public BlogPost mapToEntity(PostRequest postRequest) {
        return mapper.map(postRequest, BlogPost.class);
    }

    @Override
    public UserData mapToDto(User user) {
        return mapper.map(user, UserData.class);
    }

    @Override
    public PostData mapToDto(BlogPost blogPost) {
        return mapper.map(blogPost, PostData.class);
    }

    private void configureMapper() {
        // TypeMap<BlogPost, PostData> typeMap = mapper.createTypeMap(BlogPost.class, PostData.class);
        // typeMap.addMappings(mapper -> mapper.map(blogPost -> blogPost.getLikes()
        // .size(),
        // // PostData::setLikesCount));
        // PropertyMap<BlogPost, PostData> postMap = new PropertyMap<BlogPost, PostData>() {
        //
        // @Override
        // protected void configure() {
        // map().setLikesCount(source.getLikes()
        // .size());
        //
        // }
        // };
        // mapper.addMappings(postMap);
    }

}
