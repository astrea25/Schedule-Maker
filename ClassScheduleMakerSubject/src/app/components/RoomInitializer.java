package app.components;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.entities.Room;
import app.repositories.RoomRepository;

@Component

public class RoomInitializer {
	@Autowired
	private RoomRepository roomRepo;
	
	@PostConstruct
	public void init() {
		if(roomRepo.count()==0) {
			
			Room room[] = new Room[8];
			for(int x = 0; x <= 7 ; x++) {
				room[x] = new Room() ; //
				
			}
			
			
			room[0].setRoomName("CTC215");
			room[1].setRoomName("CTC216");
			room[2].setRoomName("CTC214");
			room[3].setRoomName("SOM301");
			room[4].setRoomName("SOM302");
			room[5].setRoomName("SOM303");
			room[6].setRoomName("SECA102");
			room[7].setRoomName("SECA101");


			
			for(int x = 0; x <= 7; x++) {
				roomRepo.save(room[x]);
			}
		

		}
	}
}
