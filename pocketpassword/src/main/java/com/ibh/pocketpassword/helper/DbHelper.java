package com.ibh.pocketpassword.helper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibh.pocketpassword.model.IBHDatabaseException;

public final class DbHelper {

	private static final Logger LOG = LoggerFactory.getLogger(DbHelper.class);

	private static final Properties PROPERTIES = new Properties();

	static {
		try {
			try (InputStream is = DbHelper.class.getResourceAsStream("/config.properties")) {
				PROPERTIES.load(is);
			}
		} catch (IOException ex) {
			LOG.warn("cannot load properties file", ex);
		}
	}

	private DbHelper() {
	}

	public static void tryConnect(String dbname, String username, String password) throws IBHDatabaseException, SQLException {

		Path dbpathname = getDbFileName(dbname);
		if (!Files.exists(dbpathname)) {
			throw new IBHDatabaseException(IBHDatabaseException.DBException.AVAILABLE);
		}

		try (BasicDataSource bds = new BasicDataSource()) {
			bds.setDriverClassName("org.h2.Driver");
			String url = String.format("jdbc:h2:tcp://localhost/%s;IFEXISTS=TRUE", dbpathname.toString());
			bds.setUrl(url);
			bds.setUsername(username);
			bds.setPassword(password);

			try (Connection conn = bds.getConnection()) {
				DatabaseMetaData meta = conn.getMetaData();
				System.out.println(meta.getURL());
				System.out.println(meta.getUserName());
			}

		}

	}

	private static Path getDbPath(String dbName) {
		Path dbpath = Paths.get(PROPERTIES.getProperty("DBFolder"), dbName);

		return dbpath;
	}

	private static Path getDbFileName(String dbName) {
		return getDbPath(dbName.concat(".mv.db"));
	}

}
