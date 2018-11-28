package ca.ualberta.cmput301f18t11.medicam.controllers;

import android.media.Image;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class ImageStorageController implements Runnable {
    private File userImageFd;
    private final Image memberImage;

    public ImageStorageController(Image image,File fd){
        memberImage = image;
        userImageFd = fd;
    }
    @Override
    public void run() {
        ByteBuffer byteBuffer = memberImage.getPlanes()[0].getBuffer();
        byte[] bytes = new byte[byteBuffer.remaining()];
        byteBuffer.get(bytes);

        FileOutputStream fileOPStream = null;

        try {
            fileOPStream = new FileOutputStream(userImageFd);
            fileOPStream.write(bytes);
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            memberImage.close();
            if (fileOPStream != null){
                try {
                    fileOPStream.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}