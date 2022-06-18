package DataAccess.Mapper;

import DataAccess.DB;
import Domain.DomainObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractMapper {
	protected String table;
	protected String[] columns;

	public AbstractMapper(String table, String[] columns) {
		this.table = table;
		this.columns = columns;
	}

	protected DomainObject abstractFind(Long id) throws SQLException {
		try {
			PreparedStatement findStatement = DB.connection().prepareStatement(findStatement());
			findStatement.setLong(1, id);
			ResultSet rs = findStatement.executeQuery();
			rs.next();
			return load(rs);
		} catch (SQLException e) {
			throw new SQLException(e);
		}
	}

	/*protected void abstractUpdate(String id, String column, String assignment) throws SQLException {
		try {
			PreparedStatement ps = DB.connection().prepareStatement(updateStatement(column));
			ps.setString(1, assignment);
			ps.setString(2, id);
			ps.executeQuery();

		} catch (SQLException e) {
			throw new SQLException(e);
		}
	}*/

	/*protected void abstractUpdate(Long id, String column, String assignment) throws SQLException {
		PreparedStatement ps = DB.connection().prepareStatement(updateStatement(column));
		ps.setString(1, assignment);
		ps.setLong(2, id);
		ps.executeQuery();
	}*/

	protected DomainObject load(ResultSet rs) throws SQLException {
		Long id = rs.getLong(1);
		return doLoad(id, rs);
	}

	protected Long getNewId() throws SQLException {
		PreparedStatement highestIdStatement = DB.connection().prepareStatement(findHighestIdStatement());
		ResultSet rs = highestIdStatement.executeQuery();
		rs.next();
		return 1 + rs.getLong("ID");
	}

	protected String findHighestIdStatement() {
		return "SELECT MAX(ID) as ID " +
				"FROM " + table;
	}
	abstract protected String findStatement();
	abstract protected String updateStatement(String column);
	abstract protected DomainObject doLoad(Long id, ResultSet rs) throws SQLException;
}
