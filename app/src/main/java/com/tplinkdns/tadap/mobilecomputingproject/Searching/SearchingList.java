package com.tplinkdns.tadap.mobilecomputingproject.Searching;


public class SearchingList {

    public String name; //이름, facltNm
    public String adrStreetName; //주소1, addr1
    public String adrSpecificName; //주소2, addr2
    public String telNum;//전화번호, tel
    public String URL;//홈페이지, homepage
    public String additionalFacilities;//부대시설, sbrsCl
    public String animal;//애완동물 가능 여부, animalCmgCl
    public String ImageUrl; //대표 사진, firstImageUrl
    public String mapX; //x좌표
    public String mapY; //y좌표
    public String contentId; // contentId, PrimaryKey
    public String doNm = "empty";//시군도 이름


    public SearchingList(String name, String doNm,String adrStreetName, String adrSpecificName,
                         String telNum, String URL, String additionalFacilities, String animal, String firstImageUrl, String mapX, String mapY, String contentId){

        this.name = name;
        this.doNm = doNm;
        this.adrStreetName = adrStreetName;
        this.adrSpecificName = adrSpecificName;
        this.telNum = telNum;
        this.URL = URL;
        this.additionalFacilities = additionalFacilities;
        this.animal = animal;
        this.ImageUrl =firstImageUrl;
        this.mapX = mapX;
        this.mapY = mapY;
        this.contentId=contentId;
    }


}
