/**
 * JBoss, Home of Professional Open Source.
 * Copyright 2014-2019 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.pnc.facade.providers;

import org.jboss.pnc.dto.Project;
import org.jboss.pnc.dto.ProjectRef;
import org.jboss.pnc.facade.mapper.api.ProjectMapper;
import org.jboss.pnc.facade.providers.api.ProjectProvider;
import org.jboss.pnc.facade.rsql.mapper.ProjectRSQLMapper;
import org.jboss.pnc.facade.validation.ConflictedEntryException;
import org.jboss.pnc.spi.datastore.repositories.ProjectRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;

import static org.jboss.pnc.spi.datastore.predicates.ProjectPredicates.withProjectName;

@Stateless
public class ProjectProviderImpl extends AbstractProvider<org.jboss.pnc.model.Project, Project, ProjectRef> implements ProjectProvider {

    @Inject
    public ProjectProviderImpl(ProjectRepository repository, ProjectMapper mapper) {
        super(repository, mapper, org.jboss.pnc.model.Project.class);
    }


    @Override
    protected void validateBeforeSaving(Project projectRest) {

        super.validateBeforeSaving(projectRest);

        org.jboss.pnc.model.Project project = repository.queryByPredicates(withProjectName(projectRest.getName()));

        //don't validate against myself
        if(project != null && !project.getId().equals(projectRest.getId())) {

            throw new ConflictedEntryException(
                    "Project of that name already exists",
                    org.jboss.pnc.model.Project.class,
                    project.getId());
        }
    }

}
