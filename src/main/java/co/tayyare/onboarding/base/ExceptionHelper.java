package co.tayyare.onboarding.base;

import co.tayyare.onboarding.saas.dto.BaseDTO;
import co.tayyare.onboarding.saas.util.constant.SaasServiceResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class ExceptionHelper {

    public ResponseEntity checkResponseCode(BaseDTO dto) {
        if (dto != null && dto.getResponseCode() != SaasServiceResponse.SUCCESS.getResponseCode()) {
            BaseDTO response = new BaseDTO();
            response.setResponseCode(dto.getResponseCode());
            response.setResponseDescription(dto.getResponseDescription());
            return new ResponseEntity(response, HttpStatus.OK);
        }
        return null;
    }

}
