/*
* Copyright (c) 2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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
package org.wso2.developerstudio.codenvy.ext.registry.client;

import com.codenvy.ide.api.extension.Extension;
import com.codenvy.ide.api.notification.NotificationManager;
import com.codenvy.ide.api.ui.wizard.ProjectTypeWizardRegistry;
import com.codenvy.ide.api.ui.wizard.ProjectWizard;
import com.google.gwt.core.client.ScriptInjector;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import org.wso2.developerstudio.codenvy.core.shared.CoreExtConstants;
import org.wso2.developerstudio.codenvy.ext.registry.client.wizard.maven.MavenSettingsPagePresenter;
import org.wso2.developerstudio.codenvy.ext.registry.client.wizard.resource.ResourceCreationPagePresenter;
import org.wso2.developerstudio.codenvy.ext.registry.shared.RegistryExtConstants;

@Singleton
@Extension(title = CoreExtConstants.EXT_NAME_PREFIX + RegistryExtConstants.EXT_NAME,
                                            version = RegistryExtConstants.EXT_VERSION)
public class RegistryExtension {

    @Inject
    public RegistryExtension(NotificationManager notificationManager,
                             RegistryExtensionResources resources,
                             ProjectTypeWizardRegistry wizardRegistry,
                             Provider<MavenSettingsPagePresenter> mavenSettingsPagePresenter,
                             Provider<ResourceCreationPagePresenter> resourceCreationPagePresenter) {

        ProjectWizard wizard = new ProjectWizard(notificationManager);
        wizard.addPage(mavenSettingsPagePresenter);
        wizard.addPage(resourceCreationPagePresenter);
        wizardRegistry.addWizard(RegistryExtConstants.WSO2_REGISTRY_PROJECT_ID, wizard);

//        ScriptInjector.fromString(resources.jqueryLib().getText()).inject();
//        ScriptInjector.fromString(resources.jqueryUILib().getText()).inject();
//        ScriptInjector.fromString(resources.jsPlumbLib().getText()).inject();

    }
}