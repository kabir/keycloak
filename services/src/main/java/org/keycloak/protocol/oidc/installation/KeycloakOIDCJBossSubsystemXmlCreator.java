package org.keycloak.protocol.oidc.installation;

import java.net.URI;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.keycloak.models.ClientModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;

/**
 * @author <a href="mailto:kabir.khan@jboss.com">Kabir Khan</a>
 */
public class KeycloakOIDCJBossSubsystemXmlCreator {
    public String generateXml(KeycloakSession session, RealmModel realm, ClientModel client, URI baseUri) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<secure-deployment name=\"WAR MODULE NAME.war\">\n");
        buffer.append("    <realm>").append(realm.getName()).append("</realm>\n");
        buffer.append("    <auth-server-url>").append(baseUri.toString()).append("</auth-server-url>\n");
        if (client.isBearerOnly()){
            buffer.append("    <bearer-only>true</bearer-only>\n");

        } else if (client.isPublicClient()) {
            buffer.append("    <public-client>true</public-client>\n");
        }
        buffer.append("    <ssl-required>").append(realm.getSslRequired().name()).append("</ssl-required>\n");
        buffer.append("    <resource>").append(client.getClientId()).append("</resource>\n");

        if (KeycloakOIDCClientInstallation.showVerifyTokenAudience(client)) {
            buffer.append("    <verify-token-audience>true</verify-token-audience>\n");
        }

        String cred = client.getSecret();
        if (KeycloakOIDCClientInstallation.showClientCredentialsAdapterConfig(client)) {
            Map<String, Object> adapterConfig = KeycloakOIDCClientInstallation.getClientCredentialsAdapterConfig(session, client);
            for (Map.Entry<String, Object> entry : adapterConfig.entrySet()) {
                buffer.append("    <credential name=\"" + entry.getKey() + "\">");

                Object value = entry.getValue();
                if (value instanceof Map) {
                    buffer.append("\n");
                    Map<String, Object> asMap = (Map<String, Object>) value;
                    for (Map.Entry<String, Object> credEntry : asMap.entrySet()) {
                        buffer.append("        <" + credEntry.getKey() + ">" + credEntry.getValue().toString() + "</" + credEntry.getKey() + ">\n");
                    }
                    buffer.append("    </credential>\n");
                } else {
                    buffer.append(value.toString()).append("</credential>\n");
                }
            }
        }
        if (client.getRoles().size() > 0) {
            buffer.append("    <use-resource-role-mappings>true</use-resource-role-mappings>\n");
        }
        buffer.append("</secure-deployment>\n");
        return buffer.toString();
    }
}
