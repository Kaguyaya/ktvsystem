package com.kaguya.ktvadmin.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("ktv_song")
public class KtvSong implements Serializable {
    @TableId
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
