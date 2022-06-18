package DataAccess.Mapper;

import DataAccess.DB;
import Domain.Playlist;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaylistMapper {
	public static final String COLUMNS = "id, name, owner";

	protected String findAllStatement() {
		return "SELECT * " +
				"FROM playlists";
	}

	protected String updateStatement() {
		return "UPDATE playlists" +
				" SET name = ?" +
				" WHERE id = ?";
	}

	protected String deleteStatement() {
		return "DELETE FROM playlists " +
				"WHERE id = ?";
	}

	protected String insertStatement() {
		return "INSERT INTO playlists (" + COLUMNS + ") " +
				"VALUES (?, ?, ?)";
	}

	protected Playlist load(ResultSet rs) throws SQLException {
		return new Playlist(rs.getLong("id"),
		rs.getString("name"),
		rs.getLong("owner"));
	}

	public void deletePlaylist(Long id) throws SQLException {
		PreparedStatement deleteStatement = DB.connection().prepareStatement(deleteStatement());
		deleteStatement.setLong(1, id);
		deleteStatement.execute();
	}

	public ArrayList<Playlist> findAll() throws SQLException {
		ArrayList<Playlist> playlists = new ArrayList<>();
		ResultSet rs = DB.connection().prepareStatement(findAllStatement()).executeQuery();
		while(rs.next()) {
			playlists.add(load(rs));
		}
		return playlists;
	}

	public void insertPlaylist(Playlist playlist) throws SQLException {
		PreparedStatement insertStatement = DB.connection().prepareStatement(insertStatement());

		insertStatement.setLong(1, getNewId());
		insertStatement.setString(2, playlist.getName());
		insertStatement.setLong(3, playlist.getOwner());
		insertStatement.execute();
	}

	public void updateName(Long id, String name) throws SQLException {
		PreparedStatement editNameStatement = DB.connection().prepareStatement(updateStatement());
		editNameStatement.setString(1, name);
		editNameStatement.setLong(2, id);
		editNameStatement.execute();
	}

	protected Long getNewId() throws SQLException {
		PreparedStatement highestIdStatement = DB.connection().prepareStatement(findHighestIdStatement());
		ResultSet rs = highestIdStatement.executeQuery();
		rs.next();
		return 1 + rs.getLong("ID");
	}

	protected String findHighestIdStatement() {
		return "SELECT MAX(ID) as ID " +
				"FROM playlists";
	}
}
