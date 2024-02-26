package IMADWRGH.server.Services;

import IMADWRGH.server.Repository.ServerRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import IMADWRGH.server.model.Server;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;
import java.util.Random;

import static IMADWRGH.server.enumeration.Status.SERVER_DOWN;
import static IMADWRGH.server.enumeration.Status.SERVER_UP;

@Service
@Slf4j
@Transactional
public class ServerService {
    private final ServerRepository serverRepository;

    @Autowired
    public ServerService(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    private String setServerImageUrl(){
        String [] images ={"server1.png","server2.png","server3.png","server4.png"};
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/server/image"
                +images[new Random().nextInt(4)]).toUriString();
    }

    public Server create(Server server){
        log.info("Saving new Server : {}", server.getName());
        server.setImageUrl(setServerImageUrl());
        return serverRepository.save(server);
    }

public Server ping(String ipAddress)throws IOException {
    log.info("Saving Server IP: {}", ipAddress);
    Server server=serverRepository.findByIpAddress(ipAddress);
    InetAddress address= InetAddress.getByName(ipAddress);
    server.setStatus(address.isReachable(10000)? SERVER_UP: SERVER_DOWN);
    serverRepository.save(server);
    return server;
}

    public List<Server> list(int limit){
        log.info("Fetching all servers");
        return  serverRepository.findAll(PageRequest.of(0,limit)).toList();
    }

    public Server get(Long id){
        log.info("Fetching server by id: {}",id);
        return  serverRepository.findById(id).get();
    }

    public Server update(Server server){
        log.info("Updating  server: {}",server.getName());
        return serverRepository.save(server);
    }

    public Boolean delete(Long id){
        log.info("Deleting server by id: {}",id);
         serverRepository.deleteById(id);
         return Boolean.TRUE;
    }



}
