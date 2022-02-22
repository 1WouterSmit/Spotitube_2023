package DataAccess.DataMapper;

import DataAccess.DB;

import java.sql.Connection;

public abstract class AbstractMapper {

	protected Connection conn() {
		return DB.connection();
	}
}
