package IMADWRGH.server.Controller;

import IMADWRGH.server.Services.ServerService;
import IMADWRGH.server.enumeration.Status;
import IMADWRGH.server.model.Response;
import IMADWRGH.server.model.Server;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@RequestMapping("/server")
public class ServerController {

    private final ServerService serverService;

    @Autowired
    public ServerController(ServerService serverService) {
        this.serverService = serverService;
    }

    @GetMapping("/list")
    public ResponseEntity<Response> getAllServers(){
        return ResponseEntity.ok(
                Response.builder()
                        .time(now())
                        .data(Map.of("servers",serverService.list(30)))
                        .message("Server retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }
    @GetMapping("/ping/{ipAddress}")
    public ResponseEntity<Response> pingServer(@PathVariable("ipAddress") String ipAddress) throws IOException {
        Server server= serverService.ping(ipAddress);
        return ResponseEntity.ok(
                Response.builder()
                        .time(now())
                        .data(Map.of("server",server))
                        .message(server.getStatus() == Status.SERVER_UP?"Ping success":"Ping failed")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @PostMapping("/save")
    public ResponseEntity<Response> createServer(@RequestBody @Valid Server server) {
        return ResponseEntity.ok( // we can use * return ResponseEntity.created(URl)*
                Response.builder()
                        .time(now())
                        .data(Map.of("server",serverService.create(server)))
                        .message("Server created")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> pingServer(@PathVariable("id") long id) {
        return ResponseEntity.ok(
                Response.builder()
                        .time(now())
                        .data(Map.of("server", serverService.get(id)))
                        .message("Server retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteServer(@PathVariable("id") long id) {
        return ResponseEntity.ok(
                Response.builder()
                        .time(now())
                        .data(Map.of("server", serverService.delete(id)))
                        .message("Server deleted")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @Value("${images.path}")
    private String imagesPath;

    @GetMapping(path = "/image/{fileName}", produces =IMAGE_PNG_VALUE )
    public byte[] getServerImage(@PathVariable("fileName") String fileName) throws IOException {
return Files.readAllBytes(Paths.get(imagesPath +fileName));
    }
}
