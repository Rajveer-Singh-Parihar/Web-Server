package com.simpleserver.server;
import java.io.*;
import java.net.*;
public class HttpRequestProcessor
{
private Socket client;
HttpRequestProcessor(Socket client)
{
    this.client = client;
    run();
}
public void run()
{
try
{
System.out.println(" WEB REQUEST ARRIVED");
InputStream inputStream;
InputStreamReader inputStreamReader;
BufferedReader bufferedReader;
inputStream = this.client.getInputStream();
inputStreamReader = new InputStreamReader(inputStream);
bufferedReader = new  BufferedReader(inputStreamReader);
String line;
 System.out.println("Reading Infrastructure created");
String requestPath = null;
while(true)
            {
                line = bufferedReader .readLine();
                if(line == null || line.equals(" ")) break;
                if(line.toUpperCase().startsWith(" GET "));
                {
                    String pcs[];
                    pcs = line.split(" ");
                    requestPath = pcs[1];
                }
                System.out.println(line);
            }
if(requestPath == null)
            {
                client.close();
                return;
            }
            String fileToServe = " ";
if(requestPath.equals("/"))
            {
if(new File("web-app/index.htm").exists())
                {
                    fileToServe = "web-app/index.htm";
                } 
else if(new File("web-app/index.html").exists())
                {
                    fileToServe = "web-app/index.html";
                }
else
                {
                    int indexofQuestionMark = requestPath.indexOf("?");
if(indexofQuestionMark != -1){
                        fileToServe = "web-app"+requestPath.substring(0, indexofQuestionMark);
}
else
                    {
                        fileToServe = "web-app"+requestPath;
                    }
                }
                OutputStream outputStream=client.getOutputStream();
                //code to read https request data
                System.out.println("preparing response");
                StringBuilder responseHeaderBuilder = new StringBuilder();
                String responseHeader;
                byte responseHeaderBytes[];
                System.out.println("{"+fileToServe+"}");
if(fileToServe.equals(" "))
                {
                    // this means path index.htm and index.html is not found
                    responseHeaderBuilder.append("HTTP/1.1 404 \r\n \r\n");
                    responseHeader = responseHeaderBuilder.toString();
                    responseHeaderBytes = responseHeader.getBytes();
                    outputStream.write(responseHeaderBytes);
                    outputStream.flush();
                    client.close();
                }
                File file = new File(fileToServe);
                if(file.exists()==false)
{
                    responseHeaderBuilder.append("HTTP/1.1 404 \r\n\r\n");
                    responseHeader = responseHeaderBuilder.toString();
                    responseHeaderBytes = responseHeader.getBytes();
                    outputStream.write(responseHeaderBytes);
                    outputStream.flush();
                    client.close();
                }
                //the file to serve exists it may be huge
                long contentLength = file.length();
                // lets determine mime type
                System.out.println(fileToServe+","+contentLength);
                String contentType = URLConnection.guessContentTypeFromName(file.getName());
                responseHeaderBuilder.append("HTTP/1.1 200\r\n");
                responseHeaderBuilder.append("content-type :"+contentType+"\r\n");
                responseHeaderBuilder.append("content -length:"+contentLength+"\r\n\r\n");
                responseHeader=responseHeaderBuilder.toString();
                responseHeaderBytes=responseHeader.getBytes();
                outputStream.write(responseHeaderBytes);
                outputStream.flush();

                FileInputStream fileInputStream = new FileInputStream(fileToServe);
                byte buffer[] = new byte[4096];
                long totalBytesFetched;
                int bytesFetched;
                totalBytesFetched=0;
                while(totalBytesFetched<contentLength)
{
                    bytesFetched=fileInputStream.read(buffer);
                    outputStream.write(buffer,0,bytesFetched);
                    outputStream.flush();
                    totalBytesFetched=totalBytesFetched+bytesFetched;
                }
                fileInputStream.close();
                this.client.close(); // socket closed
            }
        }catch(Exception e)
            {
                System.out.println(e);
            }
    }
}
