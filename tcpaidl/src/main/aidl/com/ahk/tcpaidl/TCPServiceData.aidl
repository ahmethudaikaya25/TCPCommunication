package com.ahk.tcpaidl;

import com.ahk.tcpcommunication.TCPServiceDataCallback;

interface TCPServiceData {
    void messageReceive(TCPServiceDataCallback onMessageReceived);
}