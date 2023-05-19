package dev.kma.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import dev.kma.services.ImportTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.google.gson.JsonObject;

@Api(tags = "/template", value = "模板导入")
@RestController
@RequestMapping("/template")
public class importTemplateController {
    @Autowired
    ImportTemplateService importTemplateService;

    @ApiOperation(value = "模板导入", notes = "模板导入", httpMethod = "POST")
    public String importTemplate(@RequestBody JsonObject jsonObject) throws Exception {
        // log.info(">===== start");
        String fileName = jsonObject.get("fileName").toString();
        String respMsg = importTemplateService.dealTemplate("");
        return respMsg;
    }
}
