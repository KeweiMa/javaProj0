package dev.kma.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import dev.kma.dto.ImportedRecord;
import dev.kma.services.ImportTemplateService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.google.gson.JsonObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@Api(tags = "/resource", value = "excelImport")
@RestController
@RequestMapping("/resource")
public class importTemplateController {
    @Autowired
    ImportTemplateService importTemplateService;

    /**
     * file import
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "excelImport", notes = "excelImport", httpMethod = "POST")
    @ApiImplicitParams(
            {@ApiImplicitParam(paramType = "query", name = "fileName", value = "uploaded file name", dataType = "String"),
                    @ApiImplicitParam(paramType = "query", name = "note", value = "note", dataType = "String")
            }
    )
    @RequestMapping(value="/importFile", method = RequestMethod.POST)
    public String importTemplate(@RequestBody JsonObject jsonObject) throws Exception {
        log.info(">===== start import file");
        Long startTime = System.currentTimeMillis();
        String fileName = jsonObject.get("fileName").getAsString();
        String note = jsonObject.get("note").getAsString();
        String resp = "";
        try{
            resp = importTemplateService.dealImportFile(fileName, note);
            long endSendTime = System.currentTimeMillis();
            long runSendTime = (endSendTime-startTime)/1000;
            log.info("finished, time over: {}", runSendTime);
        }catch(Exception e){
            log.error("%%%% get import file error: {}", e.getMessage());
        }
        return resp;
    }

    /**
     * download file
     * @param response
     * @param request
     * @return
     */
    @ApiOperation(value = "fileDownload", notes="fileDownload")
    @RequestMapping(value = "/downloadFile", method = RequestMethod.GET)
    @ResponseBody
    public String downloadFile(HttpServletResponse response, HttpServletRequest request){
        log.info(">=== download file start");
        long startTime = System.currentTimeMillis();
        String resp = "";
        try{
            importTemplateService.downloadFile(response);
            long endSendTime = System.currentTimeMillis();
            long runSendTime = (endSendTime-startTime)/1000;
            log.info("finished, time over: {}", runSendTime);
        }catch(Exception e){
            log.error("download file error: {}", e.getMessage());
            resp = e.getMessage();
        }
        return resp;
    }
}
