package com.kaguya.ktvadmin.service;

import com.kaguya.ktvadmin.pojo.KtvUser;
import com.kaguya.ktvadmin.pojo.KtvUserSong;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface KtvUserSongService {
    List<KtvUserSong> getUserTimes(KtvUserSong ktvUserSong);
    KtvUserSong setUserSong(KtvUserSong ktvUserSong);
    List<KtvUserSong> getAllUserTimes();
    List<KtvUserSong> getUserTimes();
    List<KtvUserSong> getSongTimes();
    HashMap<String,Double> getUserCount();
    Double getAllSongtimes();
    List<KtvUserSong>getAll();
}
