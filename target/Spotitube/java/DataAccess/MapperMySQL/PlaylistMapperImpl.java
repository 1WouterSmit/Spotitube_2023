package DataAccess.MapperMySQL;

import DataAccess.DB;
import Domain.Playlist;
import Services.IMappers.PlaylistMapper;

import javax.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaylistMapperImpl implements PlaylistMapper {
	private static final String COLUMNS = "id, name, owner";
	private DB database;

	@Inject
	public void setDatabase(DB database) {
		this.database = database;
	}

	private String findAllStatement() {
		return "SELECT * " +
				"FROM playlists";
	}

	private String updateStatement() {
		return "UPDATE playlists" +
				" SET name = ?" +
				" WHERE id = ?";
	}

	private String deleteStatement() {
		return "DELETE FROM playlists " +
				"WHERE id = ?";
	}

	private String insertStatement() {
		return "INSERT INTO playlists (" + COLUMNS + ") " +
				"VALUES (?, ?, ?)";
	}

	private Playlist load(ResultSet rs) throws SQLException {
		return new Playlist(rs.getLong("id"),
		rs.getString("name"),
		rs.getLong("owner"));
	}

	public void deletePlaylist(Long id) throws SQLException {
		PreparedStatement deleteStatement = statement(deleteStatement());
		deleteStatement.setLong(1, id);
		deleteStatement.execute();
	}

	public ArrayList<Playlist> getAllPlaylists() throws SQLException {
		ArrayList<Playlist> playlists = new ArrayList<>();
		ResultSet rs = statement(findAllStatement()).executeQuery();
		while(rs.next()) {
			playlists.add(load(rs));
		}
		return playlists;
	}

	public void insertPlaylist(Playlist playlist) throws SQLException {
		PreparedStatement insertStatement = statement(insertStatement());

		insertStatement.setLong(1, getNewId());
		insertStatement.setString(2, playlist.getName());
		insertStatement.setLong(3, playlist.getOwner());
		insertStatement.execute();
	}

	public void updatePlaylistName(Long id, String name) throws SQLException {
		PreparedStatement editNameStatement = statement(updateStatement());
		editNameStatement.setString(1, name);
		editNameStatement.setLong(2, id);
		editNameStatement.execute();
	}

	private Long getNewId() throws SQLException {
		PreparedStatement highestIdStatement = statement(findHighestIdStatement());
		ResultSet rs = highestIdStatement.executeQuery();
		rs.next();
		return 1 + rs.getLong("ID");
	}

	private String findHighestIdStatement() {
		return "SELECT MAX(ID) as ID " +
				"FROM playlists";
	}

	private PreparedStatement statement(String statement) throws SQLException {
		return database.connection().prepareStatement(statement);
	}
}
