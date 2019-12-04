package com.billcat;

import com.billcat.config.ApplicationProperties;
import com.billcat.config.DefaultProfileUtil;

import io.github.jhipster.config.JHipsterConstants;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.core.env.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@SpringBootApplication
@EnableConfigurationProperties({ LiquibaseProperties.class, ApplicationProperties.class })
@EnableDiscoveryClient
public class BillcategorizerApp implements InitializingBean {

	private static final Logger log = LoggerFactory.getLogger(BillcategorizerApp.class);

	private final Environment env;

	public BillcategorizerApp(Environment env) {
		this.env = env;
	}

	/**
	 * Initializes billcategorizer.
	 * <p>
	 * Spring profiles can be configured with a program argument
	 * --spring.profiles.active=your-active-profile
	 * <p>
	 * You can find more information on how profiles work with JHipster on <a href=
	 * "https://www.jhipster.tech/profiles/">https://www.jhipster.tech/profiles/</a>.
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
		if (activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT)
				&& activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_PRODUCTION)) {
			log.error("You have misconfigured your application! It should not run "
					+ "with both the 'dev' and 'prod' profiles at the same time.");
		}
		if (activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT)
				&& activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_CLOUD)) {
			log.error("You have misconfigured your application! It should not "
					+ "run with both the 'dev' and 'cloud' profiles at the same time.");
		}
	}

	/**
	 * Main method, used to run the application.
	 *
	 * @param args the command line arguments.
	 */
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(BillcategorizerApp.class);
		DefaultProfileUtil.addDefaultProfile(app);
		Environment env = app.run(args).getEnvironment();
		logApplicationStartup(env);

		FileInputStream file;
		try {
			file = new FileInputStream(new File("d:\\temp\\SzámlaMozgások0008EH2UW3511.xls"));
			Workbook workbook = new HSSFWorkbook(file);
			
			Sheet sheet = workbook.getSheetAt(0);

			Map<Integer, List<String>> data = new HashMap<>();
			int i = 0;
			for (Row row : sheet) {
				data.put(i, new ArrayList<String>());
				for (Cell cell : row) {
					System.out.println(getNumericValue(cell, null, false));
				}
				System.out.println("-------------------------------------------");
				i++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String getCellValueAsString(Cell cell) {
		if (cell == null) {
			return null;
		} else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			return cell.toString();
		} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			DataFormatter formatter = new HSSFDataFormatter();
			String formattedValue = formatter.formatCellValue(cell);
			return formattedValue;
		} else {
			System.out.println("Error izé.");
		}
		return null;
	}

	private static String getNumericValue(Cell cell, CellValue cellValue, boolean fromFormula) {
	    // Date is typed as numeric
	    if (HSSFDateUtil.isCellDateFormatted(cell)) { // TODO configurable??
	        DateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
	        return sdf.format(cell.getDateCellValue());
	    }
	    // Numeric type (use data formatter to get number format right)
	    DataFormatter formatter = new HSSFDataFormatter(Locale.ENGLISH);

	    if (cellValue == null) {
	        return formatter.formatCellValue(cell);
	    }

	    return fromFormula ? cellValue.formatAsString() : formatter.formatCellValue(cell);
	}
	private static void logApplicationStartup(Environment env) {
		String protocol = "http";
		if (env.getProperty("server.ssl.key-store") != null) {
			protocol = "https";
		}
		String serverPort = env.getProperty("server.port");
		String contextPath = env.getProperty("server.servlet.context-path");
		if (StringUtils.isBlank(contextPath)) {
			contextPath = "/";
		}
		String hostAddress = "localhost";
		try {
			hostAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			log.warn("The host name could not be determined, using `localhost` as fallback");
		}
		log.info(
				"\n----------------------------------------------------------\n\t"
						+ "Application '{}' is running! Access URLs:\n\t" + "Local: \t\t{}://localhost:{}{}\n\t"
						+ "External: \t{}://{}:{}{}\n\t"
						+ "Profile(s): \t{}\n----------------------------------------------------------",
				env.getProperty("spring.application.name"), protocol, serverPort, contextPath, protocol, hostAddress,
				serverPort, contextPath, env.getActiveProfiles());
	}
}
