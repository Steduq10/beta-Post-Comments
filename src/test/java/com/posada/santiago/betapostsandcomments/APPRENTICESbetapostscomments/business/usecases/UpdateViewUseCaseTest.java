package com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.usecases;

import co.com.sofka.domain.generic.DomainEvent;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.DomainViewRepository;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.EventBus;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.model.CommentViewModel;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.model.PostViewModel;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.domain.events.CommentAdded;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.domain.events.PostCreated;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UpdateViewUseCaseTest {

    @Mock
    private EventBus busMock;

    @Mock
    private DomainViewRepository repositoryMock;

    @InjectMocks
    private ViewUpdater updaterMock;

    private UpdateViewUseCase usecaseMock;

    @BeforeEach
    void init(){
        usecaseMock = new UpdateViewUseCase(this.updaterMock);
    }

    @Test
    @DisplayName("updateViewUseCasePostCreatedTest")
    void updateViewUseCasePostCreatedTest(){

        //Arrange
        PostCreated postCreated = new PostCreated(
          "Testing post",
          "Steven Duque"
        );

        PostViewModel postViewModel = new PostViewModel(
            "1",
                "Steven Duque",
                "Testing post",
                new ArrayList<>()
        );

        BDDMockito
                .when(this.repositoryMock.saveNewPost(ArgumentMatchers.any(PostViewModel.class)))
                .thenReturn(Mono.just(postViewModel));


        //Act
        this.usecaseMock.accept(postCreated);


        //Assert
        BDDMockito.verify(this.busMock, BDDMockito.times(1))
                .publishPostCreated(ArgumentMatchers.any(PostViewModel.class));

        BDDMockito.verify(this.repositoryMock, BDDMockito.times(1))
                .saveNewPost(ArgumentMatchers.any(PostViewModel.class));

    }

    @Test
    @DisplayName("updateViewUseCaseCommentAddedTest")
    void updateViewUseCaseCommentAddedTest(){

        //Arrange

        CommentAdded commentAdded = new CommentAdded(
            "1",
                "Steven Duque",
                "Test is working"
        );


        CommentViewModel commentViewModel = new CommentViewModel(
            "1",
                "1",
                "Steven Duque",
                "Test is working"
        );

        PostViewModel postViewModel = new PostViewModel(
                "1",
                "Steven Duque",
                "Testing post",
                new ArrayList<>()
        );

        BDDMockito
                .when(this.repositoryMock.addCommentToPost(ArgumentMatchers.any(CommentViewModel.class)))
                .thenReturn(Mono.just(postViewModel));


        //Act
        this.usecaseMock.accept(commentAdded);


        //Assert
        BDDMockito.verify(this.busMock, BDDMockito.times(1))
                .publishCommentAdded(ArgumentMatchers.any(CommentViewModel.class));

        BDDMockito.verify(this.repositoryMock, BDDMockito.times(1))
                .addCommentToPost(ArgumentMatchers.any(CommentViewModel.class));

    }

}