package org.ryuji.ddd.demo.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: ddd-demo
 * @description:
 * @author: LiuXi
 * @create: 2022-06-15 09:37
 */
@Slf4j
@Api(tags = "调试接口")
@RestController
@RequestMapping("/debug")
public class DebugController {

    @ApiOperation(value = "获取token")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "uid", value = "用户uid", dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "deviceId", value = "设备id", dataType = "String")
    })
    @GetMapping("/token")
    public Map test3(@RequestParam(required = false, defaultValue = "0") Long uid,
                     @RequestParam(required = false, defaultValue = "10") String deviceId) {
        Map<String, String> m = new HashMap<>();
//        String token = jwtTool.getToken(uid, deviceId);
        m.put("token", "token");
        return m;
    }

}