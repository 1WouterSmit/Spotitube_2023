package DataAccess.DataMapper;

import Domain.Song;
import org.junit.Test;

import static org.junit.Assert.*;

public class SongMapperTest {

	@Test
	public void find() {
		SongMapper songMapper = new SongMapper();
		Song song = songMapper.find(1);
		assertEquals("Song for someone", song.getTitle());
		assertEquals("The Frames", song.getPerformer());
		assertEquals(350, song.getDuration());
		assertEquals("The cost", song.getAlbum());
	}

	@Test
	public void delete() {
	}
}