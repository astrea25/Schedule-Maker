package app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.entities.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
	
	public List<Room> findAll();
	public Room findByRoomPk(Long roomPk);
	public void deleteByRoomPk(Long roomPk);
	public Room findByRoomName(String roomName);

}
