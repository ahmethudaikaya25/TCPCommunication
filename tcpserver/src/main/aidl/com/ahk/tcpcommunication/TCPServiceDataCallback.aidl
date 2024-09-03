package com.ahk.tcpcommunication;

oneway interface TCPServiceDataCallback {
    void onMessageReceived(in byte[] data);

    void onErrorOccurred(String error);
}