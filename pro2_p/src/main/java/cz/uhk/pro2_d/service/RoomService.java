package cz.uhk.pro2_d.service;

import cz.uhk.pro2_d.model.Room;

import java.util.List;

public interface RoomService {
    List<Room> getAllRooms();
    Room getRoomById(Long id);
    Room saveRoom(Room room);
    void deleteRoom(Long id);
}
