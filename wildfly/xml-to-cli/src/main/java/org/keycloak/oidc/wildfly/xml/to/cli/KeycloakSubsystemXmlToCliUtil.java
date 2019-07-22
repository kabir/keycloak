package org.keycloak.oidc.wildfly.xml.to.cli;

import org.jboss.as.controller.Extension;
import org.keycloak.subsystem.adapter.extension.KeycloakExtension;
import org.keycloak.subsystem.adapter.saml.extension.KeycloakSamlExtension;
import org.wildfly.util.xml.to.cli.WildFlyXmlToCli;

/**
 * @author <a href="mailto:kabir.khan@jboss.com">Kabir Khan</a>
 */
public class KeycloakSubsystemXmlToCliUtil {
    public String oidcXmlToCli(String xmlSnippet) throws Exception {
        return xmlToCLi(xmlSnippet, KeycloakExtension.NAMESPACE, KeycloakExtension.SUBSYSTEM_NAME, new KeycloakExtension());
    }

    public String samlXmlToCli(String xmlSnippet) throws Exception {
        return xmlToCLi(xmlSnippet, KeycloakSamlExtension.NAMESPACE, KeycloakSamlExtension.SUBSYSTEM_NAME, new KeycloakSamlExtension());
    }

    private String xmlToCLi(String xmlSnippet, String namespace, String subsystemName, Extension extension) throws Exception {
        String xml = "<subsystem xmlns=\"" + namespace + "\">" +
                xmlSnippet +
                "</subsystem>";
        WildFlyXmlToCli converter = WildFlyXmlToCli.builder()
                .setExtension(extension)
                .setSubsystemName(subsystemName)
                .setXml(xml)
                .build();
        return converter.convertXmlToCli();
    }
}
