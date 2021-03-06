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
package org.jboss.pnc.rest.endpoints;

import java.lang.invoke.MethodHandles;

import javax.inject.Inject;

import org.jboss.pnc.dto.Build;
import org.jboss.pnc.dto.BuildConfiguration;
import org.jboss.pnc.dto.BuildConfigurationRef;
import org.jboss.pnc.dto.GroupBuild;
import org.jboss.pnc.dto.GroupConfiguration;
import org.jboss.pnc.dto.GroupConfigurationRef;
import org.jboss.pnc.dto.requests.GroupBuildRequest;
import org.jboss.pnc.dto.response.Page;
import org.jboss.pnc.facade.providers.api.BuildConfigurationProvider;
import org.jboss.pnc.facade.providers.api.BuildProvider;
import org.jboss.pnc.facade.providers.api.GroupBuildProvider;
import org.jboss.pnc.facade.providers.api.GroupConfigurationProvider;
import org.jboss.pnc.rest.api.endpoints.GroupConfigurationEndpoint;
import org.jboss.pnc.rest.api.parameters.BuildsFilterParameters;
import org.jboss.pnc.rest.api.parameters.GroupBuildParameters;
import org.jboss.pnc.rest.api.parameters.PageParameters;
import static org.jboss.pnc.rest.endpoints.BuildEndpointImpl.toBuildPageInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author Honza Brázdil &lt;jbrazdil@redhat.com&gt;
 */
@ApplicationScoped
public class GroupConfigurationEndpointImpl extends AbstractEndpoint<GroupConfiguration, GroupConfigurationRef> implements GroupConfigurationEndpoint{
    
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    
    @Inject
    private GroupConfigurationProvider provider;

    @Inject
    private BuildConfigurationProvider buildConfigurationProvider;

    @Inject
    private BuildProvider buildProvider;

    @Inject
    private GroupBuildProvider groupBuildProvider;

    public GroupConfigurationEndpointImpl() {
        super(GroupConfiguration.class);
    }

    @Override
    protected GroupConfigurationProvider provider() {
        return provider;
    }

    @Override
    public Page<GroupConfiguration> getAll(PageParameters pageParams) {
        return super.getAll(pageParams);
    }

    @Override
    public GroupConfiguration createNew(GroupConfiguration groupConfiguration) {
        return super.create(groupConfiguration);
    }

    @Override
    public GroupConfiguration getSpecific(int id) {
        return super.getSpecific(id);
    }

    @Override
    public void update(int id, GroupConfiguration buildConfigurationSet) {
        super.update(id, buildConfigurationSet);
    }

    @Override
    public void deleteSpecific(int id) {
        super.delete(id);
    }

    @Override
    public GroupBuild trigger(int id, GroupBuildParameters buildParams, GroupBuildRequest request, String callbackUrl) {
        throw new UnsupportedOperationException("Not supported yet."); //TODO
    }

    @Override
    public Page<BuildConfiguration> getConfigurations(int id, PageParameters pageParams) {
        return buildConfigurationProvider.getBuildConfigurationsForGroup(pageParams.getPageIndex(),
                pageParams.getPageSize(),
                pageParams.getSort(),
                pageParams.getQ(),
                id);
    }

    @Override
    public void addConfiguration(int id, BuildConfigurationRef buildConfig) {
        provider.addConfiguration(id, buildConfig.getId());
    }

    @Override
    public void removeConfiguration(int id, int configId) {
        provider.removeConfiguration(id, configId);
    }

    @Override
    public Page<Build> getBuilds(int id, PageParameters pageParams, BuildsFilterParameters filterParams) {
        return buildProvider.getBuildsForGroupConfiguration(toBuildPageInfo(pageParams, filterParams), id);
    }

    @Override
    public Page<GroupBuild> getAllGroupBuilds(int id, PageParameters pageParams) {
        return groupBuildProvider.getGroupBuilds(pageParams.getPageIndex(),
                pageParams.getPageSize(),
                pageParams.getSort(),
                pageParams.getQ(),
                id);
    }

}
