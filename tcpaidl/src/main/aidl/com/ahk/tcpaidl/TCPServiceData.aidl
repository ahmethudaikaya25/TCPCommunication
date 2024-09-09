package com.ahk.tcpaidl;

import com.ahk.tcpaidl.TCPServiceDataCallback;

interface TCPServiceData {
    void messageReceive(TCPServiceDataCallback onMessageReceived);
}