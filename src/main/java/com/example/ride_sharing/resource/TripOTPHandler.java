package com.example.ride_sharing.resource;
import com.example.ride_sharing.dto.OTPData;
import com.example.ride_sharing.service.TwilioOTPService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
//import reactor.core.publisher.Mono;
@Component
public class TripOTPHandler {
    @Autowired
    private TwilioOTPService service;
    public Mono<ServerResponse> sendOTP(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(OTPData.class)
                .flatMap(dto -> service.getotpService(dto))
                .flatMap(dto -> ServerResponse.status(HttpStatus.OK)
                        .body(BodyInserters.fromValue(dto)));
    }
    public Mono<ServerResponse> validateOTP(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(OTPData.class)
                .flatMap(dto -> service.validateOTP(dto.getOneTimePassword(), dto.getUserName()))
                .flatMap(dto -> ServerResponse.status(HttpStatus.OK)
                        .bodyValue(dto));
    }
}
