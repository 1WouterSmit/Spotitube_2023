package Services;

import Services.IMappers.TrackMapper;
import Domain.Track;
import JSONDTO.TrackAssembler;
import JSONDTO.TrackDTO;
import JSONDTO.TracksDTO;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.ArrayList;

public class TrackService {
	TrackAssembler trackAssembler;
	TrackMapper trackMapper;

	@Inject
	public void setTrackMapper(TrackMapper trackMapper) {
		this.trackMapper = trackMapper;
	}
	@Inject
	public void setTrackAssembler(TrackAssembler trackAssembler) {
		this.trackAssembler = trackAssembler;
	}

	public TracksDTO getTrackListFromPlaylist(Long playlistId) throws SQLException {
		return trackAssembler.assembleTracksDTO(getTracksFromPlaylist(playlistId));
	}

	public ArrayList<Track> getTracksFromPlaylist(Long playlistId) throws SQLException {
		return trackMapper.getAllTracksInPlaylist(playlistId);
	}

	public ArrayList<Track> getTracksForPlaylist(Long playlistId) throws SQLException {
		return trackMapper.getAllTracksNotInPlaylist(playlistId);
	}

	public TracksDTO getTrackListForPlaylist(Long playlistId) throws SQLException {
		return trackAssembler.assembleTracksDTO(getTracksForPlaylist(playlistId));
	}

	public void addTrackToPlaylist(Long playlistId, TrackDTO trackDTO) throws SQLException {
		trackMapper.addTrackToPlaylist(playlistId, trackAssembler.disassembleTrackDTO(trackDTO));
	}

	public void removeTrackFromPlaylist(Long playlistId, Long trackId) throws SQLException {
		trackMapper.removeTrackFromPlaylist(playlistId, trackId);
	}
}
