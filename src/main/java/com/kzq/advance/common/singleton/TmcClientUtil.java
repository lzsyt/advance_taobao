package com.kzq.advance.common.singleton;

import com.taobao.api.internal.tmc.TmcClient;

public class TmcClientUtil {

    private static TmcClientUtil tmcClientUtil = new TmcClientUtil();

    public static TmcClientUtil getTmcClientUtil() {
        return tmcClientUtil;
    }

    private TmcClientUtil() {
        super();
    }

    private TmcClient client = new TmcClient("25500416", "25720ff4e7b9f8c5cfe95827c7e35479", "default");


    public  TmcClient getClient() {
        return client;
    }

}
