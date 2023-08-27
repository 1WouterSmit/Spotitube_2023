package Services;

import DataAccess.MapperMySQL.PlaylistMapperImpl;
import Domain.Playlist;
import Domain.Track;
import Services.JSONDTO.PlaylistAssembler;
import Services.JSONDTO.PlaylistDTO;
import Services.JSONDTO.PlaylistsDTO;
import Services.JSONDTO.TrackAssembler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.SQLException;
import java.util.ArrayList;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PlaylistServiceTest {
	private PlaylistService plSer;
	private final Long VALIDUSERID = (long) 27;
	private final Long VALIDUSERID2 = (long) 28;

	@BeforeEach
	public void setup() {
		PlaylistMapperImpl playlistMapperImpl = new PlaylistMapperImpl() {
			boolean added = false;
			boolean deleted = false;
			boolean edited = false;

			@Override
			public ArrayList<Playlist> getAllPlaylists() throws SQLException {
				Playlist p1 = new Playlist((long)1, "playlist1", VALIDUSERID);
				Playlist p2 = new Playlist((long)2, "playlist2", VALIDUSERID2);
				Playlist p3 = new Playlist((long)3, "playlist3", VALIDUSERID);
				ArrayList<Playlist>playlists = new ArrayList<>();
				if(edited) p1.setName("playlist1<edited>");
				playlists.add(p1);
				if(!deleted) playlists.add(p2);
				if(added) playlists.add(p3);

				return playlists;
			}
			@Override
			public void updatePlaylistName(Long id, String name) throws SQLException {
				if(id != 1 || name == null) throw new SQLException();
				edited = true;
			}
			@Override
			public void insertPlaylist(Playlist playlist) throws SQLException {
				if(playlist == null) throw new SQLException();
				added = true;
			}
			@Override
			public void deletePlaylist(Long id) throws SQLException {
				if(id != 2) throw new SQLException();
				deleted = true;
			}
		};
		PlaylistAssembler playlistAssembler = new PlaylistAssembler() {
			@Override
			public Playlist disassemblePlaylistDTO(PlaylistDTO pdto, Long userId) {
				if(pdto == null) return null;
				return new Playlist(pdto.getId(), pdto.getName(), VALIDUSERID);
			}
		};
		playlistAssembler.setTrackAssembler(new TrackAssembler());
		TrackService trackService = new TrackService() {
			@Override
			public ArrayList<Track> getTracksFromPlaylist(Long playlistId) throws SQLException {
				return new ArrayList<>();
			}
		};
		plSer = new PlaylistService();
		plSer.setPlaylistMapper(playlistMapperImpl);
		plSer.setPlaylistAssembler(playlistAssembler);
		plSer.setTrackService(trackService);
	}

	@Test
	void GetAllPlaylists() throws SQLException {
		PlaylistsDTO psDTO = plSer.getAllPlaylists(VALIDUSERID);
		Assertions.assertEquals(2, psDTO.getPlaylists().length);
		Assertions.assertEquals("playlist1", psDTO.getPlaylists()[0].getName());
		Assertions.assertEquals("playlist2", psDTO.getPlaylists()[1].getName());
	}

	@Test
	void deleteCorrectPlaylist() throws SQLException {
		plSer.deletePlaylist((long)2);
		PlaylistsDTO psDTO = plSer.getAllPlaylists((long)27);
		Assertions.assertEquals(1, psDTO.getPlaylists().length);
		Assertions.assertEquals("playlist1", psDTO.getPlaylists()[0].getName());
	}

	@Test
	void deleteInvalidPlaylistId() {
		Assertions.assertThrows(SQLException.class, () -> plSer.deletePlaylist((long)6));
	}

	@Test
	void addPlaylistCorrectly() throws SQLException {
		PlaylistDTO playlistDTO = new PlaylistDTO();
		playlistDTO.setId((long)-1);
		playlistDTO.setName("playlist3");
		playlistDTO.setOwner(false);
		plSer.addPlaylist(playlistDTO, VALIDUSERID);
		PlaylistsDTO psdto = plSer.getAllPlaylists(VALIDUSERID);
		Assertions.assertEquals(3, psdto.getPlaylists().length);
		Assertions.assertEquals("playlist3", psdto.getPlaylists()[2].getName());
	}

	@Test
	void addNullPlaylist() {
		Assertions.assertThrows(SQLException.class, () -> plSer.addPlaylist(null, VALIDUSERID));
	}

	@Test
	void editPlaylistCorrectly() throws SQLException {
		PlaylistDTO playlistDTO = new PlaylistDTO();
		playlistDTO.setId((long)1);
		playlistDTO.setName("playlist1<edited>");
		playlistDTO.setOwner(false);
		plSer.editPlaylist(playlistDTO, VALIDUSERID);
		PlaylistsDTO psdto = plSer.getAllPlaylists(VALIDUSERID);
		Assertions.assertEquals(2, psdto.getPlaylists().length);
		Assertions.assertEquals("playlist1<edited>", psdto.getPlaylists()[0].getName());
	}

	@Test
	void editInvalidPlaylist() {
		PlaylistDTO playlistDTO = new PlaylistDTO();
		playlistDTO.setId((long)6);
		playlistDTO.setName("playlist1<edited>");
		playlistDTO.setOwner(false);
		Assertions.assertThrows(SQLException.class, () -> plSer.editPlaylist(playlistDTO, VALIDUSERID));
	}
}