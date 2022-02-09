package DataAccess.DataMapper;

import DataAccess.DB;
import Domain.Song;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

public class SongMapper extends AbstractMapper {
	private HashMap<Integer, Song> map = new HashMap<>();

	public Song find(int id) {
		if(map.containsKey(id)) return map.get(id);
		Song song = retrieveSong(id);
		if(song != null) map.put(id, song);
		return song;
	}
	private Song retrieveSong(int id) {
		try {
			PreparedStatement ps = DB.connection().prepareStatement(
					"SELECT * "+
							"FROM Tracks "+
							"INNER JOIN Songs ON Tracks.id = Songs.id "+
							"WHERE Tracks.id = ?" );

			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();
			if(!rs.next()) return null;

			return new Song(rs.getInt("id"),
					rs.getString("title"),
					rs.getString("performer"),
					rs.getInt("duration"),
					rs.getString("album"),
					rs.getBoolean("offlineAvailable"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void delete(int id) {
		map.remove(id);
		deleteFromDB(id);
	}
	private void deleteFromDB(int id) {
		try {
			PreparedStatement ps = DB.connection().prepareStatement(
					"DELETE FROM Songs" +
							"WHERE id = ?" );
			ps.setInt(1, id);
			ps.executeQuery();

			ps = DB.connection().prepareStatement(
					"DELETE FROM Tracks" +
							"WHERE id = ?" );
			ps.setInt(1, id);
			ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
