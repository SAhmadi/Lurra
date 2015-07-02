package Main;
//import com.restfb.*;
//import com.restfb.types.FacebookType;
//import com.restfb.types.GraphResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by Vanessa on 29.06.2015.
 */
//public class FacebookPost{
   /* public static void FbPost(String access_token) {

        System.out.println("post aufgerufen " + access_token);
        //FacebookClient facebookClient = new DefaultFacebookClient(access_token);
        //FacebookClient publicOnlyFacebookClient = new DefaultFacebookClient();
        FacebookClient facebookClient = new DefaultFacebookClient(access_token, "02334c72a05619ce33d416ecb3830d00");
        try {
            System.out.println("message " );
            //Der Pfad an dem der Screenshot gespeichert wurde stimmt noch nicht
            FacebookType publishPhotoResponse = facebookClient.publish("me/photos", FacebookType.class,
                    BinaryAttachment.with("screeni.png", new FileInputStream(new File("res/img/Screenshots/screeni.png"))),
                    Parameter.with("message", "Screenshot von Lurra"));
            System.out.println("post message " + publishPhotoResponse);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }*/
//}
