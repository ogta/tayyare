package co.tayyare.onboarding.saas.controller;

import co.tayyare.onboarding.base.ExceptionHelper;
import co.tayyare.onboarding.saas.dto.SaasInformation;
import co.tayyare.onboarding.saas.service.ISaasInformationService;
import co.tayyare.onboarding.saas.util.constant.SaasServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/saas")
public class SaasInformationController extends ExceptionHelper {

    @Autowired
    private ISaasInformationService saasInfoService;

    @PostMapping("/")
    public ResponseEntity createSaasProject(@RequestBody SaasInformation saasInformation) {
        SaasInformation saasInfo = saasInfoService.createSaasProjectInformation(saasInformation);

        ResponseEntity response = checkResponseCode(saasInfo);
        if (response != null) return response;

        return new ResponseEntity(saasInfo, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaasInformation> getSaasProjectInformation(@PathVariable("id") final long id) {
        SaasInformation saasInfo = saasInfoService.getSaasProjectInformation(id);

        if (saasInfo.getResponseCode() == SaasServiceResponse.RECORD_NOT_FOUND.getResponseCode()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity(saasInfo, HttpStatus.OK);

    }

}
