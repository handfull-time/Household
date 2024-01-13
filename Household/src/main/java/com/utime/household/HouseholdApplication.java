package com.utime.household;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.utime.household.common.util.HouseholdUtils;
import com.utime.household.common.vo.HouseholdDefine;

import lombok.Data;

@SpringBootApplication
public class HouseholdApplication {
	

//Tabula: Tabula는 PDF 문서에서 테이블을 추출하는 데 특화된 오픈 소스 라이브러리입니다. Tabula는 사용자가 페이지 범위와 테이블 영역을 지정하면 해당 부분의 테이블 데이터를 CSV 혹은 Excel 형식으로 추출할 수 있습니다. Java API를 제공하기 때문에 Java 프로그램 내에서도 사용할 수 있습니다.

	/**
	 * 서비스 구동 정보 
	 */
	@Data
	private static class RunInfor{
		/** 서비스 동작 포트 */
		int port = 80;
		/** 서비스 호출 주소 */
		String contextPath = "Household";
		/** DB 위치 */
		String databasePath = "./Data";
		/** log 위치 */
		String logPath = "./Logs";
	}
	
	public static void main(String[] args) {
		
		final Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
		final File f = new File(Paths.get("").toAbsolutePath().toString(), "Infor.json");
		if( ! f.exists() ) {
			System.out.println("환경 정보 파일을 수정해 주세요.");
			System.out.println("파일은 여기 있어요.\n" + f.getAbsolutePath() );
			
			final String json = gson.toJson(new RunInfor());
			
			try {
				final FileOutputStream fos = new FileOutputStream(f);
				fos.write( json.getBytes("UTF-8") );
				fos.flush();
				fos.close();
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			
			return;
		}
		
		final RunInfor infor;
		try {
			final FileReader fr = new FileReader(f, Charset.forName("UTF-8"));
			infor = gson.fromJson(fr, RunInfor.class);
			System.out.println(infor.toString());
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return;
		}
		
		Exception socketException = null;
		try {
			final Socket socket = new Socket();
	        socket.connect(new InetSocketAddress("127.0.0.1", infor.port), 500);
	        socket.close();
	        socketException = null;
        } catch (Exception ex) {
        	socketException = ex;
        }
		
		if( socketException == null ) {
			// 이미 local에서 동일 포트가 오픈돼 있다면 중지한다.
			System.err.println("이미 실행중입니다.");
			return;
		}
		
		HouseholdDefine.KeyContextPath = "/" + infor.contextPath;
		
		final String dataPath = infor.databasePath;
		final File local = new File(dataPath);
		if( ! local.exists() ) {
			local.mkdir();
		}
		
		final Properties defaultProrpties = new Properties();
		defaultProrpties.setProperty("server.port", infor.port + "");
//				defaultProrpties.setProperty("spring.main.banner-mode", "off");
		defaultProrpties.setProperty("server.servlet.context-path", HouseholdDefine.KeyContextPath);
		defaultProrpties.setProperty("server.servlet.encoding.charset", "utf-8");
		defaultProrpties.setProperty("server.session.timeout", "14400");
		
		defaultProrpties.setProperty("spring.datasource.driverClassName", "net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
		defaultProrpties.setProperty("spring.datasource.url", "jdbc:log4jdbc:sqlite:"+dataPath+"/Household.sqlite.db&amp;allowMultiQueries=true");
		
		final SpringApplication application = new SpringApplication(HouseholdApplication.class);
		application.setDefaultProperties( defaultProrpties );
		application.run( args );
	}

}
