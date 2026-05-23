package com.example.demo.service;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.transaction.support.TransactionSynchronizationManager;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;

import com.example.demo.dto.CourseDto;
import com.example.demo.entity.CourseDetails;
import com.example.demo.entity.CourseVideo;
import com.example.demo.entity.EdpCategory;
import com.example.demo.entity.LangCode;
import com.example.demo.entity.Qualification;
import com.example.demo.repository.CourseDetailsRepository;
import com.example.demo.repository.CourseVideoRepository;
import com.example.demo.repository.EdpCategoryRepository;
import com.example.demo.repository.LangCodeRepository;
import com.example.demo.repository.QualificationRepository;

import jakarta.transaction.Transactional;


@Service
public class UploadFile {

    private static final Logger logger = LoggerFactory.getLogger(UploadFile.class);


    @Autowired
    private EdpCategoryRepository edpCategoryRepository;

    @Autowired
    private LangCodeRepository langcodeRepository;

    @Autowired
    private CourseDetailsRepository courseDetailsRepository;

    @Autowired
    private CourseVideoRepository courseVideoRepository;

     @Autowired
    private QualificationRepository qualificationRepository;

     
    LangCode langCode;
    CourseVideo courseVideo;
    Qualification qualificationOld,qualificationNew;
    CourseDetails courseDetails;

    

    
     @Transactional
     public void validateRows(CourseDto temp){
        if (temp == null ) {
            return;
        }
        
        // Check if EdpCategoty exists by edp_id
        EdpCategory edpCategory = edpCategoryRepository.findById(temp.getEdpId())
            .orElse(new EdpCategory());
        
        // Update fields from temp
        edpCategory.setEdp_id(temp.getEdpId());
        edpCategory.setEdp_category(temp.getTypeOfEdp());
        edpCategory.setEdp_active('Y'); // Set default active status
        
        // Check if langcode exists by langcode_id
        
            
        
        try {
        
            langCode = langcodeRepository.findByLangCode(temp.getLanguage()) .orElse(new LangCode());
            System.out.println(langCode.getLangcode_id());
            courseVideo = courseVideoRepository.findByVideoUrl(temp.getAboutVideoUrl()).orElse(new CourseVideo());
            courseVideo.setVideoDuration(temp.getDuration());
            courseVideo.setVideoUrl(temp.getAboutVideoUrl());
            courseVideo.setVideoLang(langCode.getLangcode_id());
            qualificationOld = qualificationRepository.findByQualification(temp.getQualificationName(),temp.getQualificationCodeOld()).orElse(new Qualification());
            if (qualificationOld.getCode() != null){

                qualificationOld.setCodeActive("N");
                qualificationRepository.save(qualificationOld);
                
            }else{

                qualificationOld.setCode(temp.getQualificationCodeOld());
                qualificationOld.setCodeActive("N");
                qualificationOld.setQualificationName(temp.getQualificationName());
                qualificationOld.setQualificationShortDescription(temp.getShortDescription());
                 if (!qualificationRepository.existsByCode(temp.getQualificationCodeOld())) {
                qualificationOld = qualificationRepository.save(qualificationOld);
                 }
              
            }
                
               
                qualificationNew = new Qualification();
                qualificationNew.setCode(temp.getQualificationCodeUpdated());
                qualificationNew.setCodeActive("Y");
                qualificationNew.setQualificationName(temp.getQualificationName());
                qualificationNew.setQualificationShortDescription(temp.getShortDescription());
                if (qualificationRepository.findByQualificationCode(temp.getQualificationName(), temp.getQualificationCodeUpdated()).isEmpty()) { 
    // handle not found 

                   logger.info("In previous course with qualificationId: {}", qualificationNew.getQualificationName());

                    qualificationNew = qualificationRepository.save(qualificationNew);
                }else
                {
                    PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out, StandardCharsets.UTF_8), true);

                    out.println(temp.getQualificationName() + "Vikas" + temp.getQualificationCodeUpdated());
            qualificationNew = qualificationRepository.findByQualificationCode(temp.getQualificationName(),temp.getQualificationCodeUpdated()).orElse(new Qualification());
                  
                }

                
                
            }

         catch (Exception e) {
          
            e.printStackTrace();
         
        }

        // Save to database
        edpCategoryRepository.save(edpCategory);
       courseVideo = courseVideoRepository.save(courseVideo);

        courseDetails = new CourseDetails();
        courseDetails.setEdpId(temp.getEdpId());
        courseDetails.setQualificationId(qualificationNew.getQualificationCodeId()); 
       
        long cid = (courseVideo.getId());
        int cid1 = (int)cid;
        courseDetails.setVideoId(cid1);

        if (!courseDetailsRepository.existsByQualificationId(courseDetails.getQualificationId())) {
                    System.out.println("in previous course " +  "w" + courseDetails.getQualificationId());
                    
        courseDetails = courseDetailsRepository.save(courseDetails);

        // 🔁 Register sync point
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void beforeCommit(boolean readOnly) {
                logger.info("Before commit: preparing final actions");
            }

            @Override
            public void afterCommit() {
                logger.info("After commit: transaction successfully committed");
            }

            @Override
            public void afterCompletion(int status) {
                logger.info("After completion: status = {}", status == STATUS_COMMITTED ? "COMMITTED" : "ROLLED BACK");
            }


        });
        }

        



        
        
       // courseRepository.save(course);
    }

}






    