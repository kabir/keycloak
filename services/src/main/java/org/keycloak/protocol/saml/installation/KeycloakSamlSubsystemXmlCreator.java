package org.keycloak.protocol.saml.installation;

import java.net.URI;

import org.keycloak.models.ClientModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.protocol.saml.SamlClient;

/**
 * @author <a href="mailto:kabir.khan@jboss.com">Kabir Khan</a>
 */
class KeycloakSamlSubsystemXmlCreator {
    String generateXml(KeycloakSession session, RealmModel realm, ClientModel client, URI baseUri) {
        SamlClient samlClient = new SamlClient(client);
        StringBuilder buffer = new StringBuilder();
        buffer.append("<secure-deployment name=\"YOUR-WAR.war\">\n");
        KeycloakSamlClientInstallation.baseXml(session, realm, client, baseUri, samlClient, buffer);
        buffer.append("</secure-deployment>\n");
        return buffer.toString();
    }
}
