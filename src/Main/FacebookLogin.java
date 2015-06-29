package Main;

/**
 * Created by Vanessa on 28.06.2015.
 */


import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserAdapter;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserNavigationEvent;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

public class FacebookLogin extends javax.swing.JFrame {
//App "Lurra" die auf Facebook erstellt wurde
    public static String API_KEY = "1453078924988824";
    public static String SECRET = "02334c72a05619ce33d416ecb3830d00";
    public static String request1 = "https://graph.facebook.com/oauth/authorize?"
            + "client_id="
            + API_KEY
            + "&redirect_uri=http://www.facebook.com/connect/login_success.html "
            + "&scope=publish_actions,user_photos";


    public static String request2 = "https://graph.facebook.com/oauth/access_token?"
            + "client_id="
            + API_KEY
            + "&redirect_uri=http://www.facebook.com/connect/login_success.html&"
            + "client_secret=" + SECRET + "&code=";

    public static String access_token = "";
    public static boolean request1Done = false;
    public static boolean request2Done = false;
    static FacebookLogin apiLogin;

    public FacebookLogin() {
        initComponents();
        System.out.println("Browser gestartet");
    }
    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        LoginFacebook = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        LoginFacebook.setText("Post to Facebook");
        LoginFacebook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoginFacebookActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LoginFacebook)
                                .addContainerGap(109, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 247, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(LoginFacebook)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );

        pack();
    }
    private void LoginFacebookActionPerformed(java.awt.event.ActionEvent evt) {
        NativeInterface.open();
        final JFrame authFrame = new JFrame();
        JPanel webBrowserPanel = new JPanel(new BorderLayout());
        final JWebBrowser webBrowser = new JWebBrowser();
        webBrowser.navigate(request1);
        webBrowser.addWebBrowserListener(new WebBrowserAdapter() {
            @Override
            public void locationChanged(WebBrowserNavigationEvent e) {
                super.locationChanged(e);
                if (!request1Done) {
                    if (e.getNewResourceLocation().contains("http://www.facebook.com/connect/login_success.html?code=")) {
                        String[] splits = e.getNewResourceLocation().split("=");
                        String stage2temp = request2 + splits[1];
                        System.err.println("URL location " + stage2temp);
                        webBrowser.navigate(stage2temp);
                        access_token = splits[2];
                        FacebookPost.FbPost(access_token);
                        //System.out.println("This is what we looking for" + splits[2]);

                        request1Done = true;
                        if (request1Done == true) {
                            System.out.println("firsrequest done");
                        }

                    }
                } else if (!request2Done) {
                    System.out.println(webBrowser.getHTMLContent());
                    StringReader readerSTR = new StringReader(webBrowser.getHTMLContent());
                    HTMLEditorKit.ParserCallback callback
                            = new HTMLEditorKit.ParserCallback() {
                        public void handleText(char[] data, int pos) {
                            //System.out.println(data);
                            String string = new String(data);

                            //System.out.println("Main string : " + data.toString());
                            String[] temp1 = string.split("&");

                            String[] temp2 = temp1[0].split("=");


                            //out.println("Published photo ID: " + publishPhotoResponse.getId());
                            /*String token = access_token;
                            String text_message = "Lurra";
                            String photo_url = "res/img/Screenshots/screeni.png";
                            StringBuilder sb = new StringBuilder("access_token=");

                            try {
                                sb.append(URLEncoder.encode(token, "UTF-8"));
                            } catch (UnsupportedEncodingException e1) {
                                e1.printStackTrace();
                            }
                            sb.append("&message=");
                            try {
                                sb.append(URLEncoder.encode(text_message, "UTF-8"));
                            } catch (UnsupportedEncodingException e1) {
                                e1.printStackTrace();
                            }
                            sb.append("&url=");
                            try {
                                sb.append(URLEncoder.encode(photo_url, "UTF-8"));
                            } catch (UnsupportedEncodingException e1) {
                                e1.printStackTrace();
                            }
                            System.out.println("string " + sb);

                            try {
                                String feedUrl = "https://graph.facebook.com/me/photos";
                                URL url;
                                url = new URL(feedUrl);
                                System.out.println(url);
                                HttpURLConnection connection;
                                connection = (HttpURLConnection) url.openConnection();
                                connection.setDoInput(true);
                                connection.setDoOutput(true);
                                connection.setRequestMethod("POST");
                                connection.setRequestProperty("Content-Type", "multipart/form-data");
                                connection.setRequestProperty("Content-Length", "" + sb.toString().length());
                                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream());
                                outputStreamWriter.write(sb.toString());
                                outputStreamWriter.flush();
                                outputStreamWriter.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }*/

                            //System.out.println("access_token is here" + access_token);

                            //System.out.println("length of token " + temp2.length);
                            //System.err.println("Temp 0" + temp2[0]);
                        }
                    };
                    try {
                        new ParserDelegator().parse(readerSTR, callback, false);

                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    // authFrame.dispose();

                }

            }
        });
        webBrowserPanel.add(webBrowser, BorderLayout.CENTER);
        authFrame.add(webBrowserPanel);
        authFrame.setSize(400, 500);
        authFrame.setVisible(true);

    }

    public static void Login() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FacebookLogin.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FacebookLogin.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FacebookLogin.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FacebookLogin.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }



        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                apiLogin = new FacebookLogin();
                apiLogin.setVisible(true);
            }
        });
    }
    private javax.swing.JButton LoginFacebook;
    private javax.swing.JPanel jPanel1;
}




