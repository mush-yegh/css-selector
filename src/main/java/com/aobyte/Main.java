package com.aobyte;

import java.io.*;

public class Main {
//    static ClassLoader classLoader = getClass().getClassLoader();
//    private static File fileDir = new File(classLoader.getResource("index.html").getFile());
    //private static String fileToRead = "index.html";

    public static void main(String[] args){
        StringBuilder html = new StringBuilder();
        try{
            File fileDir = new File("/home/java-duke/myJava/projects/css-selector/src/main/resources/index.html");

            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(new FileInputStream(fileDir), "UTF8"));

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                html.append(line+"\n");
            }

            bufferedReader.close();
            System.out.println(html.toString());
        }catch (UnsupportedEncodingException e){
            System.out.println("1 "+e.getMessage());
        }catch (IOException e){
            System.out.println("2 "+e.getMessage());
        }catch (Exception e){
            System.out.println("3 "+e.getMessage());
        }

        //getTagStartIndex("body", html);
        //getTagEndIndex("body", html);
        String strBody = html.substring(getTagStartIndex("body", html), getTagEndIndex("body", html));
        System.out.println(strBody);
    }

    private static int getTagStartIndex(String tagName, StringBuilder html){//NullPointerException − if param is null.
        System.out.println("index of " + "<"+tagName+">" + " is: "+ html.indexOf("<"+tagName+">"));
        return html.indexOf("<"+tagName+">");
    }
    private static int getTagEndIndex(String tagName, StringBuilder html){
        System.out.println("index of " + "<"+tagName+">" + " is: "+ html.indexOf("</"+tagName+">"));
        return html.indexOf("</"+tagName+">")+("</"+tagName+">").length();
    }
}
