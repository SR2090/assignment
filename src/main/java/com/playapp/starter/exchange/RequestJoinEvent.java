package com.playapp.starter.exchange;

import javax.validation.constraints.NotBlank;

public class RequestJoinEvent {
	@NotBlank
	private String username;

	@NotBlank
	private String uniqueid;

    public String getUsername(){
        return username;
    }

    public String getUniqueid(){
        return uniqueid;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setUniqueid(String uniqueid){
        this.uniqueid = uniqueid;
    }
}