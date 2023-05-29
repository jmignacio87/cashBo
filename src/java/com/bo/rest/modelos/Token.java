package com.bo.rest.modelos;

import com.bo.rest.utils.TypeUtils;

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

    private Integer type = 0;

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

    public Integer getType() {
        return type;
    }

    public void setType(String source) {
        if (source.equals(TypeUtils.CASHPOINT)) {
            this.type = 2;
        } else if (source.equals(TypeUtils.PARTNER)) {
            this.type = 1;
        }
    }

}
