import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    // Replace with your WiFi microcontroller's IP address
    private static final String WIFI_MICROCONTROLLER_IP = "192.168.1.200";

    // Simulated 7-segment display TextViews
    private TextView segmentA, segmentB, segmentC, segmentD, segmentE, segmentF, segmentG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize TextViews for simulated 7-segment display
        segmentA = findViewById(R.id.segmentA);
        segmentB = findViewById(R.id.segmentB);
        segmentC = findViewById(R.id.segmentC);
        segmentD = findViewById(R.id.segmentD);
        segmentE = findViewById(R.id.segmentE);
        segmentF = findViewById(R.id.segmentF);
        segmentG = findViewById(R.id.segmentG);

        // Setup buttons for drone control
        Button btnUp = findViewById(R.id.btnUp);
        Button btnDown = findViewById(R.id.btnDown);

        // Example: Send commands to WiFi microcontroller on button click
        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommandToDrone("U"); // Example command: Up
            }
        });

        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommandToDrone("D"); // Example command: Down
            }
        });
    }

    private void sendCommandToDrone(final String command) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    // Construct URL for HTTP GET request
                    URL url = new URL("http://" + WIFI_MICROCONTROLLER_IP + "/control?cmd=" + command);

                    // Create connection
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");

                    // Get the server response
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    // Update UI with response (simulated 7-segment display update)
                    updateSegmentDisplay(command);

                    // Disconnect
                    conn.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void updateSegmentDisplay(final String command) {
        // Update simulated 7-segment display based on command received
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (command) {
                    case "U":
                        segmentA.setText("U");
                        segmentB.setText("P");
                        segmentC.setText("");
                        segmentD.setText("");
                        segmentE.setText("");
                        segmentF.setText("");
                        segmentG.setText("");
                        break;
                    case "D":
                        segmentA.setText("D");
                        segmentB.setText("O");
                        segmentC.setText("W");
                        segmentD.setText("N");
                        segmentE.setText("");
                        segmentF.setText("");
                        segmentG.setText("");
                        break;
                    // Add more cases as needed for other commands
                    default:
                        // Clear display or handle other commands
                        segmentA.setText("");
                        segmentB.setText("");
                        segmentC.setText("");
                        segmentD.setText("");
                        segmentE.setText("");
                        segmentF.setText("");
                        segmentG.setText("");
                        break;
                }
            }
        });
    }
          }
