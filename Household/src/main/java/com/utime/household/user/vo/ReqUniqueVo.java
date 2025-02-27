package com.utime.household.user.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(exclude = {"sessionId", "publicKey"})
public class ReqUniqueVo {

	protected String token;
	protected String sessionId;
	protected String rsaId;
	protected String publicKey;
}
