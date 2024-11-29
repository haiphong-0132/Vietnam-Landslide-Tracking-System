package team08.oop.lms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import team08.oop.lms.model.Feature;
import team08.oop.lms.model.FeatureCollection;
import team08.oop.lms.model.FinalResult;
import team08.oop.lms.model.Image;
import team08.oop.lms.model.MiniFeature;
import team08.oop.lms.model.Result;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FinalResultService {

   @Autowired
   private FeatureService featureService;

   @Autowired
   private ImageService imageService;

   //Tối ưu hoá hiệu năng
   private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

   //CompletableFuture.supplyAsync giúp xử lý song song việc gọi API
   public FinalResult generateFinalResult(List<String> cities, List<String> types, List<String> typeStructs) {
      List<CompletableFuture<Result>> futureResults = new ArrayList<>();

      for (String city : cities) {
         for (String type : types) {
            for (String typeStruct : typeStructs) {
               futureResults
                     .add(CompletableFuture.supplyAsync(() -> processCityType(city, type, typeStruct), executor));
            }
         }
      }

      // Kết hợp tất cả các kết quả sau khi xử lý xong
      List<Result> results = futureResults.stream()
            .map(CompletableFuture::join) // Đợi từng tác vụ hoàn tất
            .filter(result -> result != null) // Loại bỏ các kết quả null
            .collect(Collectors.toList());

      return new FinalResult(results);
   }

   private Result processCityType(String city, String type, String typeStruct) {
      try {
         log.info("Fetching features for city: {}, type: {}, type_struct: {}", city, type, typeStruct);

         // Lấy danh sách các features từ FeatureService
         FeatureCollection featureCollection = featureService.getFeatureCollection(city, type, typeStruct);
         List<Feature> features = featureCollection.getFeatures();

         if (features == null || features.isEmpty()) {
            log.warn("No features found for city: {}, type: {}, type_struct: {}", city, type, typeStruct);
            return null;
         }

         List<MiniFeature> miniFeatures = features.parallelStream()
               .map(feature -> createMiniFeature(feature))
               .filter(miniFeature -> miniFeature != null)
               .collect(Collectors.toList());

         if (miniFeatures.isEmpty()) {
            log.warn("No MiniFeatures created for city: {}, type: {}, type_struct: {}", city, type, typeStruct);
            return null;
         }

         return new Result(city, type, typeStruct, miniFeatures);

      } catch (Exception e) {
         log.error("Error processing city: {}, type: {}, type_struct: {}", city, type, typeStruct, e);
         return null;
      }
   }

   private MiniFeature createMiniFeature(Feature feature) {
      try {
         String id = feature.getProperties().getId();
         log.debug("Fetching image for feature ID: {}", id);

         Image image = imageService.getImage(id);
         return new MiniFeature(feature, image);

      } catch (Exception e) {
         log.error("Error creating MiniFeature for feature: {}", feature, e);
         return null;
      }
   }
}