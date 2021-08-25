package com.yy.lightbulbroom.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yy.lightbulbroom.exception.RoomNotFoundException;
import com.yy.lightbulbroom.model.Room;
import com.yy.lightbulbroom.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.AccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

@Service
public class UserService {

    private final RoomRepository roomRepository;

    @Autowired
    public UserService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public boolean createRoom(Room room) {
        roomRepository.save(room);
        return true;
    }

    public List<Room> findAllRooms() {
        return roomRepository.findAll();
    }

    public Room checkAccessToTheRoom(Long id) throws RoomNotFoundException, IOException, AccessException {
        Room roomById = findRoomById(id);
        String userCountry = getUserCountry();

        return compareUserCountryWithRoomCountry(roomById, userCountry);
    }

    public Room compareUserCountryWithRoomCountry(Room roomById, String userCountry) throws AccessException {
        if (userCountry.equals(roomById.getCountry().getCountryName())) {
            return roomById;
        } else {
            throw new AccessException("Your country should match with room country");
        }
    }

    private Room findRoomById(Long id) throws RoomNotFoundException {
        return roomRepository.findById(id).orElseThrow(()
                -> new RoomNotFoundException("Room with id " + id + " not found"));
    }

    private String getUserCountry() throws IOException {
        URL url = new URL("http://api.sypexgeo.net/");
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            return new ObjectMapper()
                    .readTree(bufferedReader)
                    .get("country")
                    .get("name_en").asText();
        }
    }

    @Transactional
    public void updateRoom(Room room) {
        roomRepository.updateRoom(
                room.getId(),
                room.getRoomName(),
                room.getCountry(),
                room.isLight());
    }

}
