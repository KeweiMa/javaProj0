package dev.kma.controller;

import org.json.JSONObject;
import dev.kma.services.ContentDisplayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(tags = "/contentDisplay", value = "contentDisplay")
@RestController
@RequestMapping("/api/v1/mystias")
public class displayController {
    @Autowired
    ContentDisplayService contentDisplaySvc;

    /**
     * 界面筛选入口，按出现地点，稀客/普客/特殊筛选
     * @param jsonObject
     * @return
     */
    @ApiOperation(value = "content", notes="content")
    @RequestMapping(value = "/content", consumes = "application/jso", method = RequestMethod.POST)
    @ApiImplicitParams(
            {@ApiImplicitParam(paramType = "query", name = "location", value = "location", dataType = "String"),
                    @ApiImplicitParam(paramType = "query", name = "type", value= "type", dataType = "String")
            }
    )
    public String downloadFile(@RequestBody JSONObject jsonObject){
        log.info(" -->接收到校验信息内容:{}", JSONObject.valueToString(jsonObject));
        long startTime = System.currentTimeMillis();
        try {
            String errDesc = contentDisplaySvc.filterContent(jsonObject);

            if(StringUtils.isEmpty(errDesc)){
                //
            }else{
                //
            }

            long endSendTime = System.currentTimeMillis();
            long runSendTime = (endSendTime - startTime) / 1000;
            log.info(" ****** downloadFile 完成,总用时:{}S，结束！", runSendTime);
        } catch (Exception e) {
            String message = e.getMessage();
            log.error(" %%%%%% downloadFile error:[{}]", message, e);
            //
        }
        return "hello";
    }
}
