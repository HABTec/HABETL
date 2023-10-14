package com.habtech.ETLHabtech.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.habtech.ETLHabtech.models.*;
import com.habtech.ETLHabtech.services.*;
import com.habtech.ETLHabtech.transformer.Transformer;
import jakarta.persistence.Column;
import jakarta.transaction.Transactional;
import jakarta.websocket.server.PathParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.springframework.http.*;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/user/connection")
public class ConnectionController {
    private final ConnectionService connectionService;
    private final DestinationService destinationService;
    private final SourceService sourceService;

    private final StreamService streamService;
    private final TableColumnService tableColumService;

    private final JobHistoryService jobHistoryService;

    private final Logger logger = LoggerFactory.getLogger(ConnectionController.class);

    public ConnectionController(ConnectionService connectionService, DestinationService destinationService, SourceService sourceService, StreamService streamService, TableColumnService tableColumService, JobHistoryService jobHistoryService) {
        this.connectionService = connectionService;
        this.destinationService = destinationService;
        this.sourceService = sourceService;
        this.streamService = streamService;
        this.tableColumService = tableColumService;
        this.jobHistoryService = jobHistoryService;
    }

    @GetMapping()
    public String getAllConnection(Model model) {
        model.addAttribute("connections", connectionService.getAllConnection());
        return "user/connection/index";
    }

    @GetMapping("create")
    public String createConnection(Model model) {
        model.addAttribute("sources", sourceService.getAllSources());
        model.addAttribute("destinations", destinationService.getAllDestinations());
        return "user/connection/create";
    }

    @PostMapping("store")
    public String createConnection(@ModelAttribute("connection") Connection connection,
                                   @RequestParam("source_id") long source_id,
                                   @RequestParam("destination_id") long destination_id) {
        logger.debug(connection.toString());
        connection.setDestination(destinationService.getById(destination_id));
        connection.setSource(sourceService.getById(source_id));
        connectionService.createConnection(connection);
        return "redirect:/user/connection";
    }

    @GetMapping("{connection_id}")
    public String show(@PathVariable("connection_id") Long id, Model model) {
        Connection connection = connectionService.getById(id);
        model.addAttribute("connection", connection);
        return "user/connection/show";
    }

