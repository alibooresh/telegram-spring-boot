package com.telegram.core.service;

import com.telegram.core.config.TdlibClient;
import com.telegram.core.session.SessionManager;
import org.drinkless.tdlib.Client;
import org.drinkless.tdlib.TdApi;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final TdlibClient tdlibClient;
    private final SessionManager sessionManager;
    static TdApi.Object result;
    private static boolean isFound=false;


    public UserService(TdlibClient tdlibClient, SessionManager sessionManager) {
        this.tdlibClient = tdlibClient;
        this.sessionManager = sessionManager;
    }

    public TdApi.User getMe() {
        TdApi.User user = null;
        Client client = tdlibClient.getClient();
        client.send(new TdApi.GetMe(),
                new Client.ResultHandler() {
                    @Override
                    public void onResult(TdApi.Object object) {
                        result = object;
                    }
                });
        user = (TdApi.User) result;
        return user;
    }

    public TdApi.User getUserByNumber(String number) {
        TdApi.User user = null;
        Client client = tdlibClient.getClient();
        client.send(new TdApi.GetContacts(), new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.Object object) {
                TdApi.Users users = (TdApi.Users) object;
                for (long userId : users.userIds) {
                    client.send(new TdApi.GetUser(userId), new Client.ResultHandler() {
                        @Override
                        public void onResult(TdApi.Object object) {
                            TdApi.User user = (TdApi.User) object;
                            if (user.phoneNumber.equals(number)) {
                                result=user;
                                isFound=true;
                            }
                        }
                    });
                    if(isFound) {
                        break;
                    }
                }
            }
        });
        user = (TdApi.User) result;
        return user;
    }


    public TdApi.Object getUserFullInfo(Long userId) {
        TdApi.User user = null;
        Client client = tdlibClient.getClient();
        client.send(new TdApi.GetUserFullInfo(userId),
                new Client.ResultHandler() {
                    @Override
                    public void onResult(TdApi.Object object) {
                        result = object;
                    }
                });
        return result;
    }

    public TdApi.Object getUserLink() {
        TdApi.User user = null;
        Client client = tdlibClient.getClient();
        client.send(new TdApi.GetUserFullInfo(),
                new Client.ResultHandler() {
                    @Override
                    public void onResult(TdApi.Object object) {
                        result = object;
                    }
                });
        return result;
    }

    public TdApi.Object getContacts() {
        TdApi.User user = null;
        Client client = tdlibClient.getClient();
        client.send(new TdApi.GetUserFullInfo(),
                new Client.ResultHandler() {
                    @Override
                    public void onResult(TdApi.Object object) {
                        result = object;
                    }
                });
        return result;
    }

    public TdApi.Object getApplicationConfig() {
        TdApi.User user = null;
        Client client = tdlibClient.getClient();
        client.send(new TdApi.GetApplicationConfig(),
                new Client.ResultHandler() {
                    @Override
                    public void onResult(TdApi.Object object) {
                        result = object;
                    }
                });
        return result;
    }

    public TdApi.Object getActiveSessions() {
        TdApi.User user = null;
        Client client = tdlibClient.getClient();
        client.send(new TdApi.GetActiveSessions(),
                new Client.ResultHandler() {
                    @Override
                    public void onResult(TdApi.Object object) {
                        result = object;
                    }
                });
        return result;
    }

    public TdApi.Object getTimeZones() {
        TdApi.User user = null;
        Client client = tdlibClient.getClient();
        client.send(new TdApi.GetTimeZones(),
                new Client.ResultHandler() {
                    @Override
                    public void onResult(TdApi.Object object) {
                        result = object;
                    }
                });
        return result;
    }

    public TdApi.Object getAccountTtl() {
        TdApi.User user = null;
        Client client = tdlibClient.getClient();
        client.send(new TdApi.GetAccountTtl(),
                new Client.ResultHandler() {
                    @Override
                    public void onResult(TdApi.Object object) {
                        result = object;
                    }
                });
        return result;
    }

    public TdApi.Object getDatabaseStatistics() {
        TdApi.User user = null;
        Client client = tdlibClient.getClient();
        client.send(new TdApi.GetDatabaseStatistics(),
                new Client.ResultHandler() {
                    @Override
                    public void onResult(TdApi.Object object) {
                        result = object;
                    }
                });
        return result;
    }

}
