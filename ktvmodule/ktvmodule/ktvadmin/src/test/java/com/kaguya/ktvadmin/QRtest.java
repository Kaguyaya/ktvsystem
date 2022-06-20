package com.kaguya.ktvadmin;

import com.kaguya.ktvadmin.utils.QRCodeUtil;
import org.junit.jupiter.api.Test;

import java.io.File;

public class QRtest {
    @Test
    public void test(){
        QRCodeUtil.createCodeToFile("666",new File("D://QRTEST"),"QRcode2.png");
    }
}
