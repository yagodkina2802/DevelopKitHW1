package lesson1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ClientGUI extends JFrame {
    private static final int WIDNH = 400;
    private static final int HEIGHT = 300;
    private ServerWindow server;
    private boolean connected;
    private String name;


    JTextArea log;
    JTextField tfIPAddress, tfPort, tfLogin, tfMessage;
    JPasswordField password;
    JButton btnLogin, btnSend;
    JPanel headerPanel;


    ClientGUI(ServerWindow server) {
        this.server = server;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(WIDNH, HEIGHT);
        setTitle("Chat Client");

        log.setEditable(false);
        JScrollPane scrollLog = new JScrollPane(log);
        add(scrollLog);


        setVisible(true);

    }

    private void connectToServer() {
        if (server.connectUser(this)) {
            appendLog("Вы успешно подключились! \n");
            headerPanel.setVisible(false);
            connected = true;
            name = tfLogin.getText();
            String log = server.getLog();
            if (log != null) {
                appendLog(log);
            }
        } else {
            appendLog("Подключение не удалось ");
        }
    }

    public void answer(String text) {
        appendLog(text);
    }

    public void disconnectFromServer() {
        if (connected) {
            headerPanel.setVisible(true);
            connected = false;
            server.disconnectUser(this);
            appendLog("Вы были отключены от сервера!");
        }
    }
    public void message(){
        if(connected){
            String text = tfMessage.getText();
            if(!text.equals("")){
                server.message(name + ": " + text);
                tfMessage.setText("");
            }
        }else{
            appendLog("Нет подключения к серверу");
        }
    }
    private void appendLog(String text){
        log.append(text + "\n");
    }
    private Component createHeaderPanel(){
        headerPanel = new JPanel(new GridLayout(2, 3));
        tfIPAddress = new JTextField("127.0.0.1");
        tfPort = new JTextField("8189");
        tfLogin = new JTextField(("Ivanod Ivan"));
        password = new JPasswordField("123456");
        btnLogin = new JButton("login");
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectToServer();
            }
        });
        headerPanel.add(tfIPAddress);
        headerPanel.add(tfPort);
        headerPanel.add(new JPanel());
        headerPanel.add(tfLogin);
        headerPanel.add(password);
        headerPanel.add(btnLogin);

        return headerPanel;
    }
    private Component createFooter(){
        JPanel panel = new JPanel(new BorderLayout());
        tfMessage = new JTextField();
        tfMessage.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar() == '\n'){
                    message();
                }
            }
        });
        btnSend = new JButton("send");
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                message();
            }
        });
        panel.add(tfMessage);
        panel.add(btnSend, BorderLayout.EAST);
        return panel;
    }
    @Override
    protected void processWindowEvent(WindowEvent e){
        if(e.getID() == WindowEvent.WINDOW_CLOSING){
            disconnectFromServer();
        }
        super.processWindowEvent(e);
    }
}