
package com.bo.rest.modelos;

/**
 *
 * @author IGNACIO
 */
public class Token {

    private String apiKey;
    private String password;
    private String deviceId;

    //Partner
    private String uniqueCustomerIdentifier = "";
    private String email = "";

    //CashPoint
    private String externalDeviceId = null;

    public Token(String apiKey, String password, String deviceId) {
        this.apiKey = apiKey;
        this.password = password;
        this.deviceId = deviceId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getUniqueCustomerIdentifier() {
        return uniqueCustomerIdentifier;
    }

    public void setUniqueCustomerIdentifier(String uniqueCustomerIdentifier) {
        this.uniqueCustomerIdentifier = uniqueCustomerIdentifier;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExternalDeviceId() {
        return externalDeviceId;
    }

    public void setExternalDeviceId(String externalDeviceId) {
        this.externalDeviceId = externalDeviceId;
    }

}
