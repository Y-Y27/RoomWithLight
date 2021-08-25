package com.yy.lightbulbroom;

import com.yy.lightbulbroom.model.Country;
import com.yy.lightbulbroom.model.Room;
import com.yy.lightbulbroom.repository.CountryRepository;
import com.yy.lightbulbroom.repository.RoomRepository;
import com.yy.lightbulbroom.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.expression.AccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(SpringExtension.class)
public class UnitTests {

    private RoomRepository roomRepository = mock(RoomRepository.class);
    private CountryRepository countryRepository = mock(CountryRepository.class);
    private UserService userService = new UserService(roomRepository);

    @Test
    public void addRoomTest() {
        Room room = new Room();
        Country country = new Country();
        room.setRoomName("TestUnit123");
        room.setCountry(country);
        boolean isCreated = userService.createRoom(room);

        Assert.assertTrue(isCreated);
        Assert.assertNotNull(room.getRoomName());
        Assert.assertNotNull(room.getCountry());
        Assert.assertFalse(room.isLight());

        Mockito.verify(roomRepository, Mockito.times(1)).save(room);
    }

    @Test(expected = AccessException.class)
    public void compareUserCountryWithRoomCountry_Should_Throw_Exception() throws AccessException {
        Country country = new Country(1L, "Spain", null);
        Room room = new Room(1L, "TestRoom", country, false);
        given(userService.compareUserCountryWithRoomCountry(room, anyString()))
                .willThrow(new AccessException("Your country should match with room country"));
    }

    @Test
    public void compareUserCountryWithRoomCountry_Should_Return_Room() throws AccessException {
        Country country = new Country(2L, "Spain", null);
        Room room = new Room(2L, "TestRoom", country, false);

        given(roomRepository.findById(2L)).willReturn(Optional.of(room));

        Room room1 = userService.compareUserCountryWithRoomCountry(room, "Spain");

        Assert.assertEquals(room, room1);
    }

    @Test
    public void findByRoomName_Should_Return_Null() {
        Room byRoomName = roomRepository.findByRoomName(anyString());
        Assert.assertNull(byRoomName);
    }
}
