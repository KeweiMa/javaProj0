package dev.kma.services;

import org.json.JSONObject;
import dev.kma.dto.CustomerInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@Slf4j
public class ContentDisplayService {
    public String filterContent(JSONObject jsonObject) throws Exception{
        log.info("--> ");
        List<CustomerInfo> customerInfoList = new ArrayList<>();
        try{

        }catch(Exception e){
            log.error("error msg: {}", e.getMessage());
        }
        return "";
    }

}
