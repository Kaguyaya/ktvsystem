package com.kaguya.ktvadmin.service;

import com.kaguya.ktvadmin.dto.PageDto;
import com.kaguya.ktvadmin.pojo.KtvSong;
import com.kaguya.ktvadmin.vo.PageVo;

import java.io.File;
import java.util.List;

public interface KtvSongService {
   KtvSong setSongInfo(KtvSong ktvSong);
   List<KtvSong> getSongList();
   int deleteSong(KtvSong ktvSong);
   int updateSong(KtvSong ktvSong);
   PageVo<KtvSong> queryByPage(PageDto pageDto);
   PageVo<KtvSong> queryByPagelanguageandtype(PageDto pageDto);
   KtvSong queryById(String songid);
}
