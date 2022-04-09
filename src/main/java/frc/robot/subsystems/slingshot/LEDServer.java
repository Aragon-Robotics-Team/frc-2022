// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.slingshot;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/** Add your docs here. */
public class LEDServer {
  ServerSocket m_server;
  int m_progress;
  ClientThread m_client = new ClientThread();

  private static LEDServer m_instance = null;

  private LEDServer() {
  }

  public static LEDServer getInstance() {
    if (m_instance == null) {
      m_instance = new LEDServer();
    }
    return m_instance;
  }

  public void startServer(int port) {
    try {
      m_server = null;
      m_server = new ServerSocket(port);
      m_client.start();
      System.out.println("LED SERVER ON");
    } catch (IOException e) {
      System.out.println("Unable to init LED server");
    }
  }

  public void setProgress(int prog) {
    m_progress = prog;
  }

  public class ClientThread extends Thread {
    @Override
    public void run() {
      Socket m_conn = null;
      DataOutputStream m_output = null;
      String m_data;

      // Client loop.
      for (;;) {
        // Poll for a connection.
        while (m_conn == null) {
          try {
            m_conn = m_server.accept();
            System.out.println("LED SERVER CONNECTED");
          } catch (IOException e) {
            m_conn = null;
          }

          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            System.out.println("ERROR");
          }
        }

        // Clear output stream.
        while (m_output == null) {
          try {
            m_output = new DataOutputStream(m_conn.getOutputStream());
          } catch (IOException e) {
            m_output = null;
          }
        }
        m_data = Integer.toString(m_progress);

        try {
          m_output.writeUTF(m_data);
          m_output.flush();
        } catch (IOException e) {
          // Disconnect.
          try {
            m_output.close();
            m_conn.close();
            m_conn = null;
            m_output = null;
          } catch (IOException _e) {
            System.out.println("ERROR");
          }
        }

        try {
          Thread.sleep(50);
        } catch (InterruptedException e) {
          System.out.println("ERROR");
        }
      }
    }
  }
}
