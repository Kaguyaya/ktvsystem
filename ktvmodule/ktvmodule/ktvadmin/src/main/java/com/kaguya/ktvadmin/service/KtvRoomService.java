package com.kaguya.ktvadmin.service;

import com.kaguya.ktvadmin.pojo.KtvRoom;
import com.kaguya.ktvadmin.vo.KtvRoomVo;

import java.util.List;

public interface KtvRoomService {
    KtvRoom createRoom(KtvRoom ktvRoom);
    List<KtvRoom> getRoomList();
    KtvRoom searchRoom(String ktvId);
    KtvRoom startRoom(KtvRoom ktvRoom);
    KtvRoom closeRoom(KtvRoom ktvRoom);
}
