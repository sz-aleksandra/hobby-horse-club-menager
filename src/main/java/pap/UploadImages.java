package pap;

import pap.db.misc.Uploader;

public class UploadImages {
    public static void main(String[] args) throws Exception {
        Uploader.uploadImageToOffers(1, "src/main/resources/room1.jpg");
        Uploader.uploadImageToOffers(2, "src/main/resources/room2.jpg");
        Uploader.uploadImageToOffers(3, "src/main/resources/room3.jpg");
        Uploader.uploadImageToOffers(4, "src/main/resources/room4.jpg");
        Uploader.uploadImageToOffers(5, "src/main/resources/room5.jpg");
        Uploader.uploadImageToOffers(6, "src/main/resources/room6.jpeg");
        Uploader.uploadImageToOffers(7, "src/main/resources/room7.jpg");
        Uploader.uploadImageToOffers(8, "src/main/resources/room8.jpg");
        Uploader.uploadImageToOffers(9, "src/main/resources/room9.jpeg");
        Uploader.uploadImageToOffers(10, "src/main/resources/room10.jpg");
    }
}
