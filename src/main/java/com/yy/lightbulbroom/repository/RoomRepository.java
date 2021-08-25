package com.yy.lightbulbroom.repository;

import com.yy.lightbulbroom.model.Country;
import com.yy.lightbulbroom.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Modifying
    @Query("update Room r set r.roomName = :roomName, " +
            "r.country = :country , " +
            "r.light = :light where r.id = :id")
    void updateRoom(@Param(value = "id") long id,
                    @Param(value = "roomName") String roomName,
                    @Param(value = "country") Country country,
                    @Param(value = "light") boolean light);

    Room findByRoomName(String roomName);
}
