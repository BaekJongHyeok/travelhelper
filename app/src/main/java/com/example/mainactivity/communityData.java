package com.example.mainactivity;

public class communityData {
    private String member_row_number;
    private String member_title;
    private String member_Date;
    private String member_context;
    private String member_id;
    private String member_rating;
    String member_address;








    public String getMember_id(){
        return member_id;
    }
    public String getMember_row_number(){
        return member_row_number;
    }

    public String getMember_title(){
        return member_title;
    }
    public String getMember_rating(){
        return member_rating;
    }
    public String getMember_Date(){
        return member_Date;
    }
    public String getMember_context(){
        return member_context;
    }

    public void setMember_row_number(String member_row_number) {
        this.member_row_number = member_row_number;
    }
    public void setMember_rating(String member_rating) {
        this.member_rating = member_rating;
    }
    public void setMember_title(String member_title) {
        this.member_title = member_title;
    }

    public void setMember_Date(String member_address) {
        this.member_Date = member_address+"에 작성된 글입니다.";
    }
    public void setMember_id(String member_id) {
        this.member_id= "작성자: "+ member_id;
    }

    public void setMember_context(String member_context) {
        this.member_context = member_context;
    }

}
