package app.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Room {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
    private Long roomPk;

	@Column(unique=true)
	@NotNull
    private String roomName;

	public Long getRoomPk() {
		return roomPk;
	}

	public void setRoomPk(Long roomPk) {
		this.roomPk = roomPk;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	@Override
	public String toString() {
		return "Room [roomPk=" + roomPk + ", roomName=" + roomName + "]";
	}
}
