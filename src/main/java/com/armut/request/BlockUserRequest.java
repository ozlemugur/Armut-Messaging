package com.armut.request;



import lombok.Data;

@Data
public class BlockUserRequest extends UserRequest {

	private String toBeBlockedUserName;

	public BlockUserRequest() {
	}

	public BlockUserRequest(String userName, String toBeBlockedUserName, String token) {
		this.setToBeBlockedUserName(toBeBlockedUserName);
		this.setToken(token);
		this.setUserName(userName);
	}


}
