package com.ibh.pocketpassword.helper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
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

	public static void tryConnect(String dbname, String username, String password) throws IBHDatabaseException, SQLException {

		if (!Files.exists(getDbFileName(dbname))) {
			throw new IBHDatabaseException(IBHDatabaseException.DBException.NOTAVAILABLE);
		}

		try (BasicDataSource bds = new BasicDataSource()) {
			bds.setDriverClassName("org.h2.Driver");
			String url = String.format("jdbc:h2:tcp://localhost/%s;IFEXISTS=TRUE", getDbPath(dbname).toString());
			bds.setUrl(url);
			bds.setUsername(username);
			bds.setPassword(password);

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

	@Autowired
	public static void changePwd(DataSource ds, String password) throws SQLException {
		try (Connection c = ds.getConnection()) {
			Statement s = c.createStatement();
			String sql  = String.format("ALTER USER %s SET PASSWORD '%'", System.getProperty("user.db.username").toUpperCase(), password);
			s.executeUpdate(sql);
			c.commit();
			s.close();
		}
	}
	
	private static Path getDbFileName(String dbName) {
		return getDbPath(dbName.concat(".mv.db"));
	}

}
