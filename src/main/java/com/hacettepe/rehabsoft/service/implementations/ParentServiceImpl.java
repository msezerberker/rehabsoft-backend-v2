package com.hacettepe.rehabsoft.service.implementations;



import com.hacettepe.rehabsoft.entity.Parent;
import com.hacettepe.rehabsoft.entity.Phone;
import com.hacettepe.rehabsoft.repository.ParentRepository;
import com.hacettepe.rehabsoft.service.ParentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class ParentServiceImpl implements ParentService {



    private final ParentRepository parentRepository;

    public ParentServiceImpl(ParentRepository parentRepository) {
        this.parentRepository = parentRepository;
    }



    private void fillPhoneNumber(Parent parent){
        log.warn("Parent servisine girdi:FillPhoneNumber:");

        for(Phone p:parent.getPhoneCollection()){
            p.setParent(parent);
        }
    }



    @Override
    @Transactional
    public Parent saveParent(Parent parent) {
        log.warn("Parent servisine girdi:Save:");
        fillPhoneNumber(parent);
        parentRepository.save(parent);

        return parent;
    }
}
