//package vn.fs.objectstorageservice;
//
//import io.minio.BucketExistsArgs;
//import io.minio.MakeBucketArgs;
//import io.minio.MinioClient;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class MinioConfig {
//	@Value("${minio.access.name}")
//	private String accessKey;
//	@Value("${minio.access.secret}")
//	private String accessSecret;
//	@Value("${minio.url}")
//	private String minioUrl;
//	@Value("${minio.bucket.name}")
//	private String bucketName;
//
//	@Bean
//	public MinioClient minioClient() {
//		try {
//			MinioClient minioClient = MinioClient.builder()
//					.endpoint(minioUrl)
//					.credentials(accessKey, accessSecret)
//					.build();
//			if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
//				minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
//			}
//			return minioClient;
//		} catch (Exception e) {
//			throw new RuntimeException(e.getMessage());
//		}
//	}
//}