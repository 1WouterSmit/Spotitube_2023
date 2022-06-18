/* package JSONDTO;

import Domain.Playlist;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlaylistAssemblerTest {
	PlaylistAssembler assembler = new PlaylistAssembler();

	@Test
	public void assemblePlaylistDTO() {
		// Arrange
		Playlist playlist = new Playlist(101, "testPlaylist01", "owner67");
		// Act
		PlaylistDTO pdto = assembler.assemblePlaylistDTO(playlist);
		// Assert
		assertEquals(101, pdto.getId());
		assertEquals("testPlaylist01", pdto.getName());
		assertTrue(pdto.isOwner());
		assertEquals(0, pdto.getTracks().length);
	}

	@Test
	public void disassemblePlaylistDTO() {
		// Arrange
		PlaylistDTO pdto = new PlaylistDTO(102, "testPlaylist02", true, new TrackDTO[] {});
		// Act
		Playlist playlist = assembler.disassemblePlaylistDTO(pdto);
		// Assert
		assertEquals(102, playlist.getID());
		assertEquals("testPlaylist02", playlist.getName());
	}
}*/