package com.ahk.tcpaidl;

oneway interface TCPServiceDataCallback {
    void onMessageReceived(in byte[] data);

    void onErrorOccurred(String error);
}