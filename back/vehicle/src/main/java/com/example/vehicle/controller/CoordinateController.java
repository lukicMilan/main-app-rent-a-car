package com.example.vehicle.controller;

//import com.example.vehicle.WebSocketConfig;
//import com.example.vehicle.model.JwtResponse;
//import com.example.vehicle.service.CoordinateService;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Map;
//
//@RestController
//@RequestMapping(path = "token")
//public class CoordinateController {
//
//    @Autowired
//    CoordinateService secretService;
//
//    @MessageMapping("/send/message")
//    public void onRecievedMessage(String message){
//        System.out.println("recieved");
//    }
//
//    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> generateToken(@RequestBody Map<String, Object> claims) {
//        System.out.println("Token request recieved");
//        //boolean valid = service.testAuthenticate(vehicleId);
//        String jws = Jwts.builder()
//                .setClaims(claims)
//                .signWith(SignatureAlgorithm.HS256, secretService.getHS256SecretBytes())
//                .compact();
//        return new ResponseEntity<>(new JwtResponse(jws), HttpStatus.OK);
//
//    }
//
//}
