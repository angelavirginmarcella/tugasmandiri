import Network.ConnectURI;
import org.json.JSONArray;
import Model2.ResponModel;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class tampilanangel {
    private JTextField textMessage;
    private JTextField textStatus;
    private JTextField textComment;
    private JButton submitButton;
    private JButton cancelButton;
    private JPanel screen;
    private JFrame frame;
    public tampilanangel(){
        frame=new JFrame("TM1_22090017_AngelaVirginMarcella");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(440, 445));
        frame.setResizable(true);
        frame.add(screen);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()== submitButton){
                    try {
                        URL url = new URL("http://harber.mimoapps.xyz/api/getaccess.php");
                        HttpURLConnection connection =(HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");

                        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String inputLine;
                        StringBuffer response = new StringBuffer();
                        while ((inputLine = in.readLine())!=null){
                            response.append(inputLine);
                        }
                        in.close();

                        JSONArray jsonArray = new JSONArray(response.toString());

                        ArrayList<ResponModel>parsedList = new ArrayList<>();
                        for (int i = 0;i<jsonArray.length();i++){
                            ResponModel resModel = new ResponModel();
                            JSONObject myJSONObject = jsonArray.getJSONObject(i);
                            resModel.setMessage(myJSONObject.getString("message"));
                            resModel.setStatus(myJSONObject.getString("status"));
                            resModel.setComment(myJSONObject.getString("comment"));
                            parsedList.add(resModel);

                        }
                        for (int index = 0 ; index < parsedList.size();index++){
                            textMessage.setText(parsedList.get(index).getMessage());
                            textStatus.setText(parsedList.get(index).getStatus());
                            textComment.setText(parsedList.get(index).getComment());
                        }
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConnectURI koneksisaya = new ConnectURI();
                URL myAddress = koneksisaya.buildURL("http://harber.mimoapps.xyz/api/getaccess.php");
                String response = null;
                try {
                    response = koneksisaya.getResponseFromHttpUrl(myAddress);
                } catch (IOException  ex) {
                    throw new RuntimeException(ex);
                }
                System.out.println("");
                System.out.println(response);
                System.out.println("");
                System.out.println("");
                System.out.println("*  terimakasih  *");
                System.exit(0);
            }
        });
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new tampilanangel();
    }
}
