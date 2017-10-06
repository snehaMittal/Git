package com.javahelps.git;

/**
 * Created by Sneha on 02-10-2017.
 */

public class Details {

    public String image_url ;
    String login ;
    String html_url ;
    Integer followers_url ;
    Integer following_url ;
    String repos_url ;

    public Details(String image_url, String login, String html_url,
                   Integer followers_url, Integer following_url,  String repos_url) {
        this.image_url = image_url;
        this.login = login;
        this.html_url = html_url;
        this.followers_url = followers_url;
        this.following_url = following_url;
        this.repos_url = repos_url;
    }
}
