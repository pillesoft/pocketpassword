package com.ibh.pocketpassword.helper;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.h2.tools.ChangeFileEncryption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ibh.pocketpassword.model.IBHDatabaseException;

public final class DbHelper {

	private static final Logger LOG = LoggerFactory.getLogger(DbHelper.class);

	private static final Properties PROPERTIES = new Properties();

	static {
		try {
			try (InputStream is = DbHelper.class.getResourceAsStream("/application.properties")) {
				PROPERTIES.load(is);
			}
		} catch (IOException ex) {
			LOG.warn("cannot load properties file", ex);
		}
	}

	private DbHelper() {
	}

	public static void tryConnect(String dbname, String username, String password) throws IBHDatabaseException, SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {

		if (!Files.exists(getDbFileName(dbname))) {
			throw new IBHDatabaseException(IBHDatabaseException.DBException.NOTAVAILABLE);
		}
		String filepwd = CryptHelper.hash(password);
		try (BasicDataSource bds = new BasicDataSource()) {
			bds.setDriverClassName("org.h2.Driver");
			
			bds.setUrl(getConnectionUrl(dbname, true));
			bds.setUsername(username);
			bds.setPassword(String.format("%s %s", filepwd, password));

			LOG.debug(bds.getUrl());
			LOG.debug(bds.getUsername());
			LOG.debug(bds.getPassword());
			
			try (Connection conn = bds.getConnection()) {
				DatabaseMetaData meta = conn.getMetaData();
				LOG.info("db url: {}", meta.getURL());
				LOG.info("db username: {}", meta.getUserName());
			}

		}

	}

	public static void tryCreate(String dbname) throws IBHDatabaseException {
		Path dbpathname = getDbFileName(dbname);
		if (Files.exists(dbpathname)) {
			throw new IBHDatabaseException(IBHDatabaseException.DBException.AVAILABLE);
		}
	}
	
	public static Path getDbPath(String dbName) {
		Path dbpath = Paths.get(PROPERTIES.getProperty("app.dbfolder"), dbName);

		return dbpath;
	}

	public static void changePwd(String dbname, String username, String password, String newPassword) throws IBHDatabaseException, SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
		if (!Files.exists(getDbFileName(dbname))) {
			throw new IBHDatabaseException(IBHDatabaseException.DBException.NOTAVAILABLE);
		}
		String filepwdold = CryptHelper.hash(password);
		String filepwdnew = CryptHelper.hash(newPassword);

		LOG.debug("change encrypt pwd");
		ChangeFileEncryption.execute(PROPERTIES.getProperty("app.dbfolder"), dbname, "AES", filepwdold.toCharArray(), filepwdnew.toCharArray(), false);
		LOG.debug(filepwdnew);

		LOG.debug("change db pwd");
		try (BasicDataSource bds = new BasicDataSource()) {
			bds.setDriverClassName("org.h2.Driver");
			
			bds.setUrl(getConnectionUrl(dbname, true));
			bds.setUsername(username);
			bds.setPassword(String.format("%s %s", filepwdnew, password));

			try (Connection conn = bds.getConnection()) {
				Statement s = conn.createStatement();
				String sql  = String.format("ALTER USER %s SET PASSWORD '%s'", username.toUpperCase(), newPassword);
				s.executeUpdate(sql);
				conn.commit();
				s.close();
			}
		}

	}
	
	private static Path getDbFileName(String dbName) {
		return getDbPath(dbName.concat(".mv.db"));
	}

	public static String getConnectionUrl(String dbname, boolean isAvailable) {
		String url = String.format("jdbc:h2:file:%s;CIPHER=AES", getDbPath(dbname).toString());
		if(isAvailable) {
			url += ";IFEXISTS=TRUE";
		}
		return url;
	}
}
