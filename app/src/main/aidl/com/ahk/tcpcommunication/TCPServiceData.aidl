package com.ahk.tcpcommunication;

import com.ahk.tcpcommunication.TCPServiceDataCallback;

interface TCPServiceData {
    void messageReceive(TCPServiceDataCallback onMessageReceived);
}