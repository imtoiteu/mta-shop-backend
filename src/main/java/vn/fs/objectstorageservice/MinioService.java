//package vn.fs.objectstorageservice;
//
//import io.minio.*;
//import org.h2.util.IOUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.ByteArrayOutputStream;
//import java.io.InputStream;
//import java.util.UUID;
//
//@Service
//public class MinioService {
//
//    @Autowired
//    private MinioClient minioClient;
//    @Value("${minio.bucket.name}")
//    private String bucketName;
//
//    @Value("${minio.url}")
//    private String minioEndpoint;
//
//    // ... các phương thức khác của MinioService
//
//    public String getBaseUrl() {
//        // Trả về endpoint cấu hình sẵn
//        return minioEndpoint;
//    }
//
//
////    public void uploadFile(String bucketName, String objectName, InputStream inputStream, String contentType) {
////        try {
////            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
////            if (!found) {
////                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
////            }
////            minioClient.putObject(
////                    PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(
////                                    inputStream, inputStream.available(), -1)
////                            .contentType(contentType)
////                            .build());
////        } catch (Exception e) {
////            throw new RuntimeException("Error occurred: " + e.getMessage());
////        }
////    }
//
//
//    public String uploadFile(String bucketName, String objectName, InputStream inputStream, String contentType) {
//        try {
//            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
//            if (!found) {
//                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
//            }
//            String fileName = generateFileName(objectName); // Giả sử bạn có một function tạo ra tên file dựa vào objectName
//            minioClient.putObject(
//                    PutObjectArgs.builder().bucket(bucketName).object(fileName).stream(
//                                    inputStream, inputStream.available(), -1)
//                            .contentType(contentType)
//                            .build());
//            return fileName;
//        } catch (Exception e) {
//            throw new RuntimeException("Error occurred: " + e.getMessage());
//        }
//    }
//
//    // Hàm giả sử để tạo fileName (bạn có thể thay đổi theo logic mong muốn)
//    private String generateFileName(String originalName) {
//        // Thêm logic để tạo ra tên file duy nhất nếu cần thiết
//        // Ví dụ: thêm timestamp hoặc UUID
//        String extension = originalName.substring(originalName.lastIndexOf('.'));
//        String fileName = UUID.randomUUID().toString() + extension;
//        return fileName;
//    }
//
//    // Truy xuất file
//    public byte[] getFile(String fileName) {
//        try {
//            try (InputStream stream = minioClient.getObject(
//                    GetObjectArgs.builder()
//                            .bucket(bucketName)
//                            .object(fileName)
//                            .build());
//                 ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
//                IOUtils.copy(stream, outputStream);
//                return outputStream.toByteArray();
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e.getMessage());
//        }
//    }
//
//
//
//}