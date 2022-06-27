package com.tplinkdns.tadap.mobilecomputingproject;

import android.util.Log;

import com.tplinkdns.tadap.mobilecomputingproject.Searching.SearchingList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class XmlParser{

    ArrayList<SearchingList> testList;


    String key = "=Niwi7%2B0aHsdTk6Ee1rHwCWVCzC4wSGpiSShsVLL2%2FHFtQI1l00G3Q6%2FvtolM14t5GdIbYxt32o6GY13rAy4yCA%3D%3D";

    String name= "empty"; //이름, facltNm
    String adrStreetName= "empty"; //주소1, addr1
    String adrSpecificName= "empty"; //주소2, addr2
    String telNum= "empty";//전화번호, tel
    String URL= "empty";//홈페이지, homepage
    String additionalFacilities= "empty";//부대시설, sbrsCl
    String animal= "empty";//애완동물 가능 여부, animalCmgCl
    String firstImageUrl= "empty";
    String mapX="empty";
    String mapY="empty";
    String contentId = "empty";
    String doNm = "empty";//시군도 이름
    
    String searchKeyword;

    String Keyword_mapX;//경도
    String Keyword_mapY;//위도

    int size;

    public XmlParser(){
    }

    public XmlParser(String a){
        searchKeyword = a;
    }


    public XmlParser(Double KeyX, Double KeyY){
        Keyword_mapX = KeyX.toString();
        Keyword_mapY = KeyY.toString();
        Keyword_mapX = "&mapX=" + Keyword_mapX;
        Keyword_mapY = "&mapY=" + Keyword_mapY;
    }

    private int lengthKeywordParse(){
        int length = 0;
        String pageNo = "&pageNo=1";
        String deviceType = "&MobileOS=AND";
        String MobileApp = "&MobileApp=AppTest";
//            String keyword = "&keyword=야영장";
        String keyword = "&keyword="+searchKeyword;
        String numOfRows = "&numOfRows=1";
        String queryUrl = "http://api.visitkorea.or.kr/openapi/service/rest/GoCamping/searchList?serviceKey"+key+pageNo+deviceType+MobileApp+keyword+numOfRows;

        try {
            URL url= new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성
            URLConnection t_connection = url.openConnection();
//            t_connection.setReadTimeout(3000);

            InputStream is= url.openStream(); //url위치로 입력스트림 연결
            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부터 xml 입력받기
            String tag;//값을 변경하며
            int position =0;
            xpp.next();
            int eventType= xpp.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT){
                switch(eventType){
                    case XmlPullParser.START_DOCUMENT:
                        Log.d("xmlParser.START_DOCUMENT","StartParsing");

                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();//태그 이름 얻어오기
                        Log.d("xmlParser.START_DOCUMENT","Tag: "+tag);



                        if(tag.equals("item")){

                        }

                        else if(tag.equals("totalCount")) { //이름
                            xpp.next();
                            length=Integer.parseInt(xpp.getText());
                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName();

                        break;
                }
                eventType= xpp.next();

            }

        } catch (Exception e) {
            Log.d("XmlParser.catch","err Occurred");
            Log.w("XmlParser.catch", "err",e);
        }
        return length;
    }

        public void KeywordParse(){
            Log.d("xmlParser","StartKeywordParse");
            int length = lengthKeywordParse();
            testList = new ArrayList<>();
            String pageNo = "&pageNo=1";
            String deviceType = "&MobileOS=AND";
            String MobileApp = "&MobileApp=AppTest";
//            String keyword = "&keyword=야영장";
            String keyword = "&keyword="+searchKeyword;
            String numOfRows = "&numOfRows="+length;
//            String numOfRows = "&numOfRows=10";
            String queryUrl = "http://api.visitkorea.or.kr/openapi/service/rest/GoCamping/searchList?serviceKey"+key+pageNo+deviceType+MobileApp+keyword+numOfRows;

        try {
            URL url= new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성
            URLConnection t_connection = url.openConnection();
//            t_connection.setReadTimeout(3000);

            InputStream is= url.openStream(); //url위치로 입력스트림 연결
            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부터 xml 입력받기
            String tag;//값을 변경하며
            int position =0;
            xpp.next();
            int eventType= xpp.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT){
                switch(eventType){
                    case XmlPullParser.START_DOCUMENT:
                        Log.d("xmlParser.START_DOCUMENT","StartParsing");

                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();//태그 이름 얻어오기
                        Log.d("xmlParser.START_DOCUMENT","Tag: "+tag);



                        if(tag.equals("item")){

                        }

                        else if(tag.equals("facltNm")) { //이름
                            xpp.next();
                            name=xpp.getText();
                            Log.d("xmlParser.START_DOCUMENT",position+"Tag: faclNm: "+name);

                        }

                        else if(tag.equals("addr1")) {  //주소1

                            xpp.next();
                            adrStreetName = xpp.getText();
                            Log.d("xmlParser.START_DOCUMENT",position+"Tag: addr1: "+adrStreetName);
                        }

                        else if(tag.equals("addr2")) {  //주소2

                            xpp.next();
                            adrSpecificName = xpp.getText();
                            Log.d("xmlParser.START_DOCUMENT",position+"Tag: addr2"+adrSpecificName);

                        }
                        else if(tag.equals("tel")) {  //tel

                            xpp.next();
                            telNum = xpp.getText();
                            Log.d("xmlParser.START_DOCUMENT",position+"Tag: telNum: "+ telNum);

                        }
                        else if(tag.equals("homepage")) {  //홈페이지

                            xpp.next();
                            URL = xpp.getText();
                            Log.d("xmlParser.START_DOCUMENT",position+"Tag: hompage: "+URL);

                        }

//                        else if(tag.equals("sbrsCl")) {  //부대시설
//
//                            xpp.next();
//                            additionalFacilities = xpp.getText();
//                            Log.d("hyeon",position+"Tag: sbrsCl: " + additionalFacilities);
//
//                        }

//                        else if(tag.equals("animalCmgCl")) {  //애완동물
//
//                            xpp.next();
//                            animal = xpp.getText();
//                            Log.d("hyeon",position+"Tag: animalCmgCl: "+animal);
//
//                        }
                        else if(tag.equals("firstImageUrl")) {  //이미지 URL

                            xpp.next();
                            firstImageUrl = xpp.getText();
                            Log.d("xmlParser.START_DOCUMENT",position+"Tag: firstImageUrl: "+firstImageUrl);

                        }

                        else if(tag.equals("mapX")) {  //X좌표

                            xpp.next();
                            mapX = xpp.getText();
                            Log.d("xmlParser.START_DOCUMENT",position+"Tag: mapX: "+mapX);

                        }

                        else if(tag.equals("mapY")) {  //Y좌표

                            xpp.next();
                            mapY = xpp.getText();
                            Log.d("xmlParser.START_DOCUMENT",position+"Tag: mapY: "+mapY);

                        }
                        else if(tag.equals("contentId")) {  //Y좌표

                            xpp.next();
                            contentId = xpp.getText();
                            Log.d("xmlParser.START_DOCUMENT",position+"Tag - contentId: "+contentId);

                        }
                        else if(tag.equals("doNm")) {  //시,도 이름

                            xpp.next();
                            doNm = xpp.getText();
                            Log.d("xmlParser.START_DOCUMENT",position+"tag - doNm:  "+doNm);

                        }


                        break;
                    case XmlPullParser.TEXT:


                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName();
                        if(tag.equals("item")) {
                            testList.add(new SearchingList(name, doNm, adrStreetName, adrSpecificName,
                                    telNum, URL, additionalFacilities, animal, firstImageUrl,mapX,mapY, contentId));

                            Log.d("xmlParser.END_TAG",name+ adrStreetName+ adrSpecificName+
                                    telNum+ URL+ additionalFacilities+ animal+ firstImageUrl);
                            Log.d("xmlParser.END_TAG","ParsingSuccess");

                            name = "empty"; //이름, facltNm
                            adrStreetName = "empty"; //주소1, addr1
                            adrSpecificName = "empty"; //주소2, addr2
                            telNum = "empty";//전화번호, tel
                            URL= "empty";//홈페이지, homepage
                            additionalFacilities = "empty";//부대시설, sbrsCl
                            animal = "empty";//애완동물 가능 여부, animalCmgCl
                            firstImageUrl = "empty";
                            searchKeyword = "empty";
                        }
                        break;
                }
                eventType= xpp.next();

            }

        } catch (Exception e) {
            Log.d("XmlParser.catch","err Occurred");
            Log.w("XmlParser.catch", "err",e);
        }

    }

    /////////////////////////////////////////////////


    private int lengthLocationParse(){
        int length = 0;
        String pageNo = "&pageNo=1";
        String deviceType = "&MobileOS=AND";
        String MobileApp = "&MobileApp=AppTest";
        String numOfRows = "&numOfRows=1";
        String radius = "&radius=20000";


        String queryUrl = "http://api.visitkorea.or.kr/openapi/service/rest/GoCamping/locationBasedList?serviceKey"+key+pageNo+deviceType+MobileApp+numOfRows+Keyword_mapX+Keyword_mapY+radius;


        Log.d("XmlParser.LocationParse","Start Location Parse");
        Log.d("XmlParser.LocationParse","queryUrl : " + queryUrl);

        try {
            URL url= new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성
            URLConnection t_connection = url.openConnection();
            t_connection.setReadTimeout(3000);

            InputStream is= url.openStream(); //url위치로 입력스트림 연결
            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부터 xml 입력받기
            String tag;//값을 변경하며
            int position =0;

            xpp.next();
            int eventType= xpp.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT){
                switch(eventType){
                    case XmlPullParser.START_DOCUMENT:
                        Log.d("XmlParser.LocationParse","StartParsing");

                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();//태그 이름 얻어오기
                        Log.d("XmlParser.LocationParse","Tag: "+tag);


                        if(tag.equals("item")){

                        }

                        else if(tag.equals("totalCount")) { //이름
                            xpp.next();
                            length=Integer.parseInt(xpp.getText());
                        }
                        break;
                    case XmlPullParser.TEXT:


                        break;

                }
                eventType= xpp.next();

            }

        } catch (Exception e) {
            Log.d("XmlParser.LocationParse","err Occurred");
            Log.w("XmlParser.LocationParse", "err",e);
        }
        return length;
    }

    public void LocationParse(){
        testList = new ArrayList<>();
        int length = lengthLocationParse();
        String pageNo = "&pageNo=1";
        String deviceType = "&MobileOS=AND";
        String MobileApp = "&MobileApp=AppTest";
//        String numOfRows = "&numOfRows=10";
        String numOfRows = "&numOfRows="+length;
        String radius = "&radius=20000";



        String queryUrl = "http://api.visitkorea.or.kr/openapi/service/rest/GoCamping/locationBasedList?serviceKey"+key+pageNo+deviceType+MobileApp+numOfRows+Keyword_mapX+Keyword_mapY+radius;


        Log.d("XmlParser.LocationParse","Start Location Parse");
        Log.d("XmlParser.LocationParse","queryUrl : " + queryUrl);

        try {
            URL url= new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성
            URLConnection t_connection = url.openConnection();
            t_connection.setReadTimeout(3000);

            InputStream is= url.openStream(); //url위치로 입력스트림 연결
            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부터 xml 입력받기
            String tag;//값을 변경하며
            int position =0;

            xpp.next();
            int eventType= xpp.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT){
                switch(eventType){
                    case XmlPullParser.START_DOCUMENT:
                        Log.d("XmlParser.LocationParse","StartParsing");

                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();//태그 이름 얻어오기
                        Log.d("XmlParser.LocationParse","Tag: "+tag);


                        if(tag.equals("item")){

                        }

                        else if(tag.equals("facltNm")) { //이름
                            xpp.next();
                            name=xpp.getText();
                            Log.d("XmlParser.LocationParse",position+"Tag: faclNm: "+name);

                        }

                        else if(tag.equals("addr1")) {  //주소1

                            xpp.next();
                            adrStreetName = xpp.getText();
                            Log.d("XmlParser.LocationParse",position+"Tag: addr1: "+adrStreetName);
                        }

                        else if(tag.equals("addr2")) {  //주소2

                            xpp.next();
                            adrSpecificName = xpp.getText();
                            Log.d("XmlParser.LocationParse",position+"Tag: addr2"+adrSpecificName);

                        }
                        else if(tag.equals("tel")) {  //tel

                            xpp.next();
                            telNum = xpp.getText();
                            Log.d("XmlParser.LocationParse",position+"Tag: telNum: "+ telNum);

                        }
                        else if(tag.equals("homepage")) {  //홈페이지

                            xpp.next();
                            URL = xpp.getText();
                            Log.d("XmlParser.LocationParse",position+"Tag: hompage: "+URL);

                        }

//                        else if(tag.equals("sbrsCl")) {  //부대시설
//
//                            xpp.next();
//                            additionalFacilities = xpp.getText();
//                            Log.d("hyeon",position+"Tag: sbrsCl: " + additionalFacilities);
//
//                        }

//                        else if(tag.equals("animalCmgCl")) {  //애완동물
//
//                            xpp.next();
//                            animal = xpp.getText();
//                            Log.d("hyeon",position+"Tag: animalCmgCl: "+animal);
//
//                        }
                        else if(tag.equals("firstImageUrl")) {  //이미지 URL

                            xpp.next();
                            firstImageUrl = xpp.getText();
                            Log.d("XmlParser.LocationParse",position+"Tag: firstImageUrl: "+firstImageUrl);

                        }

                        else if(tag.equals("mapX")) {  //X좌표

                            xpp.next();
                            mapX = xpp.getText();
                            Log.d("XmlParser.LocationParse",position+"Tag: mapX: "+mapX);

                        }

                        else if(tag.equals("mapY")) {  //Y좌표

                            xpp.next();
                            mapY = xpp.getText();
                            Log.d("XmlParser.LocationParse",position+"Tag: mapY: "+mapY);

                        }
                        else if(tag.equals("resultMsg")) {  //resultMsg
                            Log.d("XmlParser.LocationParse",position+"Tag Msg: "+ xpp.next());

                        }
                        else if(tag.equals("resultCode")) {  //resultCode
                            Log.d("XmlParser.LocationParse",position+"Tag resultCode: "+ xpp.next());

                        }
                        else if(tag.equals("contentId")) {  //Y좌표

                            xpp.next();
                            contentId = xpp.getText();
                            Log.d("xmlParser.START_DOCUMENT",position+"Tag - contentId: "+contentId);

                        }
                        else if(tag.equals("doNm")) {  //시,도 이름

                            xpp.next();
                            doNm = xpp.getText();
                            Log.d("xmlParser.START_DOCUMENT",position+"Tag - doNm: "+doNm);

                        }


                        break;
                    case XmlPullParser.TEXT:


                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName();
                        if(tag.equals("item")) {
                            testList.add(new SearchingList(name, doNm, adrStreetName, adrSpecificName,
                                    telNum, URL, additionalFacilities, animal, firstImageUrl,mapX,mapY,contentId));

                            Log.d("XmlParser.LocationParse",name+ adrStreetName+ adrSpecificName+
                                    telNum+ URL+ additionalFacilities+ animal+ firstImageUrl);
                            Log.d("XmlParser.LocationParse","ParsingSuccess");

                            name = "empty"; //이름, facltNm
                            adrStreetName = "empty"; //주소1, addr1
                            adrSpecificName = "empty"; //주소2, addr2
                            telNum = "empty";//전화번호, tel
                            URL= "empty";//홈페이지, homepage
                            additionalFacilities = "empty";//부대시설, sbrsCl
                            animal = "empty";//애완동물 가능 여부, animalCmgCl
                            firstImageUrl = "empty";
                            searchKeyword = "empty";
                        }
                        break;
                }
                eventType= xpp.next();

            }

        } catch (Exception e) {
            Log.d("XmlParser.LocationParse","err Occurred");
            Log.w("XmlParser.LocationParse", "err",e);
        }

    }

    public void FullDBSize(){
        Log.d("xmlParser","StartFullDBParse");
        String pageNo = "&pageNo=1";//
        String deviceType = "&MobileOS=AND";//
        String MobileApp = "&MobileApp=AppTest";//
        String numOfRows = "&numOfRows=1";//
        String queryUrl = "http://api.visitkorea.or.kr/openapi/service/rest/GoCamping/basedList?serviceKey"+key+pageNo+deviceType+MobileApp+numOfRows;



        try {
            URL url= new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성
            URLConnection t_connection = url.openConnection();

            InputStream is= url.openStream(); //url위치로 입력스트림 연결
            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부터 xml 입력받기
            String tag;//값을 변경하며

            xpp.next();
            int eventType= xpp.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT){
                switch(eventType){
                    case XmlPullParser.START_DOCUMENT:


                        break;

                        case XmlPullParser.START_TAG:
                            tag= xpp.getName();//태그 이름 얻어오기

                            if(tag.equals("item")){

                            }
                            else if(tag.equals("totalCount")) {  //시,도 이름
                                xpp.next();
                                size = Integer.parseInt(xpp.getText());
                                Log.d("xmlParser","totalCount: "+ size);
                            }

                            break;

                }
                eventType= xpp.next();

            }

        } catch (Exception e) {
            Log.d("XmlParser.catch","err Occurred");
            Log.w("XmlParser.catch", "err",e);
        }

    }

    public void FullDBParse(int size){
        Log.d("xmlParser","StartFullDBParse");
        testList = new ArrayList<>();

        String pageNo = "&pageNo=1";//
        String deviceType = "&MobileOS=AND";//
        String MobileApp = "&MobileApp=AppTest";//
        String numOfRows = "&numOfRows="+ size;//
        String queryUrl = "http://api.visitkorea.or.kr/openapi/service/rest/GoCamping/basedList?serviceKey"+key+pageNo+deviceType+MobileApp+numOfRows;

        try {
            URL url= new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성
            URLConnection t_connection = url.openConnection();

            InputStream is= url.openStream(); //url위치로 입력스트림 연결
            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부터 xml 입력받기
            String tag;//값을 변경하며
            int position =0;
            xpp.next();
            int eventType= xpp.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT){
                switch(eventType){
                    case XmlPullParser.START_DOCUMENT:
                        Log.d("xmlParser.START_DOCUMENT","StartParsing");

                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();//태그 이름 얻어오기
                        Log.d("xmlParser.START_DOCUMENT","Tag: "+tag);



                        if(tag.equals("item")){

                        }

                        else if(tag.equals("facltNm")) { //이름
                            xpp.next();
                            name=xpp.getText();
                            Log.d("xmlParser.START_DOCUMENT",position+"Tag: faclNm: "+name);

                        }

                        else if(tag.equals("addr1")) {  //주소1

                            xpp.next();
                            adrStreetName = xpp.getText();
                            Log.d("xmlParser.START_DOCUMENT",position+"Tag: addr1: "+adrStreetName);
                        }

                        else if(tag.equals("addr2")) {  //주소2

                            xpp.next();
                            adrSpecificName = xpp.getText();
                            Log.d("xmlParser.START_DOCUMENT",position+"Tag: addr2"+adrSpecificName);

                        }
                        else if(tag.equals("tel")) {  //tel

                            xpp.next();
                            telNum = xpp.getText();
                            Log.d("xmlParser.START_DOCUMENT",position+"Tag: telNum: "+ telNum);

                        }
                        else if(tag.equals("homepage")) {  //홈페이지

                            xpp.next();
                            URL = xpp.getText();
                            Log.d("xmlParser.START_DOCUMENT",position+"Tag: hompage: "+URL);

                        }
                        else if(tag.equals("firstImageUrl")) {  //이미지 URL

                            xpp.next();
                            firstImageUrl = xpp.getText();
                            Log.d("xmlParser.START_DOCUMENT",position+"Tag: firstImageUrl: "+firstImageUrl);

                        }

                        else if(tag.equals("mapX")) {  //X좌표

                            xpp.next();
                            mapX = xpp.getText();
                            Log.d("xmlParser.START_DOCUMENT",position+"Tag: mapX: "+mapX);

                        }

                        else if(tag.equals("mapY")) {  //Y좌표

                            xpp.next();
                            mapY = xpp.getText();
                            Log.d("xmlParser.START_DOCUMENT",position+"Tag: mapY: "+mapY);

                        }
                        else if(tag.equals("contentId")) {  //Y좌표

                            xpp.next();
                            contentId = xpp.getText();
                            Log.d("xmlParser.START_DOCUMENT",position+"Tag - contentId: "+contentId);

                        }
                        else if(tag.equals("doNm")) {  //시,도 이름

                            xpp.next();
                            doNm = xpp.getText();
                            Log.d("xmlParser.START_DOCUMENT",position+"Tag - contentId: "+contentId);

                        }


                        break;
                    case XmlPullParser.TEXT:


                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName();
                        if(tag.equals("item")) {
                            testList.add(new SearchingList(name, doNm, adrStreetName, adrSpecificName,
                                    telNum, URL, additionalFacilities, animal, firstImageUrl,mapX,mapY, contentId));

                            Log.d("xmlParser.END_TAG",name+ adrStreetName+ adrSpecificName+
                                    telNum+ URL+ additionalFacilities+ animal+ firstImageUrl);
                            Log.d("xmlParser.END_TAG","ParsingSuccess");

                            name = "empty"; //이름, facltNm
                            adrStreetName = "empty"; //주소1, addr1
                            adrSpecificName = "empty"; //주소2, addr2
                            telNum = "empty";//전화번호, tel
                            URL= "empty";//홈페이지, homepage
                            additionalFacilities = "empty";//부대시설, sbrsCl
                            animal = "empty";//애완동물 가능 여부, animalCmgCl
                            firstImageUrl = "empty";
                            searchKeyword = "empty";
                        }
                        break;
                }
                eventType= xpp.next();

            }

        } catch (Exception e) {
            Log.d("XmlParser.catch","err Occurred");
            Log.w("XmlParser.catch", "err",e);
        }

    }

    public ArrayList<SearchingList> getResult(){
        Log.d("xmlParser.getResult", "getResult");
        Log.d("xmlParser.getResult", "sizeofArray: "+testList.size());
        return this.testList;

    }

    public int getSize(){
        Log.d("xmlParser","getSize: "+size);
        return this.size;

    }


}
