package com.kaguya.ktvadmin.service;

import com.kaguya.ktvadmin.pojo.UserRoomUsering;

import java.util.List;

public interface UserRoomUseringService {
     UserRoomUsering setUserRoomUsering(UserRoomUsering userRoomUsering);
     List<UserRoomUsering> getUserRoomUseringList();
     List<UserRoomUsering> getUseringRoomList();
     UserRoomUsering changeUserRoomState(UserRoomUsering userRoomUsering);
}
