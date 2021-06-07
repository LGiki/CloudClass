package com.fzu.CloudClass.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fzu.CloudClass.entity.Person;
import com.fzu.CloudClass.service.impl.PersonServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/oauth")
public class OauthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OauthController.class);

    private static final String ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token";

    private static final String USER_URL = "https://api.github.com/user";

    @Autowired
    private PersonServiceImpl personService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${oauth.github.app-id}")
    private String appId;

    @Value("${oauth.github.app-secret}")
    private String appSecret;

    /**
     * Github回调
     * @param request
     * @param code
     * @param state
     * @return
     */
//    @ResponseBody
    @GetMapping("/github")
    public JSONObject github(HttpServletRequest request, @RequestParam("code") String code,
                               @RequestParam(value = "state", required = false) String state) {

        LOGGER.info("Github Oath:code={}, state={}", code, state);

        /**
         * 使用code获取accessToken
         */
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
//        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
        httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

//        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
//        requestBody.add("client_id", this.appId);
//        requestBody.add("client_secret", this.appSecret);
//        requestBody.add("code", code);

//        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(requestBody, httpHeaders);

        StringBuilder builder = new StringBuilder(ACCESS_TOKEN_URL);
        builder.append("?client_id=");
        builder.append(this.appId);
        builder.append("&client_secret=");
        builder.append(this.appSecret);
        builder.append("&code=");
        builder.append(code);

        ResponseEntity<JSONObject> responseEntity = this.restTemplate.postForEntity(builder.toString(), null,
                JSONObject.class);

        JSONObject jsonObject = responseEntity.getBody();
        String accessToken = jsonObject.getString("access_token");
        String scope = jsonObject.getString("scope");
        String tokenType = jsonObject.getString("token_type");
        LOGGER.info("accessToken={}, scope={}, tokenType={}", accessToken, scope, tokenType);

        /**
         * 通过accessToken获取用户信息
         */
        httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION, "token " + accessToken);
        httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        responseEntity = this.restTemplate.exchange(USER_URL, HttpMethod.GET, new HttpEntity<Void>(null, httpHeaders),
                JSONObject.class);
        jsonObject = responseEntity.getBody();

        LOGGER.info("user={}", jsonObject);

        /**
         * TODO 从用户信息中获取到id，匹配本地数据库关联的系统用户。从而完成登录逻辑。
         */
        HttpSession session = request.getSession();
        session.setAttribute("user", JSON.toJSONString(jsonObject, SerializerFeature.PrettyFormat));

        String token = (String) jsonObject.get("node_id");

        if(personService.getPersonByOauth(token) == null){
            Person person = new Person();
            person.setOauth(token);
            personService.addPerson(person);
        }



        JSONObject result = new JSONObject();
        result.put("token", token);
        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }
}