    @PostMapping("{connection_id}/stream")
    public String postStream(@PathVariable("connection_id") Long connectionId, @ModelAttribute("stream") Stream stream, RedirectAttributes redirectAttributes) {
        try {
            // try sending request to the end point
            Connection connection = connectionService.getById(connectionId);
            Source source = connection.getSource();
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(source.getUsername(), source.getPassword());
            HttpEntity<HttpHeaders> request = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(stream.getURL(), HttpMethod.GET, request, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {

                ObjectMapper mapper = new ObjectMapper();
                try {
                    JsonNode root = mapper.readTree(response.getBody());
                    JsonNode object;

                    if (root.has("headers") && root.has("rows")){
                        stream.setResultType(ResultType.DHIS_HEADER);
                        object = root.get("headers");

                        stream.setResultObject(object.toString());
                        streamService.save(stream);

                        for (int i = 0; i < object.size(); i++) {
                            JsonNode e = object.get(i);
                            String name = e.has("column")?e.get("column").asText():e.get("name").asText();
                            TableColumn column = new TableColumn(name, name.toLowerCase(), false, "varchar(255)", "/", stream);
                            tableColumService.save(column);
                        }

                    }else{

                        object = root.at(stream.getResultPath());

                        if (object.isArray()) {
                            redirectAttributes.addFlashAttribute("result", object.get(0).toString());
                            object = object.get(0);
                        }

                        stream.setResultObject(object.toString());
                        streamService.save(stream);

                        Iterator<String> keyIterator = object.fieldNames();
                        keyIterator.forEachRemaining(e -> {
                            TableColumn column = new TableColumn(e, e, false, "varchar(255)", "/", stream);
                            tableColumService.save(column);
                        });
                    }
                    redirectAttributes.addFlashAttribute("result", object.toString());
                    redirectAttributes.addFlashAttribute("message", "Stream created!");
                    return "redirect:/user/connection/" + connectionId + "/stream/" + stream.getId();

                } catch (JsonProcessingException e) {
                    redirectAttributes.addFlashAttribute("error", "Invalid response message! "+e.toString());
                }

            }else{
                redirectAttributes.addFlashAttribute("stream",stream);
                redirectAttributes.addFlashAttribute("result",response.getStatusCode());
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error encountered! " + e.toString());
        }
        return "redirect:/user/connection/" + connectionId;
    }

    @GetMapping("{connection_id}/stream/{stream_id}")
    public String showStream(@PathVariable("connection_id") Long connection_id, @PathVariable("stream_id") long stream_id, Model model) {
        model.addAttribute("stream", streamService.getById(stream_id));
        return "user/stream/show";
    }
    @PostMapping("{connection_id}/stream/{stream_id}/update")
    @Transactional
    public String updateStream(@PathVariable("connection_id") Long connection_id, @PathVariable("stream_id") long stream_id, @ModelAttribute("stream") Stream streamTemp) {
        Stream stream = streamService.getById(stream_id);
        streamTemp.setConnection(stream.getConnection());
        streamTemp.setTableColumns(stream.getTableColumns());
        streamTemp.setResultObject(stream.getResultObject());
        streamService.save(streamTemp);
        logger.debug("from update path "+ streamTemp.toString());
        return "redirect:/user/connection/"+connection_id+"/stream/"+stream_id;
    }

    @GetMapping("{connection_id}/stream/{stream_id}/delete")
    public String deleteStream(@PathVariable("stream_id") Long id, @PathVariable("connection_id") Long connectionId) {
        streamService.delete(id);
        return "redirect:/user/connection/" + connectionId;
    }

    @PostMapping("/{connection_id}/stream/{stream_id}/columns")
    public String updateColumns(@PathVariable("stream_id") long stream_id,
                                @PathVariable("connection_id") long connection_id,
                                @RequestParam("id[]") long[] ids,
                                @RequestParam("name[]") String[] names,
                                @RequestParam("targetName[]") String[] targetNames,
                                @RequestParam("dataType[]") String[] dataTypes,
                                @RequestParam("path[]") String[] paths,
                                @RequestParam(value = "disabled[]", required = false) Long[] disabled,
                                RedirectAttributes redirectAttributes
    ){
        try {
            Stream stream = streamService.getById(stream_id);
            ArrayList<TableColumn> tableColumns = new ArrayList<>();
            for (int i = 0; i < ids.length; i++) {
                int finalI = i;
                boolean isDisabled = disabled != null ?!Arrays.stream(disabled).anyMatch(e->ids[finalI]==e):true;
                tableColumns.add(new TableColumn(ids[i], names[i], targetNames[i],isDisabled, dataTypes[i], paths[i], stream));
            }
            tableColumService.saveAll(tableColumns);
            redirectAttributes.addFlashAttribute("message", "Column information successfully updated");
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("message","something went wrong");
            redirectAttributes.addFlashAttribute("error",e.toString());
        }
        return "redirect:/user/connection/"+connection_id+"/stream/"+stream_id;
    }


    @GetMapping("{connection_id}/sync")
    public String sync(@PathVariable("connection_id") long connection_id, RedirectAttributes redirectAttributes){

        Connection connection = connectionService.getById(connection_id);
        Transformer transformer = new Transformer(connection);
        try {
            transformer.transform();
            String message = "Sync completed successfully";
            redirectAttributes.addFlashAttribute("message",message);
            connection.setLastSync(Instant.now());
            connectionService.createConnection(connection);
            jobHistoryService.save(new JobHistory(Instant.now(),true,message,connection));
        }catch (Exception e){
            jobHistoryService.save(new JobHistory(Instant.now(),false,e.toString().substring(0,Math.min(e.toString().length(),255)),connection));
            redirectAttributes.addFlashAttribute("error",e.toString());
        }
        return "redirect:/user/connection/"+connection_id;
    }

}
