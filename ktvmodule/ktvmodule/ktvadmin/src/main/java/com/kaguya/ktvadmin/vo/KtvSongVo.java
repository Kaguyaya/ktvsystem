package com.kaguya.ktvadmin.vo;

import lombok.Data;

import java.util.Date;
@Data
public class KtvSongVo {
    private String songId;
    private String songName;
    private String songUrl;
    private int songLength;
    private String songAuthor;
    private String songAlbum;
    private String songType;
    private String songLanguage;
    private String songSinger;
    private String songLyricist;
    private Date songCreatetime;
    private Date songUpdatetime;
}
