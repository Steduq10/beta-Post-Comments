package com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.usecases;


import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.DomainViewRepository;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.EventBus;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.model.CommentViewModel;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.model.PostViewModel;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.generic.DomainUpdater;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.domain.events.CommentAdded;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.domain.events.PostCreated;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class ViewUpdater extends DomainUpdater {

    //Complete the implementation of the view updater
    private final DomainViewRepository repository;

    private final EventBus bus;

    public ViewUpdater(DomainViewRepository repository, EventBus bus){
        this.repository = repository;
        this.bus = bus;

        listen((PostCreated postcreated) -> {
            PostViewModel post = new PostViewModel(
                    postcreated.aggregateRootId(),
                    postcreated.getAuthor(),
                    postcreated.getTitle(),
                    new ArrayList<>()
            );
            bus.publishPostCreated(post);
            repository.saveNewPost(post).subscribe();
        });

        listen((CommentAdded commentAdded) -> {
            CommentViewModel comment = new CommentViewModel(
                    commentAdded.getId(),
                    commentAdded.aggregateRootId(),
                    commentAdded.getAuthor(),
                    commentAdded.getContent()
            );
            bus.publishCommentAdded(comment);
            repository.addCommentToPost(comment).subscribe();
        });

    }
}
