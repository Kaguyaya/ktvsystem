package com.kaguya.ktvadmin.service.impl;

import com.kaguya.ktvadmin.mapper.KtvRoomMapper;
import com.kaguya.ktvadmin.pojo.KtvRoom;
import com.kaguya.ktvadmin.service.KtvRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class KtvRoomServiceImpl implements KtvRoomService {
    @Autowired
    KtvRoomMapper ktvRoomMapper;
    @Override
    public KtvRoom createRoom(KtvRoom ktvRoom) {
        int insert = ktvRoomMapper.insert(ktvRoom);
        return ktvRoom;
    }

    @Override
    public List<KtvRoom> getRoomList() {
        List<KtvRoom> ktvRooms = ktvRoomMapper.selectList(null);

        for (KtvRoom room:ktvRooms
             ) {
            if (room.getRoomEndtime()==null){
                room.setRoomIsopen(false);
                room.setRoomUsetime(0);
                continue;
            }
            if ((int)(new Date().getTime())>=(int)(room.getRoomEndtime().getTime())){
                room.setRoomIsopen(false);
                room.setRoomUsetime(0);
            }
            ktvRoomMapper.updateById(room);
        }
        return ktvRooms;
    }

    @Override
    public KtvRoom searchRoom(String ktvId) {
        KtvRoom ktvRoom = ktvRoomMapper.selectById(ktvId);
        return ktvRoom;
    }

    @Override
    public KtvRoom startRoom(KtvRoom ktvRoom) {
        int i = ktvRoomMapper.updateById(ktvRoom);
        return ktvRoom;
    }

    @Override
    public KtvRoom closeRoom(KtvRoom ktvRoom) {
        ktvRoom.setRoomOpentime(null);
        ktvRoom.setRoomEndtime(null);
        ktvRoom.setRoomIsopen(false);
        ktvRoom.setRoomUsetime(0);
        int update = ktvRoomMapper.updateById(ktvRoom);
        return ktvRoom;
    }


}
