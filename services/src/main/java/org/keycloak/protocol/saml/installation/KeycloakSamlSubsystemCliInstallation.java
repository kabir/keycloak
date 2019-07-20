/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
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

package org.keycloak.protocol.saml.installation;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.keycloak.Config;
import org.keycloak.models.ClientModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.models.RealmModel;
import org.keycloak.protocol.ClientInstallationProvider;
import org.keycloak.protocol.saml.SamlClient;
import org.keycloak.protocol.saml.SamlProtocol;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class KeycloakSamlSubsystemCliInstallation implements ClientInstallationProvider {

    @Override
    public Response generateInstallation(KeycloakSession session, RealmModel realm, ClientModel client, URI baseUri) {
        String xml = new KeycloakSamlSubsystemXmlCreator().generateXml(session, realm, client, baseUri);
        return Response.ok(xml, MediaType.TEXT_PLAIN_TYPE).build();
    }

    @Override
    public String getProtocol() {
        return SamlProtocol.LOGIN_PROTOCOL;
    }

    @Override
    public String getDisplayType() {
        return "Keycloak SAML Wildfly/JBoss Subsystem (CLI)";
    }

    @Override
    public String getHelpText() {
        return "Keycloak SAML adapter Wildfly/JBoss subsystem cli commands.  Save this somewhere and install with /server/path/bin/jboss-cli.sh --file=<path to saved file>";
    }

    @Override
    public String getFilename() {
        return "keycloak-saml-subsystem.cli";
    }

    @Override
    public String getMediaType() {
        return MediaType.TEXT_PLAIN;
    }

    @Override
    public boolean isDownloadOnly() {
        return false;
    }

    @Override
    public void close() {

    }

    @Override
    public ClientInstallationProvider create(KeycloakSession session) {
        return this;
    }

    @Override
    public void init(Config.Scope config) {

    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {

    }

    @Override
    public String getId() {
        return "keycloak-saml-subsystem-cli";
    }
}
