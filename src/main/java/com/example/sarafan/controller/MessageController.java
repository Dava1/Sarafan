package com.example.sarafan.controller;

import com.example.sarafan.exeptions.NotFoundExeption;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("message")
public class MessageController {
    private int counter = 4;
    private List<Map<String ,String>> messages = new ArrayList<Map<String,String>>(){{
        add(new HashMap<String, String>(){{put("id","1");put("text","First message");}});
        add(new HashMap<String, String>(){{put("id","2");put("text","second message");}});
        add(new HashMap<String, String>(){{put("id","3");put("text","third message");}});
    }};

    @GetMapping
    public List<Map<String,String>> list(){
        return messages;
    }

    @GetMapping("{id}")
    public Map<String,String> getMessageById(@PathVariable String id){
        return getMessage(id);
    }

    private Map<String, String> getMessage(String id) {
        return messages.stream().
                filter(mes -> mes.get("id").equals(id)).
                findFirst().
                orElseThrow(NotFoundExeption::new);
    }

    @PostMapping
    public Map<String,String> sendMessage(@RequestBody Map<String,String> message){
       message.put("id",String.valueOf(counter++));
       messages.add(message);
       return message;
    }

    @PutMapping("{id}")
    public Map<String, String> updateMessages(@PathVariable String id, @RequestBody Map<String,String> message){
        Map<String,String> messageFromDb = getMessage(id);
        messageFromDb.putAll(message);
        messageFromDb.put("id",id);
        return messageFromDb;
    }

    @DeleteMapping("{id}")
    public void deleteMessage(@PathVariable String id){
        Map<String,String> message = getMessage(id);
        messages.remove(message);
     }
}
