package com.utime.household.user.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReqUniqueVo {

	protected String token;
	protected String sessionId;
	protected String rsaId;
	protected String publicKey;
}
