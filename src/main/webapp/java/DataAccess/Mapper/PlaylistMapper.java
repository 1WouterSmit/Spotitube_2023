package DataAccess.Mapper;

import DataAccess.DB;
import Domain.DomainObject;
import Domain.Playlist;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaylistMapper extends AbstractMapper {
	public static final String COLUMNS = "id, name, owner";

	public PlaylistMapper() {
		super("playlists", new String[]{"id", "name", "owner"});
	}

	protected String findStatement() {
		return "SELECT " + COLUMNS +
				" FROM playlists" +
				" WHERE id = ?";
	}

	protected String findAllStatement() {
		return "SELECT * " +
				"FROM playlists";
	}

	@Override
	protected String updateStatement(String column) {
		return "UPDATE playlists" +
				" SET "+ column + " = ?" +
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

	@Override
	protected Playlist doLoad(Long id, ResultSet rs) throws SQLException {
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
			playlists.add((Playlist)load(rs));
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
		PreparedStatement editNameStatement = DB.connection().prepareStatement(updateStatement("NAME"));
		editNameStatement.setString(1, name);
		editNameStatement.setLong(2, id);
		editNameStatement.execute();
	}
}
