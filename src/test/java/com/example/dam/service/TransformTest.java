//package com.example.dam.service;
//
//import com.example.dam.enums.TransformVariable;
//import com.example.dam.service.implement.transform.ITransformable;
//import com.example.dam.service.implement.transform.ImageTransform;
//import com.example.dam.service.implement.transform.VideoTransform;
//import org.junit.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.io.IOException;
//import java.util.Map;
//
//@SpringBootTest
//public class TransformTest {
//    ITransformable imageTransform;
//    @Test
//    public void transformImage() throws IOException, InterruptedException {
//        ITransformable imageTransform = new ImageTransform();
//        String path = "src/test/resources/cup-on-a-table.jpg";
//        Map<TransformVariable, String> options = Map.of(TransformVariable.WIDTH, "100", TransformVariable.HEIGHT, "100", TransformVariable.QUALITY, "100");
//        imageTransform.transform(path, options);
//    }
//
//    @Test
//    public void transformVideo() throws IOException, InterruptedException {
//         ITransformable videoTransform = new VideoTransform();
//         String path = "src/test/resources/turtle.mp4";
//         Map<TransformVariable, String> options = Map.of(TransformVariable.RESOLUTION, "144p");
//         videoTransform.transform(path, options);
//    }
//}