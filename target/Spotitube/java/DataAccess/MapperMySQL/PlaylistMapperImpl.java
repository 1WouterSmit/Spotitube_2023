package DataAccess.MapperMySQL;

import DataAccess.DB;
import Domain.Playlist;
import Services.IMappers.PlaylistMapper;

import javax.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class PlaylistMapperImpl implements PlaylistMapper {
	private HashMap<Long, Playlist> map = new HashMap<>();
	private static final String COLUMNS = "id, name, owner";
	private DB database;

	@Inject
	public void setDatabase(DB database) {
		this.database = database;
	}

	public void deletePlaylist(Long id) throws SQLException {
		PreparedStatement deleteStatement = statement(deleteStatement());
		deleteStatement.setLong(1, id);

		deleteStatement.execute();
		map.remove(id);
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
		map.put(playlist.getID(), playlist);
	}

	public void updatePlaylistName(Long id, String name) throws SQLException {
		PreparedStatement editNameStatement = statement(updateStatement());
		editNameStatement.setString(1, name);
		editNameStatement.setLong(2, id);

		editNameStatement.execute();
		map.get(id).setName(name);
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

	private String findHighestIdStatement() {
		return "SELECT MAX(ID) as ID " +
				"FROM playlists";
	}

	private Long getNewId() throws SQLException {
		PreparedStatement highestIdStatement = statement(findHighestIdStatement());
		ResultSet rs = highestIdStatement.executeQuery();
		rs.next();
		return 1 + rs.getLong("ID");
	}

	private Playlist load(ResultSet rs) throws SQLException {
		Long id = rs.getLong("id");
		if (map.containsKey(id)) return map.get(id);
		Playlist newPlaylist = doLoad(rs, id);
		map.put(newPlaylist.getID(), newPlaylist);
		return newPlaylist;
	}

	private Playlist doLoad(ResultSet rs, Long id) throws SQLException {
		return new Playlist(id,
				rs.getString("name"),
				rs.getLong("owner"));
	}

	private PreparedStatement statement(String statement) throws SQLException {
		return database.connection().prepareStatement(statement);
	}
}
