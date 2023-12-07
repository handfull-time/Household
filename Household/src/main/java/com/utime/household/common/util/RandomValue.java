package com.utime.household.common.util;

/**
 * 난수 생성하기 숫자형/문자형/복합형
 */
public class RandomValue {
	
	/**
	 * 랜덤 생성 조건
	 */
	public static enum ERandomType { 
		/** 숫자만*/
		NumbersOnly, 
		/** 문자만(대)*/
		Uppercase, 
		/** 문자만(대)*/
		Lowercase,
		/** 숫자 대문자*/
		NumericUppercase,
		/** 숫자 소문자*/
		NumericLowercase,
		/** 숫자 대소문자*/
		NumericUpperLowercase;
	}
	
	public int length = 0;
	public ERandomType randomType;
	
	public RandomValue(){
		this(8, ERandomType.NumbersOnly);
	}
	
	public RandomValue(int iLength, ERandomType randomType) {
		this.setLength(iLength);
		this.setRandomType(randomType);
	}
	
	public void setLength(int iLength) {
		this.length = iLength;
	}

	public void setRandomType(ERandomType randomType) {
		this.randomType = randomType;
	}
	
	public String getRandomValue() {
		return this.makeRandomValue();
	}
	
	@Override
	public String toString() {
		return this.makeRandomValue();
	}

	/**
	 * 난수로 발생된 숫자를 리턴한다. 한자리!
	 * 
	 * @return
	 */
	private String getRandomNumbers() {
		return String.valueOf((int) (Math.random() * 10));
	}

	/**
	 * 난수로 발생된 문자(대문자)를 리턴한다. 한자리!
	 * 
	 * @return
	 */
	private String getRandomUppercase() {
		return String.valueOf((char) ((int) (Math.random() * 26) + 65));
	}
	
	/** 난수로 발생된 문자(소문자)를 리턴한다. 한자리!
	 * @return
	 */
	private String getRandomLowercase(){
		return String.valueOf((char) ((int) (Math.random() * 26) + 97));
	}
	

	/**
	 * 위 getRandomInteger, getRandomString을 이용해 readLengthValue,readOutputValue
	 * 값에 따라 랜덤 값을 생성한다.
	 * 
	 * @return
	 */
	private String makeRandomValue() {
		final StringBuffer sb = new StringBuffer();

		switch( this.randomType ){
		case NumbersOnly :
			for (int i = 0; i < length; i++) {
				sb.append( this.getRandomNumbers() );
			}
			break;
		case Uppercase :
			for (int i = 0; i < length; i++) {
				sb.append( this.getRandomUppercase() );
			}
			break;
		case Lowercase :
			for (int i = 0; i < length; i++) {
				sb.append( this.getRandomLowercase() );
			}
			break;	
		case NumericUppercase :
			for (int i = 0; i < length; i++) {
				switch( (int) (Math.random() * 2) ) {
				case 0 : sb.append( this.getRandomNumbers() ); break;
				default : sb.append( this.getRandomUppercase() );
				}
			}
			break;
		case NumericLowercase :
			for (int i = 0; i < length; i++) {
				switch( (int) (Math.random() * 2) ) {
				case 0 : sb.append( this.getRandomNumbers() ); break;
				default : sb.append( this.getRandomLowercase() );
				}
			}
			break;
		case NumericUpperLowercase :
			for (int i = 0; i < length; i++) {
				switch( (int) (Math.random() * 3) ) {
				case 0 : sb.append( this.getRandomNumbers() ); break;
				case 1 : sb.append( this.getRandomLowercase() ); break;
				default : sb.append( this.getRandomUppercase() );
				}
			}
			break;
		}

		return sb.toString();
	}
}
