// AuthService.java
package com.telegram.core.service;

import com.telegram.core.config.TdlibClient;
import com.telegram.core.session.SessionManager;
import org.drinkless.tdlib.Client;
import org.drinkless.tdlib.TdApi;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    static TdApi.Object result;
    private final TdlibClient tdlibClient;
    private final SessionManager sessionManager;

    public AuthService(TdlibClient tdlibClient, SessionManager sessionManager) {
        this.tdlibClient = tdlibClient;
        this.sessionManager = sessionManager;
    }

    public void sendPhoneNumber(String phoneNumber) {
        Client client = tdlibClient.getClient();
        client.send(new TdApi.SetAuthenticationPhoneNumber(phoneNumber, null), new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.Object object) {
                if (object.getConstructor() == TdApi.Ok.CONSTRUCTOR) {
                    sessionManager.set("userId", new SessionManager.Session(phoneNumber, false));
                } else {
                    System.err.println("Failed to send phone: " + object);
                }
            }
        });
    }

    public void checkCode(String code) {
        Client client = tdlibClient.getClient();
        client.send(new TdApi.CheckAuthenticationCode(code), new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.Object object) {
                if (object.getConstructor() == TdApi.Ok.CONSTRUCTOR) {
                    SessionManager.Session session = sessionManager.get("userId");
                    sessionManager.set("userId", new SessionManager.Session(session.phoneNumber(), true));
                } else {
                    System.err.println("Code verification failed: " + object);
                }
            }
        });
    }

    public boolean isAuthorized(String userId) {
        return sessionManager.isLoggedIn(userId);
    }

    public TdApi.Object logout() {
        Client client = tdlibClient.getClient();
        client.send(new TdApi.LogOut(), new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.Object object) {
                result = object;
            }
        });
        return result;
    }

    public TdApi.Object close() {
        Client client = tdlibClient.getClient();
        client.send(new TdApi.Close(), new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.Object object) {
                result = object;
            }
        });
        return result;
    }



    public void setParam() {
        Client client = tdlibClient.getClient();
        TdApi.SetTdlibParameters request = new TdApi.SetTdlibParameters();
        request.databaseDirectory = "tdlib";
        request.useMessageDatabase = true;
        request.useSecretChats = true;
        request.apiId = 94575;
        request.apiHash = "a3406de8d171bb422bb6ddf3bbd800e2";
        request.systemLanguageCode = "en";
        request.deviceModel = "Desktop";
        request.applicationVersion = "1.0";
        client.send(request, new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.Object object) {
                if (object.getConstructor() == TdApi.Ok.CONSTRUCTOR) {

                }
            }
        });
    }
}