package com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.usecases;

import co.com.sofka.domain.generic.DomainEvent;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.application.adapters.repository.MongoViewRepository;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.model.PostViewModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BringAllPostsUseCaseTest {

    @Mock
    private MongoViewRepository repositoryMock;

    @InjectMocks
    private BringAllPostsUseCase useCaseMock;

    @Test
    @DisplayName("BringAllPostsUseCaseTest. Should Return a List of PostViewModels from the Database")
    void bringAllPostsUseCaseTest(){

        //Arrange
        PostViewModel postViewModel = new PostViewModel(
                "1",
                "Santiago Sierra",
                "First Post",
                new ArrayList<>()
        );

        BDDMockito
                .when(this.repositoryMock.findAllPosts())
                .thenReturn(Flux.just(postViewModel));

        //Act
        Mono<List<PostViewModel>> posts = this.useCaseMock.get()
                .collectList();

        //Assert
        StepVerifier.create(posts)
                .expectNextMatches(foundPosts ->
                    foundPosts.get(0) instanceof PostViewModel)
                .verifyComplete();

        BDDMockito.verify(this.repositoryMock)
                        .findAllPosts();

    }



}