package com.hawkeye.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DerbyDbConnection {
	private int version = 1;
	public static boolean dbstarted = false;
	public static final String driver = "org.apache.derby.jdbc.EmbeddedDriver";
	public static final String connectionURL = "jdbc:derby:C:/DATA/Perftool/Hawkeye.db;create=true";
	private static DerbyDbConnection instance = new DerbyDbConnection();
	private static Connection conn;

	public static void main(String[] args) {
		DerbyDbConnection.getInstance().init();
	}

	public static DerbyDbConnection getInstance() {
		return instance;

	}

	public  Connection getConnection() {
		try {
			Class.forName(driver);
		} catch (java.lang.ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(connectionURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public boolean init() {
		getConnection();
		if (!checkDB()) {

			String createVersion = "CREATE TABLE HAWKEYE.VERSION (versionNumber INTEGER NOT NULL)";
			String createsqlstats = "create table HAWKEYE.SQL_STATS(ID BIGINT not null generated always as identity,SQL_STMT VARCHAR(1000) not null,EXEC_TIME BIGINT not null,FAILED_FLAG VARCHAR(2) default 'N' not null,TIME_STAMP TIMESTAMP not null,REQUEST_ID BIGINT not null, constraint SQL120810064036500 primary key (ID))";
			String createtracestats = "create table HAWKEYE.TRACE_STATS(ID BIGINT not null generated always as identity, METHOD_NAME VARCHAR(1000) not null,ACTION VARCHAR(15) not null,PARAMS VARCHAR(1000) ,RETURNS VARCHAR(500) ,EXEC_TIME BIGINT not null,TIME_STAMP TIMESTAMP ,REQUEST_ID BIGINT not null, constraint SQL120810064036501 primary key (ID))";
			String createsqlstatsConstraints = "create unique index SQL120810064036500 on HAWKEYE.SQL_STATS(ID)";
			String createtracestatsConstraints = "create unique index SQL120810064036501 on HAWKEYE.TRACE_STATS(ID)";
			String createsqlstatsvital = "create table HAWKEYE.VITAL_STATS(ID BIGINT not null generated always as identity,TYPE VARCHAR(50) not null,INIT_MEM BIGINT not null,USED_MEM BIGINT not null,MAX_MEM BIGINT not null,COMITTED_MEM BIGINT not null,TIME_STAMP TIMESTAMP not null, constraint SQL120810064036502 primary key (ID))";
			String createvitalstatsConstraints = "create unique index SQL120810064036502 on HAWKEYE.VITAL_STATS(ID)";
			String createwatches = "create table HAWKEYE.WATCHES(ID BIGINT not null generated always as identity,URL VARCHAR(100) not null, constraint SQL120810064036503 primary key (ID))";
			String createwatchesConstraints = "create unique index SQL120810064036503 on HAWKEYE.WATCHES(ID)";
			try {

				Statement stmt = conn.createStatement();
				stmt.addBatch(createVersion);
				stmt.addBatch(createsqlstats);
				stmt.addBatch(createsqlstatsConstraints);
				stmt.addBatch(createtracestats);
				stmt.addBatch(createtracestatsConstraints);
				stmt.addBatch(createsqlstatsvital);
				stmt.addBatch(createvitalstatsConstraints);
				stmt.addBatch(createwatches);
				stmt.addBatch(createwatchesConstraints);
				stmt.executeBatch();
				conn.commit();
				conn.close();
				dbstarted = true;
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return dbstarted;
	}

	private boolean checkDB() {
		boolean tableExists = false;
		Statement stmt2;
		try {
			stmt2 = conn.createStatement();
			ResultSet rs = stmt2.executeQuery("select * from HAWKEYE.VERSION");
			tableExists = true;			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}

		return tableExists;
	}

}
