package com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.usecases;

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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BringPostByIdTest {

    @Mock
    private MongoViewRepository repositoryMock;

    @InjectMocks
    private BringPostById useCaseMock;

    @Test
    @DisplayName("BringPostByIdTest. Should Return a PostView Model from the Database")
    void bringPostByIdTest(){

        PostViewModel postViewModel = new PostViewModel(
                "1",
                "Santiago Sierra",
                "First Post",
                new ArrayList<>()
        );

        BDDMockito
                .when(this.repositoryMock.findByAggregateId(ArgumentMatchers.anyString()))
                .thenReturn(Mono.just(postViewModel));


        //Act
        Mono<PostViewModel> post = this.useCaseMock.apply("1");

        //Assert
        StepVerifier.create(post)
                .expectNextMatches(foundPost ->
                        foundPost.getAuthor().equals(postViewModel.getAuthor()))
                .verifyComplete();

        BDDMockito.verify(this.repositoryMock)
                .findByAggregateId(ArgumentMatchers.anyString());

    }

}