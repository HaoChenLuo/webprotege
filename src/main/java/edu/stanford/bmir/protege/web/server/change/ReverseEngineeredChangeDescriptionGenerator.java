package edu.stanford.bmir.protege.web.server.change;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import edu.stanford.bmir.protege.web.server.change.matcher.ChangeMatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 16/03/16
 */
public class ReverseEngineeredChangeDescriptionGenerator<S> implements ChangeDescriptionGenerator<S> {

    private final String defaultDescription;

    private final List<ChangeMatcher> matchers;

    @Inject
    public ReverseEngineeredChangeDescriptionGenerator(@Assisted String defaultDescription, Set<ChangeMatcher> matchers) {
        this.defaultDescription = defaultDescription;
        this.matchers = new ArrayList<>(matchers);
    }

    @Override
    public String generateChangeDescription(ChangeApplicationResult<S> result) {
        for(ChangeMatcher matcher : matchers) {
            Optional<String> description = matcher.getDescription(result);
            if(description.isPresent()) {
                return description.get();
            }
        }
        return defaultDescription;
    }
}