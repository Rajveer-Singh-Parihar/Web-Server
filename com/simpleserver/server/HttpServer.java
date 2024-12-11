package com.simpleserver.server;
import java.io.*;
import java.net.*;
public class HttpServer
{

    public static void main(String[] args) {
        try
        {
        int port = Integer.parseInt(args[0]);
        ServerSocket serverSocket;
        serverSocket =new ServerSocket(port);
        
        System.out.println("HTTP WEB SERVER IS READY TO ACCEPT REQUEST ON "+port);
        Socket client;
        HttpRequestProcessor httpRequestProcessor;
             while(true)
             {
                client=serverSocket.accept();
                httpRequestProcessor=new HttpRequestProcessor(client);
             }
        }catch(Exception e)
        {
            System.out.println(e);
        }
    }
}