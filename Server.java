package lesson1;

import java.util.ArrayList;
import java.util.List;



public class Server {
    private List<Client> clients;
    private ServerView serverView;
    private boolean isServerWorking;
    private Repository<String> repository;
    public Server(ServerView serverView, Repository<String> repository){
        this.serverView = serverView;
        this.repository = repository;
        clients = new ArrayList<>();
        serverView.setServer(this);
    }


    public boolean connectUser(Client client) {
            if(!isServerWorking){
                return false;
            }
            clients.add((Client) clients);
            showOnWindow(((Client) clients).getName() + "подключился к беседе");
            return true;
    }



    public String getHistory() {
        return repository.load();
    }


    public void sendMessage(String text) {
        if (!isServerWorking){
            return;
        }
        showOnWindow(text);
        answerAll(text);
        saveInHistory(text);
    }

    private void answerAll(String text){
        for (Client clients: clients){
            clients.serverAnswer(text);
        }
    }

    private void saveInHistory(String text){
        repository.save(text);
    }

    public void start(){
        if (isServerWorking){
            showOnWindow("Сервер уже был запущен");
        } else {
            isServerWorking = true;
            showOnWindow("Сервер запущен!");
        }
    }

    private void showOnWindow(String text) {
        serverView.sendMessage(text + "\n");
    }

    public void stop(){
        if (!isServerWorking){
            showOnWindow("Сервер уже был остановлен");
        } else {
            isServerWorking = false;
            while (!clients.isEmpty()){
                disconnectUser(clients.get(clients.size() - 1));
            }
            showOnWindow("Сервер остановлен!");
        }
    }

    public void disconnectUser(Client client){
        clients.remove((Client) clients);
        if (clients != null){
            ((Client) clients).disconnect();
            showOnWindow(((Client) clients).getName() + " отключился от беседы");
        }
    }
}
