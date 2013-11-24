package net.iaeste.iws.ejb.util;

import net.iaeste.iws.api.constants.IWSConstants;

import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: fili
 * Date: 24.11.13
 * Time: 12:42
 * To change this template use File | Settings | File Templates.
 */
public class SettingJndiProperties {
    public static Properties getProperties() {
        Properties properties = new Properties();

        properties.setProperty("smtpAddress", "localhost");
        properties.setProperty("smtpPort", "25");
        properties.setProperty("iw4BaseUrl", IWSConstants.BASE_URL);
        properties.setProperty("sendingEmailAddress", IWSConstants.IWS_EMAIL_SENDER);
        properties.setProperty("publicMailAddress", IWSConstants.PUBLIC_EMAIL_ADDRESS);
        properties.setProperty("privateMailAddress", IWSConstants.PRIVATE_EMAIL_ADDRESS);

        return properties;
    }
}
